package modelo;

public abstract class Descuento {
	
	private int codigo;
	
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * @param precio
	 * @return Precio final una vez se aplica el descuento a <code>precio</code>
	 */
	public abstract double calcularDescuento(double precio);
	
	/**
	 * @param usuario
	 * @return true si el descuento es aplicable a <code>usuario</code>. false si no lo es.
	 */
	public abstract boolean esAplicable(Usuario usuario);
}
