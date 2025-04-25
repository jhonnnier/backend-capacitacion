package com.capacitacion.service;

public interface INotification {
    String getType();  // clave del mapa

    void send(String message);
}
