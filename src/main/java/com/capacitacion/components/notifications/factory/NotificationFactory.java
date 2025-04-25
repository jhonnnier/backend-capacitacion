package com.capacitacion.components.notifications.factory;

import com.capacitacion.service.INotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationFactory {

    private final Map<String, INotification> strategies;

    @Autowired
    public NotificationFactory(java.util.List<INotification> notificationList) {
        this.strategies = notificationList.stream()
                .collect(Collectors.toMap(INotification::getType, n -> n));
    }

    public INotification getNotification(String type) {
        INotification notification = strategies.get(type.toLowerCase());
        if (notification == null) {
            throw new IllegalArgumentException("Tipo de notificación no soportado: " + type);
        }
        return notification;
    }
}
