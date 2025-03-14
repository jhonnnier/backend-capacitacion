package com.capacitacion.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

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
}
