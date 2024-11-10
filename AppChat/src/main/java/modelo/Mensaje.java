package modelo;

import java.time.LocalDateTime;
import java.util.Objects;

public class Mensaje{
    private Usuario emisor;
    private Usuario receptor;
    private LocalDateTime fechaYHora;
	private String texto;
    private Integer emoticono;
    
    public Usuario getEmisor() {
		return emisor;
	}

	public Usuario getReceptor() {
		return receptor;
	}

	public LocalDateTime getFechaYHora() {
		return fechaYHora;
	}

	public String getTexto() {
		return texto;
	}

	public Integer getEmoticono() {
		return emoticono;
	}
	
	public Integer getNumeroDelEmisor() {
		return emisor.getNumeroTelefono();
	}
	public Integer getNumeroDelReceptor() {
		return receptor.getNumeroTelefono();
	}
	public Usuario getOtro(Usuario user) {
		return user.equals(emisor) ? receptor : emisor;
	}
	public boolean isReceptor(Usuario user) {
		return user.equals(receptor);
	}

	// Constructor: Mensaje de texto
	public Mensaje(Usuario emisor, Usuario receptor, String texto) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.texto = texto;
		this.emoticono = null;
		this.fechaYHora = LocalDateTime.now(); // El mensaje tiene la fecha/hora actual
	}
	
	// Constructor: Mensaje de 1 emoticono
	public Mensaje(Usuario emisor, Usuario receptor, int emoticono) {
		this.emisor = emisor;
		this.receptor = receptor;
		this.emoticono = emoticono;
		this.texto = null;
		this.fechaYHora = LocalDateTime.now(); // El mensaje tiene la fecha/hora actual
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
