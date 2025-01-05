package controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import modelo.ContactoIndividual;
import modelo.GeneradorPDF;
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
	
	// TODO: Eliminar este método, es de prueba
	public void enviarMensaje(Usuario emisor, Usuario receptor, String texto) {
		Mensaje mensaje = emisor.enviarMensaje(receptor, texto);
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorUsuario.modificarUsuario(emisor);
		adaptadorUsuario.modificarUsuario(receptor);
	}
	
	public Mensaje enviarMensaje(Mensaje m, String texto) {
		Usuario receptor = m.obtenerElOtroUsuario(usuarioActual);
		Mensaje mensaje = usuarioActual.enviarMensaje(receptor, texto);
		adaptadorMensaje.registrarMensaje(mensaje);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		adaptadorUsuario.modificarUsuario(receptor);
		return mensaje;
	}
	
	public List<Mensaje> getUltimosMensajes() {
		return usuarioActual.getUltimosMensajes();
	}
	
	public String getImagenDePerfil() {
		return usuarioActual.getImagenPerfil();
	}
	
	public String getNombreDeContacto(Usuario usuario) {
		Optional<String> nombreOptional = usuarioActual.getNombreContacto(usuario);
		if(nombreOptional.isPresent()) {
			return nombreOptional.get();
		}
		else {
			return Integer.toString(usuario.getNumeroTelefono());
		}
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
			ContactoIndividual contacto = usuarioActual.addContactoIndividual(usuario, nombre);
			adaptadorContacto.registrarContacto(contacto);
			adaptadorUsuario.modificarUsuario(usuarioActual);
			return true;
		}
		return false;
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
