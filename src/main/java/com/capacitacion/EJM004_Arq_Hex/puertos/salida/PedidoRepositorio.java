package com.capacitacion.EJM004_Arq_Hex.puertos.salida;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;

public interface PedidoRepositorio {
    Pedido guardar(Pedido pedido);
    Pedido obtenerPorId(Long id);
    void actualizarEstado(Long id, String estado);
}
