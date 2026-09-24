package com.capacitacion.EJM004_Arq_Hex.adaptadores.persistencia;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.PedidoRepositorio;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PedidoRepositorioImpl implements PedidoRepositorio {
    private final Map<Long, Pedido> pedidos = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public Pedido guardar(Pedido pedido) {
        pedido.setId(nextId.getAndIncrement());
        pedidos.put(pedido.getId(), pedido);
        System.out.println("[Repositorio] Pedido guardado con ID: " + pedido.getId());
        return pedido;
    }

    @Override
    public Pedido obtenerPorId(Long id) {
        return pedidos.get(id);
    }

    @Override
    public void actualizarEstado(Long id, String estado) {
        Pedido pedido = pedidos.get(id);
        if (pedido != null) {
            pedido.setEstado(estado);
            System.out.println("[Repositorio] Estado del pedido " + id + " actualizado a: " + estado);
        }
    }
}

