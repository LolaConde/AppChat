package controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import modelo.GeneradorPDF;
import modelo.Mensaje;
import modelo.RepositorioUsuarios;
import modelo.Usuario;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class Controlador {
	private static Controlador unicaInstancia;
	
	private Usuario usuarioActual;
	private GeneradorPDF servicioPDF;
	private RepositorioUsuarios repositorioUsuarios;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	private Controlador() {
		inicializarAdaptadores();
		inicializarRepositorios();
		// Añadir ejemplo
		
		// Usuario actual
		/*usuarioActual = new Usuario("Lola Conde", "password", 12345678, "lola@gmail.com", LocalDate.now(), "https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg");
		// Otros usuarios
		Usuario u1 = new Usuario("Ana Conde", "password", 13579, "ana@gmail.com", LocalDate.now(),
				"https://img.huffingtonpost.es/files/image_1200_720/uploads/2023/06/22/un-perro-de-raza-labrador.jpeg");
		Usuario u2 = new Usuario("Alvaro Gomez", "password", 234567891, "alvaro@gmail.com", LocalDate.now(),
				"https://estaticos-cdn.prensaiberica.es/clip/823f515c-8143-4044-8f13-85ea1ef58f3a_16-9-discover-aspect-ratio_default_0.jpg");
		Usuario u3 = new Usuario("Pablo Gomez", "password", 345678912, "pablo@gmail.com", LocalDate.now(),
				"https://doggiesintown.com/wp-content/uploads/2023/08/El-Fascinante-Mundo-del-Perro-Salchicha-Explorando-su-Historia-Crianza-y-Personalidad-Unica-Doggies-in-Town-1200x676-5.jpg");
		
		// Contacto
		usuarioActual.addContactoIndividual(u2, "Álvaro <3");
		
		// Mensajes
		usuarioActual.enviarMensaje(u1, "Hola");
		u2.enviarMensaje(usuarioActual, "¿Qué tal?");
		u3.enviarMensaje(usuarioActual, "Heyyyy");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		usuarioActual.enviarMensaje(u2, "Bien, ¿y tu?");*/
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
	
	public void enviarMensaje(Usuario emisor, Usuario receptor, String texto) {
		emisor.enviarMensaje(receptor, texto);
	}
	
	public List<Mensaje> getUltimosMensajes() {
		return usuarioActual.getUltimosMensajes();
	}
	
	public String getImagenDePerfil() {
		return usuarioActual.getImagenPerfil();
	}
	
	public String getNombreDeContacto(Usuario usuario) {
		String nombreDeContacto = usuarioActual.getNombreContacto(usuario);
		return nombreDeContacto == null ? Integer.toString(usuario.getNumeroTelefono()) : nombreDeContacto;
	}
	
	public boolean isUsuarioActual(Usuario usuario) {
		return usuarioActual.equals(usuario);
	}
	
	public List<Mensaje> getOtrosMensajes(Mensaje m){
		return usuarioActual.getOtrosMensajes(m);
	}
	
	public boolean login(int telf, String passw) {
		Optional<Usuario> optional_usuario = repositorioUsuarios.getUsuario(telf);
		if (optional_usuario.isPresent()) {
			Usuario u = optional_usuario.get();
			if (u.getPassword() == passw) {
				usuarioActual = u;
				return true;
			}
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
	}
	
	private void inicializarRepositorios() {
		repositorioUsuarios = RepositorioUsuarios.getUnicaInstancia();
	}
}
