package com.capacitacion.EJM004_Arq_Hex.adaptadores.procesadorespago;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.ProcesadorPago;
import org.springframework.stereotype.Component;

@Component("procesadorPayPal")
public class ProcesadorPayPal implements ProcesadorPago {
    @Override
    public void procesarPago(Pedido pedido, String detallesPago) {
        System.out.println("[PayPal] Procesando pago para pedido " + pedido.getId() + " con detalles: " + detallesPago);
        // Lógica para procesar pago con PayPal
    }

    @Override
    public String getMetodoPago() {
        return "paypal";
    }
}
