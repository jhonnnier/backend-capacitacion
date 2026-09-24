package com.capacitacion.EJM004_Arq_Hex.adaptadores.notificadores;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.NotificadorEstadoPedido;
import org.springframework.stereotype.Component;

@Component("notificadorEmail")
public class NotificadorEmail implements NotificadorEstadoPedido {
    @Override
    public void notificar(Pedido pedido) {
        System.out.println("[Email] Estado del pedido " + pedido.getId() + " actualizado a: " + pedido.getEstado() + ". Enviando correo...");
        // Lógica para enviar email
    }

    @Override
    public String getCanal() {
        return "email";
    }
}

