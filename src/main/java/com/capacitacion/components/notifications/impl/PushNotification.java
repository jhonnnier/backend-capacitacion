package com.capacitacion.components.notifications.impl;

import com.capacitacion.service.INotification;
import org.springframework.stereotype.Component;

@Component
public class PushNotification implements INotification {
    @Override
    public String getType() {
        return "push";
    }

    @Override
    public void send(String message) {
        System.out.println("📲 Push: " + message);
    }
}
