package modelo;

public class ContactoIndividual extends Contacto{
    private Usuario usuario;
    
    public ContactoIndividual(Usuario usuario, String nombre) {
    	super(nombre);
    	this.usuario = usuario;
    }
    
    public boolean isUsuario(Usuario usuario) {
    	return this.usuario.equals(usuario);
    }
    
    public Usuario getUsuario() {
    	return this.usuario;
    }
    
    public String getImagen() {
    	return usuario.getImagenPerfil();
    }
}