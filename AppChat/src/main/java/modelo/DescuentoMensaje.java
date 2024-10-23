package modelo;

public class DescuentoMensaje implements Descuento{
    public double calcularDescuento(double precio) {
        return precio*0.8;
    }
}