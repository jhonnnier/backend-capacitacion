package com.capacitacion.EJM004_Arq_Hex.puertos.salida;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;

public interface PedidoInicializador {
    void inicializar(Pedido pedido);

    String getTipoPedido();
}
