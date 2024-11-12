package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    
    public int getNumeroTelefono() {
    	return numeroTelefono;
    }
    
    public void setImagenPerfil(String imagen) {
    	this.imagenPerfil = imagen;
    }
    
    public String getImagenPerfil() {
    	return this.imagenPerfil;
    }
    public String getNombreContacto(Usuario usuario) {
    	for (Contacto c : contactos) {
    		if (c instanceof ContactoIndividual && ((ContactoIndividual) c).isUsuario(usuario)) {
    			return c.getNombre();
    		}
    	}
    	return null;
    }
    
    public void addContactoIndividual(Usuario usuario, String nombre) {
    	contactos.add(new ContactoIndividual(usuario, nombre));
    }
    
    public void enviarMensaje(Usuario usuario, String texto) {
    	Mensaje mensaje = new Mensaje(this, usuario, texto);
    	this.mensajesEnviados.add(mensaje);
    	usuario.recibirMensaje(mensaje);
    }
    
    public void recibirMensaje(Mensaje mensaje) {
    	if (mensaje.isReceptor(this)) {
    		this.mensajesRecibidos.add(mensaje);
    	}
    }
    
    public List<Mensaje> getUltimosMensajes() {
    	Map<Usuario, List<Mensaje>> mensajesPorUsuario = Stream.of(mensajesEnviados, mensajesRecibidos)
    			.flatMap(listaMensajes -> listaMensajes.stream())
    			.sorted((m1, m2) -> - m1.getFechaYHora().compareTo(m2.getFechaYHora()))
    			.collect(Collectors.groupingBy(m -> m.obtenerElOtroUsuario(this)));

    	return mensajesPorUsuario.values().stream()
    		.map(lista -> lista.get(0))
    		.collect(Collectors.toList());
    }
  
    
    public List<Mensaje> getOtrosMensajes(Mensaje mensaje){
    	return Stream.of(mensajesEnviados, mensajesRecibidos)
    			.flatMap(listaMensajes -> listaMensajes.stream())
    			.filter(m -> m.isMismaConversacion(mensaje))
    			.sorted((m1, m2) -> m1.getFechaYHora().compareTo(m2.getFechaYHora()))
    			.collect(Collectors.toList());
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
