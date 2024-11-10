package modelo;

public abstract class Contacto {
    private String nombre;
    
    // Constructor
    public Contacto(String nombre) {
    	this.nombre = nombre;
    }
    
    public String getNombre() {
    	return nombre;
    }
}