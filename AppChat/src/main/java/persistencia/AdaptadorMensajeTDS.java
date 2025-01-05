package persistencia;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Mensaje;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorMensajeTDS implements IAdaptadorMensajeDAO {
	private static AdaptadorMensajeTDS unicaInstancia;
	private static ServicioPersistencia servPersistencia;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
	
	public static AdaptadorMensajeTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new AdaptadorMensajeTDS();
		}
		return unicaInstancia;
	}
	
	private AdaptadorMensajeTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarMensaje(Mensaje mensaje) {
		Entidad eMensaje = null;
		
		// Si la entidad esta registrada no se registra de nuevo
		try {
			eMensaje = servPersistencia.recuperarEntidad(mensaje.getCodigo());
		}catch(NullPointerException e) {}
		if (eMensaje != null) return;
		
		// Crear entidad Mensaje
		eMensaje = new Entidad();
		eMensaje.setNombre("mensaje");
		
		// registrar emisor y receptor
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		adaptadorUsuario.registrarUsuario(mensaje.getEmisor());
		adaptadorUsuario.registrarUsuario(mensaje.getReceptor());
		
		// Meter propiedades al mensaje
		eMensaje.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(	new Propiedad("emisor", String.valueOf(mensaje.getEmisor().getCodigo())),
								new Propiedad("receptor", String.valueOf(mensaje.getReceptor().getCodigo())),
								new Propiedad("fechaYHora", mensaje.getFechaYHora().format(FORMATTER)),
								new Propiedad("texto", mensaje.getTexto().isPresent() ? mensaje.getTexto().get() : ""),
								new Propiedad("emoticono", mensaje.getEmoticono().isPresent() ? mensaje.getEmoticono().get().toString() : "")
						)));
		
		// Registrar entidad mensaje
		eMensaje = servPersistencia.registrarEntidad(eMensaje);
		// Asigna el código al mensaje
		mensaje.setCodigo(eMensaje.getId());
	}

	@Override
	public Mensaje recuperarMensaje(int codigo) {
		// Si está en el pool, se retorna
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Mensaje) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		// Recupera el mensaje de la base de datos
		Entidad eMensaje = servPersistencia.recuperarEntidad(codigo);
		
		// Recuperar propiedades que no son objetos
		LocalDateTime fecha = LocalDateTime.parse(servPersistencia.recuperarPropiedadEntidad(eMensaje, "fechaYHora"), FORMATTER);
		String texto = servPersistencia.recuperarPropiedadEntidad(eMensaje, "texto");
		String emoticonoString = servPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono");
		Integer emoticono;
		
		// Recuperar propiedades que son objetos
		AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
		int codigoEmisor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor"));
		Usuario emisor = adaptadorUsuario.recuperarUsuario(codigoEmisor);
		int codigoReceptor = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor"));
		Usuario receptor = adaptadorUsuario.recuperarUsuario(codigoReceptor);
		
		// Crear mensaje
		Mensaje mensaje;
		if(emoticonoString.equals("")) {
			mensaje = new Mensaje(emisor, receptor, texto, fecha);
		}
		else {
			emoticono = Integer.valueOf(emoticonoString);
			mensaje = new Mensaje(emisor, receptor, emoticono, fecha);
		}
		mensaje.setCodigo(codigo);
		return mensaje;
	}

}
