package com.capacitacion.EJM004_Arq_Hex.puertos.salida;


import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;

public interface NotificadorEstadoPedido {
    void notificar(Pedido pedido);

    String getCanal();
}
