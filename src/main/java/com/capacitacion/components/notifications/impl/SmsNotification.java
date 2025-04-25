package com.capacitacion.components.notifications.impl;

import com.capacitacion.service.INotification;
import org.springframework.stereotype.Component;

@Component
public class SmsNotification implements INotification {
    @Override
    public String getType() {
        return "sms";
    }

    @Override
    public void send(String message) {
        System.out.println("📱 SMS: " + message);
    }
}
