package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioUsuarios {
	
	private static RepositorioUsuarios unicaInstancia;
	private Map<Integer, Usuario> usuarios;
	
	private RepositorioUsuarios() {
		usuarios = new HashMap<Integer, Usuario>();
	}
	
	public static RepositorioUsuarios getUnicaInstancia() {
		if(unicaInstancia == null) {
			unicaInstancia = new RepositorioUsuarios();
		}
		return unicaInstancia;
	}
	
	public void addUsuario(Usuario u) {
		usuarios.put(u.getNumeroTelefono(), u);
	}
}
