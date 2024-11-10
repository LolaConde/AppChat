package controlador;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import modelo.GeneradorPDF;
import modelo.Mensaje;
import modelo.RepositorioUsuarios;
import modelo.Usuario;

public class Controlador {
	private static Usuario usuarioActual;
	private RepositorioUsuarios repositorioUsuarios;
	private GeneradorPDF servicioPDF;
	
	public static Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	static {
		// Añadir ejemplo
		
		// Usuario actual
		usuarioActual = new Usuario("Lola Conde", "password", 12345678, "lola@gmail.com", LocalDate.now(), "https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg");
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
		usuarioActual.enviarMensaje(u2, "Bien, ¿y tu?");
		
	}
	
	public void enviarMensaje(Usuario emisor, Usuario receptor, String texto) {
		emisor.enviarMensaje(receptor, texto);
	}
	
	public static List<Mensaje> getUltimosMensajes() {
		return usuarioActual.getUltimosMensajes();
	}
	
	public static String getNombreDeContacto(Usuario usuario) {
		String nombreDeContacto = usuarioActual.getNombreContacto(usuario);
		return nombreDeContacto == null ? Integer.toString(usuario.getNumeroTelefono()) : nombreDeContacto;
	}
	
	public static boolean isUsuarioActual(Usuario usuario) {
		return usuarioActual.equals(usuario);
	}
}
