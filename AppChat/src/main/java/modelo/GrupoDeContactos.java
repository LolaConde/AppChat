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
	
	public String getImagen() {
		return this.imagen;
	}
}