package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
	// Datos
    private String nombreCompleto;
    private String password;
    private int numeroTelefono;
    private String email;
    private LocalDate fechaNacimiento;
    private String imagenPerfil; // URL de la imagen
    private String saludo;
    private static final String SALUDO_POR_DEFECTO = "Hi there! I'm using AppChat";
    
    // Mensajes
    private List<Mensaje> mensajesEnviados;
    private List<Mensaje> mensajesRecibidos;
    
    // Contactos
    private List<Contacto> contactos;
    
    // Premium
    private boolean isPremium;
    private Descuento descuento;
    
    // Constructor
    public Usuario(String nombreCompleto, String password, int numeroTelefono, String email, LocalDate fechaNacimiento,
			String imagenPerfil, String saludo) {
    	//Datos
		this.nombreCompleto = nombreCompleto;
		this.password = password;
		this.numeroTelefono = numeroTelefono;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.imagenPerfil = imagenPerfil;
		this.saludo = saludo;
		// Mensajes y contactos
		this.mensajesEnviados = new ArrayList<Mensaje>();
		this.mensajesRecibidos = new ArrayList<Mensaje>();
		this.contactos = new ArrayList<Contacto>();
		//Premium
		this.isPremium = false;
		this.descuento = null;
	}
    
    // Constructor: sin saludo
    // Para registrarse en el sistema, un usuario debe indicar ... y un mensaje de saludo opcional
    public Usuario(String nombreCompleto, String password, int numeroTelefono, String email, LocalDate fechaNacimiento,
			String imagenPerfil) {
    	this(nombreCompleto, password, numeroTelefono, email, fechaNacimiento, imagenPerfil, SALUDO_POR_DEFECTO);
    }
    
    public void setImagenPerfil(String imagen) {
    	this.imagenPerfil = imagen;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
    	if (obj == null || getClass() != obj.getClass()) return false;
    	
    	Usuario otroUsuario = (Usuario) obj;
    	
    	return otroUsuario.numeroTelefono == this.numeroTelefono;
    }
    @Override
    public int hashCode() {
    	return numeroTelefono;
    }
}
