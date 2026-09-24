package com.capacitacion.EJM004_Arq_Hex.adaptadores.procesadorespago;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.ProcesadorPago;
import org.springframework.stereotype.Component;

@Component("procesadorTarjetaCredito")
public class ProcesadorTarjetaCredito implements ProcesadorPago {
    @Override
    public void procesarPago(Pedido pedido, String detallesPago) {
        System.out.println("[Tarjeta Crédito] Procesando pago para pedido " + pedido.getId() + " con detalles: " + detallesPago);
        // Lógica para procesar pago con tarjeta de crédito
    }

    @Override
    public String getMetodoPago() {
        return "tarjeta_credito";
    }
}
