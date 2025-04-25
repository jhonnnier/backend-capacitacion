package com.capacitacion.components.notifications.impl;

import com.capacitacion.service.INotification;
import org.springframework.stereotype.Component;

@Component
public class EmailNotification implements INotification {
    @Override
    public String getType() {
        return "email";
    }

    @Override
    public void send(String message) {
        System.out.println("📧 Email: " + message);
    }
}
