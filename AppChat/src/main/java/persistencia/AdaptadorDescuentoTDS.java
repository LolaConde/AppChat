package persistencia;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import modelo.Contacto;
import modelo.ContactoIndividual;
import modelo.Descuento;
import modelo.DescuentoFecha;
import modelo.DescuentoMensaje;
import modelo.DescuentoNull;
import modelo.GrupoDeContactos;
import modelo.Usuario;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorDescuentoTDS implements IAdaptadorDescuentoDAO {
	
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorDescuentoTDS unicaInstancia;
	
	public static AdaptadorDescuentoTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new AdaptadorDescuentoTDS();
		}
		return unicaInstancia;
	}
	
	private AdaptadorDescuentoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}


	@Override
	public void registrarDescuento(Descuento descuento) {
		Entidad eDescuento = null;
		
		// Si la entidad esta registrada, no se registra de nuevo
		try {
			eDescuento = servPersistencia.recuperarEntidad(descuento.getCodigo());
		}catch(NullPointerException e) {}
		if (eDescuento != null) return;
		
		// Crear entidad Descuento
		eDescuento = new Entidad();
		ArrayList<Propiedad> propiedades = new ArrayList<Propiedad>();
		
		if(descuento.getClass().getName().equals("modelo.DescuentoNull")) {
			eDescuento.setNombre("DescuentoNull");
		}
		else if(descuento.getClass().getName().equals("modelo.DescuentoFecha")){
			DescuentoFecha descuentoFecha = (DescuentoFecha) descuento;
			eDescuento.setNombre("DescuentoFecha");
			
			propiedades.add(new Propiedad("fechaDesde", descuentoFecha.getFechaDesde().toString()));
			propiedades.add(new Propiedad("fechaHasta", descuentoFecha.getFechaHasta().toString()));
			propiedades.add(new Propiedad("porcentajeDescuento", descuentoFecha.getPorcentajeDescuento().toString()));
		}
		else if(descuento.getClass().getName().equals("modelo.DescuentoMensaje")) {
			DescuentoMensaje descuentoMensaje = (DescuentoMensaje) descuento;
			eDescuento.setNombre("DescuentoMensaje");
			
			propiedades.add(new Propiedad("numMensajes", Integer.toString(descuentoMensaje.getNumMensajes())));
			propiedades.add(new Propiedad("porcentajeDescuento", descuentoMensaje.getPorcentajeDescuento().toString()));
		}
		
		eDescuento.setPropiedades(propiedades);
		
		// Registrar entidad descuento
		eDescuento = servPersistencia.registrarEntidad(eDescuento);
		
		// Asigna el código al descuento
		descuento.setCodigo(eDescuento.getId());

	}

	@Override
	public Descuento recuperarDescuento(int codigo){
		// Si está en el pool, se retorna
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Descuento) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		
		// Recuperar el descuento de la base de datos
		Entidad eDescuento = servPersistencia.recuperarEntidad(codigo);
		
		if (eDescuento.getNombre().equals("DescuentoNull")) {
			Descuento descuento = new DescuentoNull();
			descuento.setCodigo(codigo);
			
			PoolDAO.getUnicaInstancia().addObjeto(codigo, descuento);
			return descuento;
		}
		else if(eDescuento.getNombre().equals("DescuentoFecha")){
			LocalDate fechaDesde = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eDescuento, "fechaDesde"));
			LocalDate fechaHasta = LocalDate.parse(servPersistencia.recuperarPropiedadEntidad(eDescuento, "fechaHasta"));
			Double porcentajeDescuento = Double.valueOf(servPersistencia.recuperarPropiedadEntidad(eDescuento, "porcentajeDescuento"));
			Descuento descuento = new DescuentoFecha(fechaDesde, fechaHasta, porcentajeDescuento);
			descuento.setCodigo(codigo);
			
			PoolDAO.getUnicaInstancia().addObjeto(codigo, descuento);
			return descuento;
		}
		else if(eDescuento.getNombre().equals("DescuentoMensaje")) {
			int numMensajes = Integer.valueOf(servPersistencia.recuperarPropiedadEntidad(eDescuento, "numMensajes"));
			Double porcentajeDescuento = Double.valueOf(servPersistencia.recuperarPropiedadEntidad(eDescuento, "porcentajeDescuento"));
			Descuento descuento = new DescuentoMensaje(porcentajeDescuento, numMensajes);
			descuento.setCodigo(codigo);
			
			PoolDAO.getUnicaInstancia().addObjeto(codigo, descuento);
			return descuento;
		}
		
		return null;
	}

}
