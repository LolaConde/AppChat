package persistencia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.GrupoDeContactos;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorContactoTDS implements IAdaptadorContactoDAO {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorContactoTDS unicaInstancia;
	
	public static AdaptadorContactoTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new AdaptadorContactoTDS();
		}
		return unicaInstancia;
	}
	
	private AdaptadorContactoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarContacto(Contacto contacto) {
		Entidad eContacto = null;
		
		// Si la entidad esta registrada, no se registra de nuevo
		try {
			eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		}catch(NullPointerException e) {}
		if (eContacto != null) return;
		
		// Crear entidad Contacto
		eContacto = new Entidad();
		eContacto.setNombre("contacto");
		
		// Propiedades de contacto
		ArrayList<Propiedad> propiedades = new ArrayList<Propiedad>();
		propiedades.add(new Propiedad("nombre", contacto.getNombre()));
		
		// Propiedades de la subclase
		if(contacto.getClass().getName().equals("modelo.ContactoIndividual")) {
			// Registrar el usuario
			Usuario usuario = ((ContactoIndividual) contacto).getUsuario();
			AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
			adaptadorUsuario.registrarUsuario(usuario);
			
			// Propiedades
			propiedades.add(new Propiedad("usuario", String.valueOf(usuario.getCodigo())));
			propiedades.add(new Propiedad("imagen", ""));
			propiedades.add(new Propiedad("contactos", ""));
		}
		else {
			// Registrar contactos
			for (Contacto c : ((GrupoDeContactos) contacto).getContactos()) {
				this.registrarContacto(c);
			}
			
			// Propiedades
			propiedades.add(new Propiedad("imagen", ((GrupoDeContactos) contacto).getImagen()));
			propiedades.add(new Propiedad("contactos", obtenerCodigosContactos(((GrupoDeContactos) contacto).getContactos())));
			propiedades.add(new Propiedad("usuario", ""));
		}
		
		// Añadir propiedades
		eContacto.setPropiedades(propiedades);
		
		// Registrar entidad contacto
		eContacto = servPersistencia.registrarEntidad(eContacto);
		// Asigna el código al contacto
		contacto.setCodigo(eContacto.getId());
	}
	
	@Override
	public void modificarContacto(Contacto contacto) {
		Entidad eContacto = servPersistencia.recuperarEntidad(contacto.getCodigo());
		for(Propiedad prop : eContacto.getPropiedades()) {
			if(prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(contacto.getCodigo()));
			}
			else if(prop.getNombre().equals("nombre")) {
				prop.setValor(contacto.getNombre());
			}else if(prop.getNombre().equals("usuario")) {
				if(contacto instanceof ContactoIndividual) {
					Usuario usuario = ((ContactoIndividual) contacto).getUsuario();
					prop.setValor(String.valueOf(usuario.getCodigo()));
				} else {
					prop.setValor("");
				}
			}else if(prop.getNombre().equals("imagen")) {
				if(contacto instanceof GrupoDeContactos) {
					prop.setValor(((GrupoDeContactos) contacto).getImagen());
				}else {
					prop.setValor("");
				}
			}else if(prop.getNombre().equals("contactos")) {
				if(contacto instanceof GrupoDeContactos) {
					prop.setValor(obtenerCodigosContactos(((GrupoDeContactos) contacto).getContactos()));
				}
				else {
					prop.setValor("");
				}
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Contacto recuperarContacto(int codigo) {
		// Si está en el pool, se retorna
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Contacto) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		// Recuperar el contacto de la base de datos
		Entidad eContacto = servPersistencia.recuperarEntidad(codigo);
		
		// Recuperar propiedades de contacto
		String nombre = servPersistencia.recuperarPropiedadEntidad(eContacto, "nombre");
		String imagen = servPersistencia.recuperarPropiedadEntidad(eContacto, "imagen");
		List<ContactoIndividual> contactos = null;
		Usuario usuario = null;
		
		// Crear contacto según sea individual o grupal
		if (servPersistencia.recuperarPropiedadEntidad(eContacto, "usuario").equals("")) {
			// Es un contacto grupal
			contactos = obtenerContactosIndDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eContacto, "contactos"));
			GrupoDeContactos grupo = new GrupoDeContactos(contactos, nombre, imagen);
			grupo.setCodigo(codigo);
			// Añade contacto al pool
			PoolDAO.getUnicaInstancia().addObjeto(codigo, grupo);
			return grupo;
		}
		else {
			// Es un contacto individual
			AdaptadorUsuarioTDS adaptadorUsuario = AdaptadorUsuarioTDS.getUnicaInstancia();
			usuario = adaptadorUsuario.recuperarUsuario(Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eContacto, "usuario")));
			ContactoIndividual contacto = new ContactoIndividual(usuario, nombre);
			contacto.setCodigo(codigo);
			// Añade contacto al pool
			PoolDAO.getUnicaInstancia().addObjeto(codigo, contacto);
			return contacto;
		}
	}
	
	private String obtenerCodigosContactos(List<ContactoIndividual> contactos) {
		String resultado = "";
		for (Contacto c : contactos) {
			resultado += c.getCodigo() + " ";
		}
		return resultado;
	}
	
	private List<ContactoIndividual> obtenerContactosIndDesdeCodigos(String codigos){
		List<ContactoIndividual> contactos = new LinkedList<ContactoIndividual>();
		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		while(strTok.hasMoreTokens()) {
			int codigo = Integer.valueOf((String) strTok.nextElement());
			Contacto contacto = this.recuperarContacto(codigo);
			if (contacto instanceof ContactoIndividual) {
				contactos.add((ContactoIndividual) contacto);
			}
			else {
				System.err.println("En los códigos de contactos individuales de un contacto grupal (en la base de datos), hay un código que no se corresponde con un contacto individual. Este código es " + codigo);
			}
		}
		return contactos;
	}
}
