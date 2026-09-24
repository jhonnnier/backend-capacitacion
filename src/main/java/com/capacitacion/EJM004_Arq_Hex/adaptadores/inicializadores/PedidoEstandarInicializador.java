package com.capacitacion.EJM004_Arq_Hex.adaptadores.inicializadores;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import org.springframework.stereotype.Component;

@Component("pedidoEstandarInicializador")
public class PedidoEstandarInicializador extends AbstractPedidoInicializador {

    public PedidoEstandarInicializador() {
        super("estandar");
    }

    @Override
    protected void aplicarReglasEspecificas(Pedido pedido) {
        System.out.println("[Inicializador Estándar] No se aplican reglas específicas.");
        // Lógica específica para pedidos estándar (si la hubiera)
    }
}
