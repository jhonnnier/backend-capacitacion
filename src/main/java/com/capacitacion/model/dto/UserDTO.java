package com.capacitacion.model.dto;

import com.capacitacion.annotations.ValidBirthday;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    @Schema(description = "ID del usuario")
    private Integer id;
    @Schema(description = "User firstName")
    private String firstName;
    @Schema(description = "User secondName")
    private String secondName;
    @Schema(description = "User firstLastName")
    private String firstLastName;
    @Schema(description = "User secondLastName")
    private String secondLastName;
    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    @ValidBirthday(minAge = 15) // Personalizamos la edad mínima a 21 años
    private LocalDateTime dateBirth;
    private String dateBirthFormated;
    private String dateBirthFormated2;
    private String edad;
}
