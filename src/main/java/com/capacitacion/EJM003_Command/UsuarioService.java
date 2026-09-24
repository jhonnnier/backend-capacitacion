package com.capacitacion.EJM003_Command;

import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    public void crearUsuario(String nombre, String email) {
        // Lógica para crear un usuario en la base de datos
        System.out.println("[Servicio] Creando usuario: " + nombre + ", " + email);
    }

    public void actualizarUsuario(Long id, String nuevoNombre) {
        // Lógica para actualizar un usuario en la base de datos
        System.out.println("[Servicio] Actualizando usuario con ID " + id + " a " + nuevoNombre);
    }

    public void eliminarUsuario(Long id) {
        // Lógica para eliminar un usuario de la base de datos
        System.out.println("[Servicio] Eliminando usuario con ID " + id);
    }
}
