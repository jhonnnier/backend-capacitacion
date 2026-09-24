package com.capacitacion.EJM003_Command;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("crearUsuarioCommand")
public class CrearUsuarioCommand extends Command {

    @Setter
    @Getter
    private String nombre;
    private String email;

    @Autowired
    private UsuarioService usuarioService;

    public CrearUsuarioCommand() {
    }

    public CrearUsuarioCommand(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    @Override
    public void ejecutar() {
        usuarioService.crearUsuario(nombre, email);
        System.out.println("Comando: Creando usuario con nombre " + nombre + " y email " + email);
    }

    
}
