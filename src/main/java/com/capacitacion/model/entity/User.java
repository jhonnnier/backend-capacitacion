package com.capacitacion.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
    @Id
    @Schema(description = "ID del usuario")
    private Integer id;

    @NotNull
    @Schema(description = "User firstName")
    private String firstName;

    @Schema(description = "User secondName")
    private String secondName;

    @NotNull
    @Schema(description = "User firstLastName")
    private String firstLastName;

    @Schema(description = "User secondLastName")
    private String secondLastName;
}
