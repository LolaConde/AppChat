package generarDocumentos;

import java.awt.image.BufferedImage;

public abstract class GeneradorDocumento {
	/**
	 * Se empieza a crear un documento en la ruta <code>url</code>
	 * @param url
	 * @return true si se ha podido empezar el documento. false en caso contrario.
	 */
	public abstract boolean nuevoDocumento(String url);
	
	/**
	 * Se añaden los metadatos al documento que se está creando actualmente.
	 * @param titulo
	 * @param autor
	 * @param asunto
	 * @param palabrasClave
	 */
	public abstract void añadirMetadatos(String titulo, String autor, String asunto, String palabrasClave);
	
	/**
	 * Añade la cadena <code>texto</code> al párrafo actual.
	 * @param texto
	 */
	public abstract void añadirTextoNormal(String texto);
	
	/**
	 * Añade la cadena <code>texto</code> en negrita al párrafo actual.
	 * @param texto
	 */
	public abstract void añadirTextoNegrita(String texto);
	
	/**
	 * Añade la cadena <code>texto</code> en cursiva al párrafo actual.
	 * @param texto
	 */
	public abstract void añadirTextoCursiva(String texto);
	
	/**
	 * Añade la imagen <code>imagen</code> al párrafo actual.
	 * @param imagen
	 */
	public abstract void añadirImagen(BufferedImage imagen);
	
	/**
	 * Finaliza el párrafo actual. Al añadir más elementos, estos se añadirán al siguiente párrafo.
	 */
	public abstract void finalizarParrafo();
	
	/**
	 * Finaliza la página actual. Al añadir más elementos, estos se añadirán en la siguiente página.
	 */
	public abstract void siguientePagina();
	
	/**
	 * Termina de crear el documento actual. Es necesario llamar al método después de crear el documento.
	 */
	public abstract void finalizarDocumento();
}
