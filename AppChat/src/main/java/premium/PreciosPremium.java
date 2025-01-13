package premium;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import modelo.Descuento;
import modelo.DescuentoFecha;
import modelo.DescuentoMensaje;
import modelo.DescuentoNull;
import modelo.Usuario;

public enum PreciosPremium {
	INSTANCE;
	
	public static final double PRECIO_PREMIUM = 24.0;
	private List<Descuento> descuentos; // Descuentos disponibles en este momento
	
	PreciosPremium() {
		addDescuentos();
	}
	
	/**
	 * Inicializa <code>descuentos</code> con una lista de todos los descuentos disponibles en
	 * este momento.
	 */
	private void addDescuentos() {
		descuentos = new ArrayList<Descuento>();
		descuentos.add(new DescuentoFecha(LocalDate.of(2024, 11, 13), LocalDate.of(2025, 3, 13), 20.0));
		descuentos.add(new DescuentoMensaje(30.0, 4));
	}
	
	/**
	 * Considera sólo los descuentos disponibles en la actualidad y 
	 * aplicables a <code>usuario</code> en el momento de la llamada a la función.
	 * 
	 * @param usuario
	 * @return El descuento que resulta en menor precio final para <code>usuario</code>.
	 */
	public Descuento obtenerMejorDescuentoDisponible(Usuario usuario) {
		return descuentos.stream()
				.filter(d -> d.esAplicable(usuario))
				.min(Comparator.comparing(d -> d.calcularDescuento(PRECIO_PREMIUM)))
				.orElse(new DescuentoNull());
	}
}
