package com.capacitacion.EJM004_Arq_Hex.dominio.excepciones;

public class ProductoNoDisponibleException extends RuntimeException {
    public ProductoNoDisponibleException(Long productoId) {
        super("Producto con ID " + productoId + " no está disponible.");
    }
}

