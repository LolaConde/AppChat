package modelo;

public abstract class Contacto {
    private String nombre;
    private int codigo;
    
    public Contacto(String nombre) {
    	this.nombre = nombre;
    }
    
    public String getNombre() {
    	return nombre;
    }
    
    public int getCodigo() {
    	return this.codigo;
    }
    
    public void setCodigo(int codigo) {
    	this.codigo = codigo;
    }
  
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    /**
     * @return la URL de la imagen del usuario si es un contacto individual, o la URL de la imagen del grupo si es un grupo de contactos
     */
    public abstract String getImagen();
}