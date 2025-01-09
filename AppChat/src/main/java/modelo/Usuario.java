package modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    
    // Codigo
    private int codigo;
    
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
		// Codigo
		this.codigo = 0;
	}
    
    // Constructor: sin saludo
    // Para registrarse en el sistema, un usuario debe indicar ... y un mensaje de saludo opcional
    public Usuario(String nombreCompleto, String password, int numeroTelefono, String email, LocalDate fechaNacimiento,
			String imagenPerfil) {
    	this(nombreCompleto, password, numeroTelefono, email, fechaNacimiento, imagenPerfil, SALUDO_POR_DEFECTO);
    }
    
    public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}
    
    public int getNumeroTelefono() {
    	return numeroTelefono;
    }
    
    public boolean isPremium() {
    	return this.isPremium;
    }
    
    public void setImagenPerfil(String imagen) {
    	this.imagenPerfil = imagen;
    }
    
    public String getImagenPerfil() {
    	return this.imagenPerfil;
    }
    public Optional<String> getNombreContacto(Usuario usuario) {
    	for (Contacto c : contactos) {
    		if (c instanceof ContactoIndividual && ((ContactoIndividual) c).isUsuario(usuario)) {
    			return Optional.of(c.getNombre());
    		}
    	}
    	return Optional.empty();
    }
    
    public String getNombre() {
    	return this.nombreCompleto;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    
    public String getEmail() {
    	return this.email;
    }
    
    public LocalDate getFechaNacimiento() {
    	return this.fechaNacimiento;
    }
    
    public String getSaludo() {
    	return this.saludo;
    }
    
    public ContactoIndividual addContactoIndividual(Usuario usuario, String nombre) {
    	ContactoIndividual contacto = new ContactoIndividual(usuario, nombre);
    	contactos.add(contacto);
    	return contacto;
    }
    
    public Optional<ContactoIndividual> actualizarContactoIndividual(Usuario usuario, String nombre) {
    	// Si el contacto ya existía, se cambia su nombre
    	Optional<ContactoIndividual> contactoExistente = contactos.stream()
    			.filter(c -> c instanceof ContactoIndividual)
    			.map(c -> (ContactoIndividual) c)
    			.filter(c -> c.getUsuario().equals(usuario))
    			.findAny();
    	if(contactoExistente.isPresent()) {
    		ContactoIndividual contacto = contactoExistente.get();
    		contacto.setNombre(nombre);
    		return Optional.of(contacto);
    	}
    	return Optional.empty();
    }
    
    public GrupoDeContactos addGrupoDeContactos(List<ContactoIndividual> contactosDelGrupo, String nombre, String imagen) {
    	GrupoDeContactos grupo = new GrupoDeContactos(contactosDelGrupo, nombre, imagen);
    	contactos.add(grupo);
    	return grupo;
    }
    
    public Mensaje enviarMensaje(Usuario usuario, String texto) {
    	Mensaje mensaje = new Mensaje(this, usuario, texto, LocalDateTime.now());
    	this.mensajesEnviados.add(mensaje);
    	usuario.recibirMensaje(mensaje);
    	return mensaje;
    }
    
    public Mensaje enviarMensaje(Usuario usuario, int emoticono) {
    	Mensaje mensaje = new Mensaje(this, usuario, emoticono, LocalDateTime.now());
    	this.mensajesEnviados.add(mensaje);
    	usuario.recibirMensaje(mensaje);
    	return mensaje;
    }
    
    public List<Mensaje> getMensajesEnviados() {
		return mensajesEnviados;
	}

	public List<Mensaje> getMensajesRecibidos() {
		return mensajesRecibidos;
	}

	public List<Contacto> getContactos() {
		return contactos;
	}

	public Descuento getDescuento() {
		return descuento;
	}

	public void setMensajesEnviados(List<Mensaje> mensajesEnviados) {
		this.mensajesEnviados = mensajesEnviados;
	}

	public void setMensajesRecibidos(List<Mensaje> mensajesRecibidos) {
		this.mensajesRecibidos = mensajesRecibidos;
	}

	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}

	public void setDescuento(Descuento descuento) {
		this.descuento = descuento;
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
    		.sorted((m1, m2) -> - m1.getFechaYHora().compareTo(m2.getFechaYHora()))
    		.collect(Collectors.toList());
    }
  
    
    public List<Mensaje> getOtrosMensajes(Mensaje mensaje){
    	return Stream.of(mensajesEnviados, mensajesRecibidos)
    			.flatMap(listaMensajes -> listaMensajes.stream())
    			.filter(m -> m.isMismaConversacion(mensaje))
    			.sorted((m1, m2) -> m1.getFechaYHora().compareTo(m2.getFechaYHora()))
    			.collect(Collectors.toList());
    }
    
    public void setPremium(boolean b) {
    	isPremium = b;
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
