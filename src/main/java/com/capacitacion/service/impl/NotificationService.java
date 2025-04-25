package com.capacitacion.service.impl;

import com.capacitacion.components.notifications.factory.NotificationFactory;
import com.capacitacion.service.INotification;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationFactory factory;

    public NotificationService(NotificationFactory factory) {
        this.factory = factory;
    }

    public void send(String type, String message) {
        INotification notification = factory.getNotification(type);
        notification.send(message);
    }
}
