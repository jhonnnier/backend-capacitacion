package com.capacitacion.EJM004_Arq_Hex.puertos.salida;


import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;

import java.math.BigDecimal;

public interface CalculadorTotalPedido {
    BigDecimal calcularTotal(Pedido pedido);

    String getEstrategia();
}