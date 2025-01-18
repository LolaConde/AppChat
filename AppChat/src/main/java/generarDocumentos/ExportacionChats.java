package generarDocumentos;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import modelo.Mensaje;
import modelo.Usuario;
import tds.BubbleText;

public class ExportacionChats {
	
	private static ExportacionChats unicaInstancia;
	
	private ExportacionChats() {}
	
	public static ExportacionChats getUnicaInstancia() {
		if(unicaInstancia == null) {
			unicaInstancia = new ExportacionChats();
		}
		return unicaInstancia;
	}
	
	/**
	 * Exporta la conversación de <code>usuarioPrincipal</code> con <code>otroUsuario</code> usando el 
	 * generador de documentos pasado como parámetro.
	 * 
	 * @param usuarioPrincipal El usuario para el que se realiza el documento
	 * @param otroUsuario
	 * @param urlArchivo La url del archivo creado
	 * @param generadorDocumento
	 */
	public void exportarDocumento(Usuario usuarioPrincipal, Usuario otroUsuario, String urlArchivo, GeneradorDocumento generadorDocumento) {
		generadorDocumento.nuevoDocumento(urlArchivo);
		// Primera página
		generadorDocumento.añadirTextoNegrita("Conversación con el usuario: ");
		generadorDocumento.añadirTextoNormal(otroUsuario.getNombre() + " (");
		generadorDocumento.añadirTextoCursiva(Integer.toString(otroUsuario.getNumeroTelefono()));
		generadorDocumento.añadirTextoNormal(")");
		generadorDocumento.finalizarParrafo();
		generadorDocumento.siguientePagina();
		
		// Conversación
		usuarioPrincipal.getConversacion(otroUsuario).stream().forEach(m -> addMensaje(m, generadorDocumento));
		generadorDocumento.finalizarParrafo();
		generadorDocumento.finalizarDocumento();
	}
	
	/** Añade los datos de <code>mensaje</code> al documento que se está creando en <code>generadorDocumento</code>
	 * 
	 * @param mensaje
	 * @param generadorDocumento
	 */
	private void addMensaje(Mensaje mensaje, GeneradorDocumento generadorDocumento) {
		generadorDocumento.añadirTextoNegrita("Emisor: ");
		generadorDocumento.añadirTextoCursiva(mensaje.getEmisor().getNombre());
		generadorDocumento.finalizarParrafo();
		generadorDocumento.añadirTextoNegrita("Fecha: ");
		generadorDocumento.añadirTextoCursiva(mensaje.getFechaYHora().toString());
		generadorDocumento.finalizarParrafo();
		if(mensaje.isMensajeDeTexto()) {
			generadorDocumento.añadirTextoNegrita("Texto: ");
			generadorDocumento.añadirTextoCursiva(mensaje.getTexto().orElse(""));
		}
		else {
			generadorDocumento.añadirTextoNegrita("Emoticono: ");
			ImageIcon imageIcon = BubbleText.getEmoji(mensaje.getEmoticono().get());
			BufferedImage imagen = (BufferedImage) imageIcon.getImage();
			generadorDocumento.añadirImagen(imagen);
		}
		generadorDocumento.finalizarParrafo();
		generadorDocumento.añadirTextoNormal("------------------");
		generadorDocumento.finalizarParrafo();
		generadorDocumento.finalizarParrafo();
	}
}
