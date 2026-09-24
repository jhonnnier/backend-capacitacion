package com.capacitacion.EJM003_Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("actualizarUsuarioCommand")
public class ActualizarUsuarioCommand extends Command {
    private Long id;
    private String nuevoNombre;

    @Autowired
    private UsuarioService usuarioService;

    public ActualizarUsuarioCommand(){}

    public ActualizarUsuarioCommand(Long id, String nuevoNombre) {
        this.id = id;
        this.nuevoNombre = nuevoNombre;
    }

    @Override
    public void ejecutar() {
        usuarioService.actualizarUsuario(id, nuevoNombre);
        System.out.println("Comando: Actualizando usuario con ID " + id + " a nombre " + nuevoNombre);
    }
}