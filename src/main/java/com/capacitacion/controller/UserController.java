package com.capacitacion.controller;

import com.capacitacion.model.dto.UserDTO;
import com.capacitacion.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Rest Api for Users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> obtenerUsuario(@PathVariable int id) {
        UserDTO userDto = userService.findById(1);
        return ResponseEntity.ok(userDto);
    }
}
