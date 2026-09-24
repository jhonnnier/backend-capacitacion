package com.capacitacion.EJM004_Arq_Hex.aplicacion;

import com.capacitacion.EJM004_Arq_Hex.dominio.ItemPedido;
import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.dominio.excepciones.PedidoNoEncontradoException;
import com.capacitacion.EJM004_Arq_Hex.puertos.entrada.GestionPedidoService;
import com.capacitacion.EJM004_Arq_Hex.puertos.salida.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GestionPedidoServiceImpl implements GestionPedidoService {

    private final PedidoRepositorio pedidoRepositorio;
    private final Map<String, CalculadorTotalPedido> calculadoresTotal;
    private final List<NotificadorEstadoPedido> notificadoresEstado;
    private final Map<String, ProcesadorPago> procesadoresPago;
    private final Map<String, PedidoInicializador> inicializadoresPedido;

    private final Map<Long, BigDecimal> preciosProductos = Map.of(
            1L, BigDecimal.TEN, 
            2L, BigDecimal.valueOf(20.05),
            3L, BigDecimal.valueOf(30.10),
            4L, BigDecimal.valueOf(40.15),
            5L, BigDecimal.valueOf(50.20),
            6L, BigDecimal.valueOf(60.25),
            7L, BigDecimal.valueOf(65.30),
            8L, BigDecimal.valueOf(70.35)
    
    );

    public GestionPedidoServiceImpl(PedidoRepositorio pedidoRepositorio,
                                    List<CalculadorTotalPedido> calculadoresTotal,
                                    List<NotificadorEstadoPedido> notificadoresEstado,
                                    List<ProcesadorPago> procesadoresPago,
                                    List<PedidoInicializador> inicializadoresPedido) {
        this.pedidoRepositorio = pedidoRepositorio;
        this.calculadoresTotal = calculadoresTotal.stream().collect(Collectors.toMap(CalculadorTotalPedido::getEstrategia, calc -> calc));
        this.notificadoresEstado = notificadoresEstado;
        this.procesadoresPago = procesadoresPago.stream().collect(Collectors.toMap(ProcesadorPago::getMetodoPago, proc -> proc));
        this.inicializadoresPedido = inicializadoresPedido.stream().collect(Collectors.toMap(PedidoInicializador::getTipoPedido, init -> init));
    }

    @Override
    public Pedido crearPedido(List<Long> itemIds, String tipoPedido) {
        Pedido pedido = new Pedido();
        pedido.setFechaCreacion(LocalDateTime.now());
        pedido.setEstado("CREADO");
        pedido.setTipoPedido(tipoPedido);
        List<ItemPedido> itemsPedido = itemIds.stream()
                .filter(preciosProductos::containsKey)
                .map(id -> new ItemPedido(id, 1, preciosProductos.get(id)))
                .collect(Collectors.toList());
        pedido.setItems(itemsPedido);

        String tipoPedidoAux = tipoPedido.toLowerCase();
        
        PedidoInicializador inicializador = inicializadoresPedido.get(tipoPedidoAux);
        if (inicializador != null) {
            inicializador.inicializar(pedido);
        }

        BigDecimal total = calculadoresTotal.get("default").calcularTotal(pedido);
        pedido.setTotal(total);

        return pedidoRepositorio.guardar(pedido);
    }

    @Override
    public void actualizarEstadoPedido(Long pedidoId, String nuevoEstado) {
        Pedido pedido = pedidoRepositorio.obtenerPorId(pedidoId);
        if (pedido != null) {
            pedidoRepositorio.actualizarEstado(pedidoId, nuevoEstado);
            pedido.setEstado(nuevoEstado);
            notificadoresEstado.forEach(notificador -> notificador.notificar(pedido));
        }
    }

    @Override
    public void iniciarProcesoPago(Long pedidoId, String metodoPago, String detallesPago) {
        Pedido pedido = pedidoRepositorio.obtenerPorId(pedidoId);
        if (pedido != null) {
            ProcesadorPago procesador = procesadoresPago.get(metodoPago.toLowerCase());
            if (procesador != null) {
                procesador.procesarPago(pedido, detallesPago);
                pedidoRepositorio.actualizarEstado(pedidoId, "PAGADO");
                pedido.setEstado("PAGADO");
                notificadoresEstado.forEach(notificador -> notificador.notificar(pedido));
            } else {
                System.out.println("Método de pago no soportado: " + metodoPago);
            }
        }
    }

    @Override
    public Pedido obtenerPedido(Long pedidoId) {
        Pedido pedido = pedidoRepositorio.obtenerPorId(pedidoId);
        if (pedido == null) {
            throw new PedidoNoEncontradoException(pedidoId);
        }
        return pedido;
    }
}
