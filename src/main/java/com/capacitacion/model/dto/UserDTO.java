package com.capacitacion.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private Integer id;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
}
