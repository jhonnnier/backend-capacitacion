package com.capacitacion.annotations;

import com.capacitacion.util.validators.BirthdayValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.FIELD) // Se aplica a atributos
@Retention(RetentionPolicy.RUNTIME) // Disponible en tiempo de ejecución
@Constraint(validatedBy = BirthdayValidator.class) // Usa la clase validadora
public @interface ValidBirthday {
    String message() default "La fecha de nacimiento no es válida o no cumple con la edad mínima requerida.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int minAge() default 18; // Edad mínima por defecto
}