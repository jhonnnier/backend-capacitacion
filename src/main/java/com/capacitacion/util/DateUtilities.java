package com.capacitacion.util;

import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

@UtilityClass
public class DateUtilities {
    public static String getFormat(@NotNull(message = "La fecha de nacimiento es obligatoria.") LocalDateTime date) {
        // Definir el formato DD/MM/AAAA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Convertir la fecha al formato deseado y devolverla como String
        return date.format(formatter);
    }

    public static String getFormat2(LocalDateTime date) {
        if (Objects.isNull(date)) {
            return "-";
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        String dayName = dayOfWeek.getDisplayName(java.time.format.TextStyle.FULL, new Locale("es", "ES"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM", new Locale("es", "ES"));
        String formattedDate = date.format(formatter);

        return dayName + " " + formattedDate;
    }

    public String calcularEdad(LocalDateTime fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede ser nula.");
        }

        // Convertir a LocalDate para ignorar la hora
        LocalDate fechaNacimientoSinHora = fechaNacimiento.toLocalDate();
        LocalDate fechaActual = LocalDate.now();

        // Calcular la diferencia en años
        int anios = Period.between(fechaNacimientoSinHora, fechaActual).getYears();

        if (anios < 1) {
            int meses = Period.between(fechaNacimientoSinHora, fechaActual).getMonths();
            return meses + " meses";
        }

        return anios + " años";
    }
}
