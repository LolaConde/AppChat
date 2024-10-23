package modelo;

import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {
	private List<Usuario> usuarios;
	
	public void addUsuario(Usuario u) {
		usuarios.add(u);
	}
	
	public void removeUsuario(Usuario u) {
		usuarios.remove(u);
	}
}
