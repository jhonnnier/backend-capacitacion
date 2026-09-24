package com.capacitacion.EJM004_Arq_Hex.adaptadores.notificadores;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.NotificadorEstadoPedido;
import org.springframework.stereotype.Component;

@Component("notificadorSMS")
public class NotificadorSMS implements NotificadorEstadoPedido {
    @Override
    public void notificar(Pedido pedido) {
        System.out.println("[SMS] Estado del pedido " + pedido.getId() + " actualizado a: " + pedido.getEstado() + ". Enviando SMS...");
        // Lógica para enviar SMS
    }

    @Override
    public String getCanal() {
        return "sms";
    }
}
