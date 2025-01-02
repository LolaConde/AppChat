package persistencia;

import java.util.List;

import modelo.Usuario;

public interface IAdaptadorUsuarioDAO {
	public void registrarUsuario(Usuario usuario);
	public List<Usuario> recuperarTodosUsuarios();
	public Usuario recuperarUsuario(int codigo);
	public void modificarUsuario(Usuario usuario);
}
