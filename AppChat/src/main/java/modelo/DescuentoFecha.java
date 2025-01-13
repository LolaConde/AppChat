package modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DescuentoFecha extends Descuento{
	
	private LocalDate fechaDesde;
	private LocalDate fechaHasta;
	private Double porcentajeDescuento;
	
	public LocalDate getFechaDesde() {
		return fechaDesde;
	}

	public LocalDate getFechaHasta() {
		return fechaHasta;
	}

	public Double getPorcentajeDescuento() {
		return porcentajeDescuento;
	}
	
	/**
	 * Se crea un descuento del <code>porcentaje</code> por ciento porque el usuario se registró 
	 * entre las fechas <code>fechaDesde</code> y <code>fechaHasta</code>
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @param porcentaje
	 */
	public DescuentoFecha (LocalDate fechaDesde, LocalDate fechaHasta, Double porcentaje){
		this.fechaDesde = fechaDesde;
		this.fechaHasta = fechaHasta;
		this.porcentajeDescuento = porcentaje;
	}
	
    public double calcularDescuento(double precio) {
        return precio - precio*porcentajeDescuento/100;
    }
    
    @Override
    public boolean esAplicable(Usuario usuario) {
    	LocalDate fechaRegistro = usuario.getFechaRegistro();
    	return !fechaRegistro.isBefore(fechaDesde) && !fechaRegistro.isAfter(fechaHasta);
    }
    
    @Override
    public String toString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	return "Descuento por registro de usuario entre las fechas: " 
    			+ fechaDesde.format(formatter)
    			+ " y "
    			+ fechaHasta.format(formatter);
    }
}