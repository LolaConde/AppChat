package modelo;

public class DescuentoFecha implements Descuento{
    public double calcularDescuento(double precio) {
        return precio*0.9;
    }
}