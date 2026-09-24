package com.capacitacion.EJM004_Arq_Hex.adaptadores.inicializadores;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import org.springframework.stereotype.Component;

@Component("pedidoUrgenteInicializador")
public class PedidoUrgenteInicializador extends AbstractPedidoInicializador {

    public PedidoUrgenteInicializador() {
        super("urgente");
    }

    @Override
    protected void aplicarReglasEspecificas(Pedido pedido) {
        System.out.println("[Inicializador Urgente] Estableciendo estado inicial a EN_PROCESO.");
        pedido.setEstado("EN_PROCESO");
        // Lógica específica para pedidos urgentes (e.g., asignar a un agente prioritario)
    }
}
