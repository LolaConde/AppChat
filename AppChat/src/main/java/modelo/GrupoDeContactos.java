package modelo;

import java.util.List;

public class GrupoDeContactos extends Contacto{
	private List<ContactoIndividual> contactos;
	private String imagen;
	
	// Constructor
	public GrupoDeContactos(List<ContactoIndividual> contactos, String nombre, String imagen) {
		super(nombre);
		this.contactos = contactos;
		this.imagen = imagen;
	}
	
	// Constructor: sin imagen
	// La imagen es opcional
	public GrupoDeContactos(List<ContactoIndividual> contactos, String nombre) {
		this(contactos, nombre, null);
	}
	
	public List<ContactoIndividual> getContactos(){
		return this.contactos;
	}
	
	/**
	 * @return la URL de la imagen del grupo, o una cadena vacía si no tiene imagen.
	 */
	public String getImagen() {
		return this.imagen == null? "" : this.imagen;
	}
}