package com.capacitacion.EJM003_Command;

import org.springframework.stereotype.Component;

@Component
public class ComandoInvoker {

    public void ejecutarComando(Command comando) {
        comando.ejecutar();
    }
}