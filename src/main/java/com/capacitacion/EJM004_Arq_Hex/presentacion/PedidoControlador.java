package com.capacitacion.EJM004_Arq_Hex.adaptadores.presentacion;

import com.capacitacion.EJM004_Arq_Hex.dominio.Pedido;
import com.capacitacion.EJM004_Arq_Hex.puertos.entrada.GestionPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoControlador {

    private final GestionPedidoService gestionPedidoService;

    public PedidoControlador(GestionPedidoService gestionPedidoService) {
        this.gestionPedidoService = gestionPedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestParam List<Long> itemIds, @RequestParam String tipoPedido) {
        Pedido pedido = gestionPedidoService.crearPedido(itemIds, tipoPedido);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @PatchMapping("/{pedidoId}/estado")
    public ResponseEntity<String> actualizarEstadoPedido(@PathVariable Long pedidoId, @RequestParam String nuevoEstado) {
        gestionPedidoService.actualizarEstadoPedido(pedidoId, nuevoEstado);
        return ResponseEntity.ok("Estado del pedido " + pedidoId + " actualizado a " + nuevoEstado);
    }

    @PostMapping("/{pedidoId}/pagar")
    public ResponseEntity<String> iniciarProcesoPago(@PathVariable Long pedidoId,
                                                     @RequestParam String metodoPago,
                                                     @RequestParam String detallesPago) {
        gestionPedidoService.iniciarProcesoPago(pedidoId, metodoPago, detallesPago);
        return ResponseEntity.ok("Proceso de pago para el pedido " + pedidoId + " iniciado con " + metodoPago);
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long pedidoId) {
        Pedido pedido = gestionPedidoService.obtenerPedido(pedidoId);
        if (pedido != null) {
            return ResponseEntity.ok(pedido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
