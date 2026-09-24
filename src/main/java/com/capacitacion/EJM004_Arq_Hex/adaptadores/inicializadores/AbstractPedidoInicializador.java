package com.capacitacion.EJM004_Arq_Hex.adaptadores.inicializadores;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.PedidoInicializador;

public abstract class AbstractPedidoInicializador implements PedidoInicializador {

    private final String tipoPedido;

    protected AbstractPedidoInicializador(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    @Override
    public void inicializar(Pedido pedido) {
        establecerFechaCreacion(pedido);
        establecerEstadoInicial(pedido);
        aplicarReglasEspecificas(pedido);
    }

    protected void establecerFechaCreacion(Pedido pedido) {
        if (pedido.getFechaCreacion() == null) {
            pedido.setFechaCreacion(java.time.LocalDateTime.now());
        }
    }

    protected void establecerEstadoInicial(Pedido pedido) {
        if (pedido.getEstado() == null) {
            pedido.setEstado("PENDIENTE");
        }
    }

    protected abstract void aplicarReglasEspecificas(Pedido pedido);

    @Override
    public String getTipoPedido() {
        return tipoPedido;
    }
}
