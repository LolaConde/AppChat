package modelo;

import java.time.LocalDateTime;

public class Mensaje {
    private Usuario emisor;
    private Usuario receptor;
    private LocalDateTime fechaYHora;
	private String texto;
    private Integer emoticono;
    
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
}
