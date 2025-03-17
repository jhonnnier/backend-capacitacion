package com.capacitacion.util.validators;

import com.capacitacion.annotations.ValidBirthday;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;
import java.time.Period;

public class BirthdayValidator implements ConstraintValidator<ValidBirthday, LocalDateTime> {
    private int minAge;

    @Override
    public void initialize(ValidBirthday constraintAnnotation) {
        this.minAge = constraintAnnotation.minAge();
    }

    @Override
    public boolean isValid(LocalDateTime birthday, ConstraintValidatorContext context) {
        if (birthday == null) {
            return false; // No permite valores nulos
        }

        LocalDateTime now = LocalDateTime.now();
        if (birthday.isAfter(now)) {
            return false; // No puede ser una fecha futura
        }

        int age = Period.between(birthday.toLocalDate(), now.toLocalDate()).getYears();
        if (age < minAge) {
            // 🔥 IMPORTANTE: Deshabilitar el mensaje predeterminado y agregar un mensaje nuevo
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Debes tener al menos " + minAge + " años.")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}