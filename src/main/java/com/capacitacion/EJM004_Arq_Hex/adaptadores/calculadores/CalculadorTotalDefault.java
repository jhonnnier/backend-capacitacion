package com.capacitacion.EJM004_Arq_Hex.adaptadores.calculadores;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.CalculadorTotalPedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("calculadorTotalDefault")
public class CalculadorTotalDefault implements CalculadorTotalPedido {
    @Override
    public BigDecimal calcularTotal(Pedido pedido) {
        return pedido.getItems().stream()
                .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public String getEstrategia() {
        return "default";
    }
}
