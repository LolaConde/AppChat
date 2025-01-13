package persistencia;

import modelo.Descuento;

public interface IAdaptadorDescuentoDAO {
	public void registrarDescuento(Descuento descuento);
	public Descuento recuperarDescuento(int codigo);
}
