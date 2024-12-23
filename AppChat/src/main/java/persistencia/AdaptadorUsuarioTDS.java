package persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import modelo.Usuario;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static AdaptadorUsuarioTDS unicaInstancia;
	private static ServicioPersistencia servPersistencia;
	
	public static AdaptadorUsuarioTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorUsuarioTDS();
		return unicaInstancia;
	}
	
	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void registrarUsuario(Usuario usuario) {
		// TODO Add mensajesEnviados, mensajesRecibidos, Descuento, 
		Entidad eUsuario = null;
		
		// Si la entidad esta registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch(NullPointerException e) {}
		if (eUsuario != null) return;
		
		// Crear entidad Cliente
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombreCompleto", usuario.getNombre()),
						new Propiedad("numeroTelefono", Integer.toString(usuario.getNumeroTelefono())),
						new Propiedad("password", usuario.getPassword()),
						new Propiedad("email", usuario.getEmail()),
						new Propiedad("fechaNacimiento", usuario.getFechaNacimiento().toString()),
						new Propiedad("imagenPerfil", usuario.getImagenPerfil()),
						new Propiedad("saludo", usuario.getSaludo()),
						new Propiedad("isPremium", usuario.isPremium() ? "true" : "false")
						)));
		
		// Registrar entidad cliente
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// Asigna el código al usuario
		usuario.setCodigo(eUsuario.getId());
	}

	@Override
	public List<Usuario> recuperarTodosUsuarios() {
		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new ArrayList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarCliente(eUsuario.getId()));
		}
		return usuarios;
	}
	
	public Usuario recuperarCliente(int codigo) {

		// Si la entidad est� en el pool la devuelve directamente
		/*if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);*/

		// si no, la recupera de la base de datos
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		String nombreCompleto = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombreCompleto");
		int numeroTelefono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eUsuario, "numeroTelefono"));
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		LocalDate fechaNacimiento = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		String imagenPerfil = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagenPerfil");
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		Boolean isPremium = servPersistencia.recuperarPropiedadEntidad(eUsuario, "isPremium").equals("true");
		
		Usuario usuario = new Usuario(nombreCompleto, password, numeroTelefono, email, fechaNacimiento, imagenPerfil, saludo);
		usuario.setPremium(isPremium);
		usuario.setCodigo(codigo);

		// IMPORTANTE:a�adir el cliente al pool antes de llamar a otros
		// adaptadores
		//PoolDAO.getUnicaInstancia().addObjeto(codigo, cliente);

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		//ventas = obtenerVentasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "ventas"));

		/*for (Venta v : ventas)
			cliente.addVenta(v);*/

		return usuario;
	}
	
}
