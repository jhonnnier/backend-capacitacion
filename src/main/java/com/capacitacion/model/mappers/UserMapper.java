package com.capacitacion.model.mappers;

import com.capacitacion.model.dto.UserDTO;
import com.capacitacion.model.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .firstLastName(user.getFirstLastName())
                .secondLastName(user.getSecondLastName())
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .secondName(userDTO.getSecondName())
                .firstLastName(userDTO.getFirstLastName())
                .secondLastName(userDTO.getSecondLastName())
                .build();
    }
}
