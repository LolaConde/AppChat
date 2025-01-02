package modelo;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class Mensaje{
    private final Usuario emisor;
    private final Usuario receptor;
    private final LocalDateTime fechaYHora;
	private final String texto; // Podría ser null
    private final Integer emoticono; //  Podría ser null
    private int codigo;
    
    // Getters y setters
    public Usuario getEmisor() {
		return emisor;
	}
    
    public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public LocalDateTime getFechaYHora() {
		return fechaYHora;
	}

	public Optional<String> getTexto() {
		return Optional.ofNullable(texto);
	}

	public Optional<Integer> getEmoticono() {
		return Optional.ofNullable(emoticono);
	}
	
	public boolean isMensajeDeTexto() {
		return null != texto;
	}
	
	// Otros métodos
	
	public Integer getNumeroDelEmisor() {
		return emisor.getNumeroTelefono();
	}
	public Integer getNumeroDelReceptor() {
		return receptor.getNumeroTelefono();
	}
	public Usuario obtenerElOtroUsuario(Usuario user) {
		return user.equals(emisor) ? receptor : emisor;
	}
	public boolean isReceptor(Usuario user) {
		return receptor.equals(user);
	}
	
	public boolean isEmisor(Usuario user) {
		return emisor.equals(user);
	}
	
	public boolean isMismaConversacion(Mensaje m) {
		return (m.isEmisor(emisor) && m.isReceptor(receptor) || (m.isEmisor(receptor) && m.isReceptor(emisor)));
	}

	// Constructor: Mensaje de texto
	public Mensaje(Usuario emisor, Usuario receptor, String texto, LocalDateTime fechaYHora) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.texto = texto;
		this.emoticono = null;
		this.fechaYHora = fechaYHora;
		this.codigo = 0;
	}
	
	// Constructor: Mensaje de 1 emoticono
	public Mensaje(Usuario emisor, Usuario receptor, int emoticono, LocalDateTime fechaYHora) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.emoticono = emoticono;
		this.texto = null;
		this.fechaYHora = fechaYHora;
		this.codigo = 0;
	}
	

    @Override
    public boolean equals(Object obj) {
    	if (this == obj) return true;
    	if (obj == null || getClass() != obj.getClass()) return false;
    	
    	Mensaje m = (Mensaje) obj;
    	
    	return m.emisor.equals(this.emisor) 
    			&& m.receptor.equals(this.receptor) 
    			&& m.texto != null ? m.texto.equals(this.texto) : null == this.texto
    			&& m.emoticono != null ? m.emoticono.equals(this.emoticono) : null == this.emoticono
    			&& m.fechaYHora.equals(this.fechaYHora);
    }
    @Override
    public int hashCode() {
    	return Objects.hash(emisor, receptor, texto, emoticono, fechaYHora);
    }
}
