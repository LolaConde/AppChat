package persistencia;

import java.util.List;

import modelo.Mensaje;
import modelo.Usuario;

public interface IAdaptadorMensajeDAO {
	public void registrarMensaje(Mensaje mensaje);
	public Mensaje recuperarMensaje(int codigo);
}
