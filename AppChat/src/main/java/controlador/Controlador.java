package controlador;

import java.awt.Image;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.GeneradorPDF;
import modelo.GrupoDeContactos;
import modelo.Mensaje;
import modelo.RepositorioUsuarios;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorContactoDAO;
import persistencia.IAdaptadorMensajeDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class Controlador {
	private static Controlador unicaInstancia;
	
	private Usuario usuarioActual;
	private GeneradorPDF servicioPDF;
	private RepositorioUsuarios repositorioUsuarios;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorContactoDAO adaptadorContacto; 
	
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
	
	public Usuario obtenerUsuario(Mensaje mensaje) {
		return mensaje.obtenerElOtroUsuario(usuarioActual);
	}
	
	public void registrarUsuario(String nombreCompleto, String password, int numeroTelefono, String email, LocalDate fechaNacimiento,
			String imagenPerfil, String saludo) {
		Usuario usuario = new Usuario(nombreCompleto, password, numeroTelefono, email, fechaNacimiento, imagenPerfil, saludo);
		adaptadorUsuario.registrarUsuario(usuario);
		repositorioUsuarios.addUsuario(usuario);
	}
	
	// TODO: Eliminar este método, es de prueba
	public void registrarUsuario(Usuario usuario) {
		adaptadorUsuario.registrarUsuario(usuario);
		repositorioUsuarios.addUsuario(usuario);
	}
	
	private void enviarMensaje(Usuario emisor, Usuario receptor, String texto) {
		Mensaje mensaje = emisor.enviarMensaje(receptor, texto);
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorUsuario.modificarUsuario(emisor);
		adaptadorUsuario.modificarUsuario(receptor);
	}
	
	public List<Contacto> getContactos(){
		return usuarioActual.getContactos();
	}
	
	public void addGrupo(List<ContactoIndividual> contactos, String nombre, String foto) {
		GrupoDeContactos grupo = usuarioActual.addGrupoDeContactos(contactos, nombre, foto);
		adaptadorContacto.registrarContacto(grupo);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	public Mensaje enviarMensaje(Mensaje m, String texto) {
		Usuario receptor = m.obtenerElOtroUsuario(usuarioActual);
		Mensaje mensaje = usuarioActual.enviarMensaje(receptor, texto);
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(receptor);
		return mensaje;
	}
	
	public boolean enviarMensaje(int tlf, String texto) {
		Optional<Usuario> optionalUsuario = repositorioUsuarios.getUsuario(tlf);
		if(optionalUsuario.isPresent()) {
			Usuario receptor = optionalUsuario.get();
			Mensaje mensaje = usuarioActual.enviarMensaje(receptor, texto);
			adaptadorMensaje.registrarMensaje(mensaje);
			adaptadorUsuario.modificarUsuario(usuarioActual);
			adaptadorUsuario.modificarUsuario(receptor);
			return true;
		}
		return false;
	}
	
	public void enviarMensaje(Contacto contacto, String texto) {
		if(contacto instanceof ContactoIndividual) {
			this.enviarMensaje(usuarioActual, ((ContactoIndividual) contacto).getUsuario() , texto);
		} else if(contacto instanceof GrupoDeContactos) {
			((GrupoDeContactos) contacto).getContactos().stream().forEach(c -> this.enviarMensaje(usuarioActual, c.getUsuario(), texto));
		}
	}
	
	public List<Mensaje> getUltimosMensajes() {
		return usuarioActual.getUltimosMensajes();
	}
	
	public String getImagenDePerfil() {
		return usuarioActual.getImagenPerfil();
	}
	
	public Optional<String> getNombreDeContacto(Usuario usuario) {
		return usuarioActual.getNombreContacto(usuario);
	}
	
	public List<Mensaje> getOtrosMensajes(Mensaje m){
		return usuarioActual.getOtrosMensajes(m);
	}
	
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
	
	public boolean isUsuarioActualEmisor(Mensaje mensaje) {
		return mensaje.isEmisor(usuarioActual); 
	}
	
	public boolean addContactoIndividual(int telefono, String nombre){
		Optional<Usuario> optionalUsuario = repositorioUsuarios.getUsuario(telefono);
		if(optionalUsuario.isPresent()) {
			Usuario usuario = optionalUsuario.get();
			addContactoIndividual(usuario, nombre);
			return true;
		}
		return false;
	}
	
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
	}
	
	private void inicializarRepositorios() {
		repositorioUsuarios = RepositorioUsuarios.getUnicaInstancia();
	}
	
	
}
