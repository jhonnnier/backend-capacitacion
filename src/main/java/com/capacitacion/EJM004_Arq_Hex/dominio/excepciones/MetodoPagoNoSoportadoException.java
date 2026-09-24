package com.capacitacion.EJM004_Arq_Hex.dominio.excepciones;

public class MetodoPagoNoSoportadoException extends RuntimeException {
    public MetodoPagoNoSoportadoException(String metodoPago) {
        super("Método de pago no soportado: " + metodoPago);
    }
}
