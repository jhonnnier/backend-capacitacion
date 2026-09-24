package com.capacitacion.EJM004_Arq_Hex.adaptadores.calculadores;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.CalculadorTotalPedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("calculadorTotalPromocion")
public class CalculadorTotalPromocion implements CalculadorTotalPedido {
    @Override
    public BigDecimal calcularTotal(Pedido pedido) {
        BigDecimal subtotal = pedido.getItems().stream()
                .map(item -> item.getPrecioUnitario().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Aplicar descuento por promoción si aplica
        if (subtotal.compareTo(BigDecimal.valueOf(50)) > 0) {
            return subtotal.multiply(BigDecimal.valueOf(0.9)); // 10% de descuento
        }
        return subtotal;
    }

    @Override
    public String getEstrategia() {
        return "promocion";
    }
}
