package com.capacitacion.EJM004_Arq_Hex.puertos.entrada;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;

import java.util.List;

public interface GestionPedidoService {
    Pedido crearPedido(List<Long> productoIds, String tipoPedido);

    void actualizarEstadoPedido(Long pedidoId, String nuevoEstado);

    void iniciarProcesoPago(Long pedidoId, String metodoPago, String detallesPago);

    Pedido obtenerPedido(Long pedidoId);
}
