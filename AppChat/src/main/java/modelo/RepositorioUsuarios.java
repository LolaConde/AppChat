package modelo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;

public class RepositorioUsuarios {
	
	private static RepositorioUsuarios unicaInstancia;
	private Map<Integer, Usuario> usuarios;
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private RepositorioUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<Integer, Usuario>();
  			this.cargarRepositorio();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static RepositorioUsuarios getUnicaInstancia() {
		if(unicaInstancia == null) {
			unicaInstancia = new RepositorioUsuarios();
		}
		return unicaInstancia;
	}
	
	public Optional<Usuario> getUsuario(int telf) {
		return Optional.ofNullable(usuarios.get(telf));
	}
	
	public void addUsuario(Usuario u) {
		usuarios.put(u.getNumeroTelefono(), u);
	}
	
	/*Recupera todos los clientes para trabajar con ellos en memoria*/
	private void cargarRepositorio() throws DAOException {
		 List<Usuario> usuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario u: usuariosBD) 
			     usuarios.put((u.getNumeroTelefono()), u);
	}
}
