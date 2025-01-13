package controlador;

import java.awt.Image;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Descuento;
import modelo.GeneradorPDF;
import modelo.GrupoDeContactos;
import modelo.Mensaje;
import modelo.RepositorioUsuarios;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoDAO;
import persistencia.IAdaptadorDescuentoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;
import tds.BubbleText;

public class Controlador {
	private static Controlador unicaInstancia;
	
	private Usuario usuarioActual;
	private GeneradorPDF servicioPDF;
	private RepositorioUsuarios repositorioUsuarios;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorContactoDAO adaptadorContacto; 
	private IAdaptadorDescuentoDAO adaptadorDescuento;
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	private Controlador() {
		inicializarAdaptadores();
		inicializarRepositorios();
	}
	
	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new Controlador();
		}
		return unicaInstancia;
	}
	
	/**
	 * @param mensaje
	 * @return El usuario que intercambió <code>mensaje</code> con el usuario actual
	 */
	public Usuario obtenerUsuario(Mensaje mensaje) {
		return mensaje.obtenerElOtroUsuario(usuarioActual);
	}
	
	public boolean isUsuarioActualPremium() {
		return usuarioActual.isPremium();
	}
	
	public Descuento getDescuentoUsuarioActual() {
		return usuarioActual.getDescuento();
	}
	
	public void registrarUsuario(String nombreCompleto, String password, int numeroTelefono, String email, LocalDate fechaNacimiento,
			String imagenPerfil, String saludo) {
		Usuario usuario = new Usuario(nombreCompleto, password, numeroTelefono, email, fechaNacimiento, imagenPerfil, saludo, LocalDate.now());
		adaptadorUsuario.registrarUsuario(usuario);
		repositorioUsuarios.addUsuario(usuario);
	}
	
	private Mensaje enviarMensaje(Usuario emisor, Usuario receptor, String texto) {
		Optional<Integer> emoticonoOptional = getEmoticono(texto);
		Mensaje mensaje;
		if(emoticonoOptional.isPresent()) {
			mensaje = emisor.enviarMensaje(receptor, emoticonoOptional.get());
		}
		else {
			mensaje = emisor.enviarMensaje(receptor, texto);
		}
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorUsuario.modificarUsuario(emisor);
		adaptadorUsuario.modificarUsuario(receptor);
		return mensaje;
	}
	
	/**
	 * @return Lista con todos los contactos del usuario actual
	 */
	public List<Contacto> getContactos(){
		return usuarioActual.getContactos();
	}
	
	/**
	 * @return Lista con todos los mensajes que ha enviado o recibido el usuario actual
	 */
	public List<Mensaje> getTodosMensajes(){
		return Stream.of(usuarioActual.getMensajesEnviados(), usuarioActual.getMensajesRecibidos())
				.flatMap(lista -> lista.stream())
				.sorted((m1, m2) -> - m1.getFechaYHora().compareTo(m2.getFechaYHora()))
				.collect(Collectors.toList());
	}
	
	/**
	 * Nota: Los emojis se indican en <code>texto</code>, con dos puntos seguidos del número del emoticono
	 * 
	 * @param nombreContacto Si no está vacío, sólo devuelve mensajes enviados con un contacto del usuario actual, cuyo nombre contenga la subcadena <code>nombreContacto</code>
	 * @param texto Si no está vacío, sólo devuelve mensajes que contengan la subcadena <code>texto</code>, o se refieran al emoji indicado
	 * @param numeros Si no está vacío, sólo devuelve mensajes que el usuario actual ha intercambiado con un usuario cuyo teléfono contenga la subcadena <code>numeros</code>
	 * @return Lista con los mensajes que ha enviado o recibido el usuario actual, y cumplen los filtros
	 */
	public List<Mensaje> getMensajesFiltrados(String nombreContacto, String texto, String numeros){
		// ¿"m" tiene el emoji indicado en "texto"?
		Predicate<Mensaje> isEmoji = (m -> {
			Optional<Integer> optionalEmoji = getEmoticono(texto);
			if(optionalEmoji.isPresent() && optionalEmoji.get().equals(m.getEmoticono().orElse(Integer.valueOf(BubbleText.MAXICONO+1))))
				return true;
			return false;
		});
		// ¿"m" es enviado/recibido por un contacto del usuario actual cuyo nombre contiene "nombreContacto"?
		Predicate<Mensaje> otroEsContactoConNombre = (m -> {
			Optional<String> optionalNombreContacto = usuarioActual.getNombreContacto(m.obtenerElOtroUsuario(usuarioActual));
			return optionalNombreContacto.isPresent() && optionalNombreContacto.get().contains(nombreContacto);
		});
		
		return getTodosMensajes().stream()
				.filter(m -> (texto.isEmpty() || m.containsText(texto) || isEmoji.test(m)))
				.filter(m -> (numeros.isEmpty() || (m.isEmisor(usuarioActual) && m.tlfReceptorContiene(numeros)) || (m.isReceptor(usuarioActual) && m.tlfEmisorContiene(numeros))))
				.filter(m -> nombreContacto.isEmpty() || otroEsContactoConNombre.test(m))
				.collect(Collectors.toList());
	}
	
	/**
	 * Añade un nuevo grupo de contactos a los contactos del usuario actual, y lo guarda en la base de datos
	 * 
	 * @param contactos Lista de contactos que conforman el grupo
	 * @param nombre El nombre del grupo
	 * @param foto URL de la foto del grupo (usar cadena vacía "" si no hay foto)
	 */
	public void addGrupo(List<ContactoIndividual> contactos, String nombre, String foto) {
		GrupoDeContactos grupo = usuarioActual.addGrupoDeContactos(contactos, nombre, foto);
		adaptadorContacto.registrarContacto(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	/**
	 * Registra un nuevo mensaje enviado por el usuario actual al usuario con el que se intercambió el mensaje <code>m</code>, y lo guarda en la base de datos.
	 * @param m
	 * @param texto El texto del nuevo mensaje
	 * @return El nuevo mensaje
	 */
	public Mensaje enviarMensaje(Mensaje m, String texto) {
		Usuario receptor = m.obtenerElOtroUsuario(usuarioActual);
		return enviarMensaje(usuarioActual, receptor, texto);
	}
	
	/**
	 * Registra un mensaje enviado por el usuario actual al usuario con teléfono <code>tlf</code>, y lo guarda en la base de datos
	 * @param tlf
	 * @param texto El texto del nuevo mensaje
	 * @return true si se ha podido enviar el mensaje, false si el teléfono no pertenece a ningún usuario de la aplicación.
	 */
	public boolean enviarMensaje(int tlf, String texto) {
		Optional<Usuario> optionalUsuario = repositorioUsuarios.getUsuario(tlf);
		if(optionalUsuario.isPresent()) {
			Usuario receptor = optionalUsuario.get();
			enviarMensaje(usuarioActual, receptor, texto);
			return true;
		}
		return false;
	}
	
	/**
	 * Registra un mensaje enviado por el usuario actual a su contacto <code>contacto</code>, o en el caso de ser un grupo, envía uno a cada contacto individual de este. Los registra en la base de datos.
	 * 
	 * @param contacto
	 * @param texto Texto del nuevo mensaje
	 */
	public void enviarMensaje(Contacto contacto, String texto) {
		if(contacto instanceof ContactoIndividual) {
			this.enviarMensaje(usuarioActual, ((ContactoIndividual) contacto).getUsuario() , texto);
		} else if(contacto instanceof GrupoDeContactos) {
			((GrupoDeContactos) contacto).getContactos().stream().forEach(c -> this.enviarMensaje(usuarioActual, c.getUsuario(), texto));
		}
	}
	
	/**
	 * @return Lista ordenada por fecha con el último mensaje de cada chat del usuario actual
	 */
	public List<Mensaje> getUltimosMensajes() {
		return usuarioActual.getUltimosMensajes();
	}
	
	/**
	 * @return URL de la imagen de perfil del usuario actual
	 */
	public String getImagenDePerfil() {
		return usuarioActual.getImagenPerfil();
	}
	
	/**
	 * @param usuario
	 * @return Optional vacío si el usuario actual no tiene agregado a <code>usuario</code>, o Optional que contiene el nombre de contacto si lo tiene agregado
	 */
	public Optional<String> getNombreDeContacto(Usuario usuario) {
		return usuarioActual.getNombreContacto(usuario);
	}
	
	/**
	 * Obtiene los mensajes intercambiados entre el usuario actual y el otro participante de <code>m</code>.
	 * 
	 * @param m 
	 * @return Lista ordenada por fecha con los mensajes de la misma conversación que <code>m</code>
	 */
	public List<Mensaje> getOtrosMensajes(Mensaje m){
		return usuarioActual.getOtrosMensajes(m);
	}
	
	/**
	 * @param telefono
	 * @param password
	 * @return true si el login es exitoso, false en caso contrario
	 */
	
	public boolean login(int telefono, String password) {
		Optional<Usuario> optionalUsuario = repositorioUsuarios.getUsuario(telefono);
		if (optionalUsuario.isPresent()) {
			Usuario usuario = optionalUsuario.get();
			if (usuario.getPassword().equals(password)) {
				usuarioActual = usuario;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param mensaje
	 * @return true si el usuario actual es el emisor de <code>mensaje</code>. false en caso contrario.
	 */
	public boolean isUsuarioActualEmisor(Mensaje mensaje) {
		return mensaje.isEmisor(usuarioActual); 
	}
	
	/**
	 * Registra o actualiza el contacto del usuario actual con el número <code>telefono</code> y lo guarda en la base de datos.
	 * 
	 * @param telefono
	 * @param nombre Nombre del contacto
	 * @return true si el teléfono pertenece a un usuario de la aplicación. false en caso contrario.
	 */
	public boolean addContactoIndividual(int telefono, String nombre){
		Optional<Usuario> optionalUsuario = repositorioUsuarios.getUsuario(telefono);
		if(optionalUsuario.isPresent()) {
			Usuario usuario = optionalUsuario.get();
			addContactoIndividual(usuario, nombre);
			return true;
		}
		return false;
	}
	
	/**
	 * Registra o actualiza el contacto del usuario actual que hace referencia a <code>usuario</code>, y lo guarda en la base de datos
	 * 
	 * @param usuario
	 * @param nombre Nombre del contacto
	 */
	public void addContactoIndividual(Usuario usuario, String nombre){
		Optional<ContactoIndividual> contactoOptional = usuarioActual.actualizarContactoIndividual(usuario, nombre);
		if(contactoOptional.isPresent()) {
			//Ya existía el contacto y se ha actualizado su nombre
			adaptadorContacto.modificarContacto(contactoOptional.get());
			return;
		}
		// No existía y se tiene que crear
		ContactoIndividual contacto = usuarioActual.addContactoIndividual(usuario, nombre);
		adaptadorContacto.registrarContacto(contacto);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	/**
	 * Modifica la imagen de perfil del usuario actual y lo refleja en la base de datos.
	 * 
	 * @param url URL de la nueva imagen
	 * @return true si la URL <code>url</code> es válida y se ha modificado, false en caso contrario.
	 */
	public boolean cambiarImagenDePerfil(String url) {
		try {
			URL imageUrl = new URL(url);
			Image imageNueva = ImageIO.read(imageUrl);
		} catch(Exception e) {
			return false;
		}
		
		// Si no hay excepción, la imagen es válida (la url apunta a una imagen accesible)
		usuarioActual.setImagenPerfil(url);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		return true;
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
		adaptadorContacto = factoria.getContactoDAO();
		adaptadorDescuento = factoria.getDescuentoDAO();
	}
	
	private void inicializarRepositorios() {
		repositorioUsuarios = RepositorioUsuarios.getUnicaInstancia();
	}
	
	private Optional<Integer> getEmoticono(String texto) {
		if(texto.matches("^:[0-9]+$")) {
			String numeroString = texto.substring(1);
			int numero = Integer.valueOf(numeroString);
			if (numero >= 0 && numero <= BubbleText.MAXICONO) {
				return Optional.of(numero);
			}
		}
		// El texto no se refiere a ningún emoticono
		return Optional.empty();
	}
	
	public void hacerPremium(Descuento descuento) {
		usuarioActual.setPremium(true);
		usuarioActual.setDescuento(descuento);
		adaptadorDescuento.registrarDescuento(descuento);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
}
