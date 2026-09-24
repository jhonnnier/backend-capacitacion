package com.capacitacion.EJM004_Arq_Hex.dominio.excepciones;


public class PagoRechazadoException extends RuntimeException {
    public PagoRechazadoException(String mensaje) {
        super("Pago rechazado: " + mensaje);
    }
}
