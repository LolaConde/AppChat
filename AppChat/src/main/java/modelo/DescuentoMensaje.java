package modelo;

public class DescuentoMensaje extends Descuento{
	
	private Double porcentajeDescuento;
	private int numMensajes;
	
	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public int getNumMensajes() {
		return numMensajes;
	}
	
	/**
	 * Se crea un descuento del <code>porcentaje</code> por ciento porque el usuario envió
	 * más de <code>numMensajes</code> en el último mes.
	 * 
	 * @param porcentaje
	 * @param numMensajes
	 */
	public DescuentoMensaje(Double porcentaje, int numMensajes) {
		this.porcentajeDescuento = porcentaje;
		this.numMensajes = numMensajes;
	}
	
    public double calcularDescuento(double precio) {
    	 return precio - precio*porcentajeDescuento/100;
    }

	@Override
	public boolean esAplicable(Usuario usuario) {
		return usuario.contarMensajesEnviadosEsteMes() > numMensajes;
	}
	
	@Override
	public String toString() {
		return "Descuento por enviar más de " + numMensajes + " mensajes el último mes.";
	}
}