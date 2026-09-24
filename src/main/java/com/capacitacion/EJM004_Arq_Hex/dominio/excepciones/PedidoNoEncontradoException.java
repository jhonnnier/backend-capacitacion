package com.capacitacion.EJM004_Arq_Hex.dominio.excepciones;

public class PedidoNoEncontradoException extends RuntimeException {
    public PedidoNoEncontradoException(Long pedidoId) {
        super("Pedido con ID " + pedidoId + " no encontrado.");
    }
}
