package com.capacitacion.EJM004_Arq_Hex.puertos.salida;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;

public interface ProcesadorPago {
    void procesarPago(Pedido pedido, String detallesPago);

    String getMetodoPago();
}
