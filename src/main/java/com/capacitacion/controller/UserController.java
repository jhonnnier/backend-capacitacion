package com.capacitacion.controller;

import com.capacitacion.annotations.TrackExecution;
import com.capacitacion.model.dto.UserDTO;
import com.capacitacion.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;

@RestController
@RequestMapping("/user")
@Tag(name = "User", description = "Rest Api for Users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private MessageSource messageSource;

    @TrackExecution
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @RequestParam(name = "lang", required = false) String lang,
            @PathVariable int id
    ) {
        UserDTO userDto = userService.findById(1);

        Locale locale = new Locale(lang);
        String msg =  messageSource.getMessage("mensaje.bienvenida", new Object[]{userDto.getFirstName()}, locale);

        return ResponseEntity.ok(userDto);
    }

    @TrackExecution
    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserDTO userDTO) {
        int edad = Period.between(LocalDate.from(userDTO.getDateBirth()), LocalDate.now()).getYears();
        return ResponseEntity.ok("Usuario creado con éxito. Edad calculada: " + edad + " años.");
    }

    @GetMapping("/bienvenida")
    public String mensajeBienvenida(@RequestParam String nombre, @RequestParam(defaultValue = "es") String lang) {
        Locale locale = new Locale(lang);
        return messageSource.getMessage("mensaje.bienvenida", new Object[]{nombre}, locale);
    }
}
