package persistencia;

import modelo.Contacto;

public interface IAdaptadorContactoDAO {
	public void registrarContacto(Contacto contacto);
	public Contacto recuperarContacto(int codigo);
}
