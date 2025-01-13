package persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import modelo.Contacto;
import modelo.Descuento;
import modelo.Mensaje;
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
		Entidad eUsuario = null;
		
		// Si la entidad esta registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch(NullPointerException e) {}
		if (eUsuario != null) return;
		
		// registrar Contactos
		AdaptadorContactoTDS adaptadorContacto = AdaptadorContactoTDS.getUnicaInstancia();
		usuario.getContactos().stream().forEach(adaptadorContacto::registrarContacto);
		
		// Registrar Mensajes
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		usuario.getMensajesEnviados().stream().forEach(adaptadorMensaje::registrarMensaje);
		usuario.getMensajesRecibidos().stream().forEach(adaptadorMensaje::registrarMensaje);
		
		// Registrar Descuento
		AdaptadorDescuentoTDS adaptadorDescuento = AdaptadorDescuentoTDS.getUnicaInstancia();
		adaptadorDescuento.registrarDescuento(usuario.getDescuento());
		
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
						new Propiedad("isPremium", usuario.isPremium() ? "true" : "false"),
						new Propiedad("contactos", obtenerCodigosContactos(usuario.getContactos())),
						new Propiedad("mensajesEnviados", obtenerCodigosMensajes(usuario.getMensajesEnviados())),
						new Propiedad("mensajesRecibidos", obtenerCodigosMensajes(usuario.getMensajesRecibidos())),
						new Propiedad("fechaRegistro", usuario.getFechaRegistro().toString()),
						new Propiedad("descuento", Integer.toString(usuario.getDescuento().getCodigo()))
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
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}
	
	@Override
	public Usuario recuperarUsuario(int codigo) {
		// Si está en el pool, se retorna
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		Entidad eUsuario = servPersistencia.recuperarEntidad(codigo);

		// Recuperar propiedades que no son objetos
		String nombreCompleto = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombreCompleto");
		int numeroTelefono = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eUsuario, "numeroTelefono"));
		String password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		String email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		LocalDate fechaNacimiento = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"));
		String imagenPerfil = servPersistencia.recuperarPropiedadEntidad(eUsuario, "imagenPerfil");
		String saludo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "saludo");
		Boolean isPremium = servPersistencia.recuperarPropiedadEntidad(eUsuario, "isPremium").equals("true");
		LocalDate fechaRegistro = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaRegistro"));
		
		Usuario usuario = new Usuario(nombreCompleto, password, numeroTelefono, email, fechaNacimiento, imagenPerfil, saludo, fechaRegistro);
		usuario.setPremium(isPremium);
		usuario.setCodigo(codigo);
		
		// Añade usuario al pool
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);
		
		// Recuperar propiedades que son objetos
		List<Contacto> contactos = obtenerContactosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "contactos"));
		List<Mensaje> mensajesEnviados = this.obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "mensajesEnviados"));
		List<Mensaje> mensajesRecibidos = this.obtenerMensajesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "mensajesRecibidos"));
		Descuento descuento = AdaptadorDescuentoTDS.getUnicaInstancia().recuperarDescuento(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eUsuario, "descuento")));
		
		usuario.setContactos(contactos);
		usuario.setMensajesEnviados(mensajesEnviados);
		usuario.setMensajesRecibidos(mensajesRecibidos);
		usuario.setDescuento(descuento);
		
		return usuario;
	}
	
	private String obtenerCodigosContactos(List<Contacto> contactos) {
		String resultado = "";
		for (Contacto c : contactos) {
			resultado += c.getCodigo() + " ";
		}
		return resultado;
	}
	
	private List<Contacto> obtenerContactosDesdeCodigos(String codigos){
		List<Contacto> contactos = new LinkedList<Contacto>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorContactoTDS adaptadorContacto = AdaptadorContactoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			contactos.add(adaptadorContacto.recuperarContacto(Integer.valueOf((String) strTok.nextElement())));
		}
		return contactos;
	}
	
	private String obtenerCodigosMensajes(List<Mensaje> mensajes){
		String resultado = "";
		for(Mensaje mensaje : mensajes) {
			resultado += mensaje.getCodigo() + " ";
		}
		return resultado;
	}
	
	private List<Mensaje> obtenerMensajesDesdeCodigos(String codigos){
		List<Mensaje> mensajes = new LinkedList<Mensaje>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorMensajeTDS adaptadorMensaje = AdaptadorMensajeTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			mensajes.add(adaptadorMensaje.recuperarMensaje(Integer.valueOf((String) strTok.nextElement())));
		}
		return mensajes;
	}

	@Override
	public void modificarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if(prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(usuario.getCodigo()));
			}
			else if(prop.getNombre().equals("nombreCompleto")) {
				prop.setValor(usuario.getNombre());
			}
			else if(prop.getNombre().equals("numeroTelefono")) {
				prop.setValor(Integer.toString(usuario.getNumeroTelefono()));
			}
			else if(prop.getNombre().equals("password")) {
				prop.setValor(usuario.getPassword());
			}
			else if(prop.getNombre().equals("email")) {
				prop.setValor(usuario.getEmail());
			}
			else if(prop.getNombre().equals("fechaNacimiento")) {
				prop.setValor(usuario.getFechaNacimiento().toString());
			}
			else if(prop.getNombre().equals("imagenPerfil")) {
				prop.setValor(usuario.getImagenPerfil());
			}
			else if(prop.getNombre().equals("saludo")) {
				prop.setValor(usuario.getSaludo());
			}
			else if(prop.getNombre().equals("isPremium")) {
				prop.setValor(usuario.isPremium() ? "true" : "false");
			}
			else if(prop.getNombre().equals("contactos")) {
				prop.setValor(obtenerCodigosContactos(usuario.getContactos()));
			}
			else if(prop.getNombre().equals("mensajesEnviados")) {
				prop.setValor(obtenerCodigosMensajes(usuario.getMensajesEnviados()));
			}
			else if(prop.getNombre().equals("mensajesRecibidos")) {
				prop.setValor(obtenerCodigosMensajes(usuario.getMensajesRecibidos()));
			}
			else if(prop.getNombre().equals("fechaRegistro")) {
				prop.setValor(usuario.getFechaRegistro().toString());
			} else if(prop.getNombre().equals("descuento")) {
				prop.setValor(Integer.toString(usuario.getDescuento().getCodigo()));
			}
			servPersistencia.modificarPropiedad(prop);
		}
		
	}
}
