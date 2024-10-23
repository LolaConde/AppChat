package modelo;

public class ContactoIndividual extends Contacto{
    private Usuario usuario;
    
    // Constructor
    public ContactoIndividual(Usuario usuario, String nombre) {
    	super(nombre);
    	this.usuario = usuario;
    }
}