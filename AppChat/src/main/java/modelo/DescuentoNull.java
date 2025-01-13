package modelo;

public class DescuentoNull extends Descuento {

	@Override
	public double calcularDescuento(double precio) {
		return precio;
	}

	@Override
	public boolean esAplicable(Usuario usuario) {
		return true;
	}
	
	@Override
	public String toString() {
		return "No se ha aplicado ningún descuento";
	}

}
