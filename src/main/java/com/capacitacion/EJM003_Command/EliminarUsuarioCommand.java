package com.capacitacion.EJM003_Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("eliminarUsuarioCommand")
public class EliminarUsuarioCommand extends Command {

    private Long id;

    @Autowired
    private UsuarioService usuarioService;

    public EliminarUsuarioCommand() {
    }
    
    public EliminarUsuarioCommand(Long id) {
        this.id = id;
    }

    @Override
    public void ejecutar() {
        usuarioService.eliminarUsuario(id);
        System.out.println("Comando: Eliminando usuario con ID " + id);
    }
}
