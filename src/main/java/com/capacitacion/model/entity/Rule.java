package com.capacitacion.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "ruls")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Rule {
    @Id
    @Schema(description = "ID de la regla")
    private Integer id;

    @Schema(description = "Código de la regla")
    private String code;

    @Schema(description = "Descripción de la regla")
    private String description;

    @Schema(description = "Indica si la regla está activa")
    private boolean active;
}
