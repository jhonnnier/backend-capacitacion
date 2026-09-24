package com.capacitacion.EJM003_Command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuarioController {

    @Autowired
    private ComandoInvoker comandoInvoker;

    @Autowired
    @Qualifier("crearUsuarioCommand")
    private CrearUsuarioCommand crearUsuarioCommand;

    @Autowired
    @Qualifier("actualizarUsuarioCommand")
    private ActualizarUsuarioCommand actualizarUsuarioCommand;

    @Autowired
    @Qualifier("eliminarUsuarioCommand")
    private EliminarUsuarioCommand eliminarUsuarioCommand;

    @PostMapping("/usuarios")
    public String crearUsuario(@RequestParam String nombre, @RequestParam String email) {
        // Creamos una nueva instancia del comando con los parámetros específicos
        CrearUsuarioCommand comando = new CrearUsuarioCommand(nombre, email);
        comandoInvoker.ejecutarComando(comando);
        return "Comando de creación de usuario ejecutado.";
    }

    @PostMapping("/usuarios/{id}")
    public String actualizarUsuario(@PathVariable Long id, @RequestParam String nuevoNombre) {
        // Creamos una nueva instancia del comando con los parámetros específicos
        ActualizarUsuarioCommand comando = new ActualizarUsuarioCommand(id, nuevoNombre);
        comandoInvoker.ejecutarComando(comando);
        return "Comando de actualización de usuario ejecutado.";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        // Creamos una nueva instancia del comando con los parámetros específicos
        EliminarUsuarioCommand comando = new EliminarUsuarioCommand(id);
        comandoInvoker.ejecutarComando(comando);
        return "Comando de eliminación de usuario ejecutado.";
    }
}
