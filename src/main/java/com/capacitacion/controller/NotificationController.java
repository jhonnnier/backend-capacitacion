package com.capacitacion.controller;


import com.capacitacion.service.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @PostMapping("/send")
    public String send(
            @RequestParam String type,
            @RequestParam String message
    ) {
        service.send(type, message);
        return "Notificación enviada por " + type;
    }
}