package com.capacitacion.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Integer id;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
}
