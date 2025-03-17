package com.capacitacion.model.mappers;

import com.capacitacion.model.dto.UserDTO;
import com.capacitacion.model.entity.User;
import com.capacitacion.util.DateUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    @Autowired
    private MessageSource messageSource;

    public UserDTO toDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .secondName(user.getSecondName())
                .firstLastName(user.getFirstLastName())
                .secondLastName(user.getSecondLastName())
                .dateBirth(user.getDateBirth())
                .dateBirthFormated(DateUtilities.getFormat(user.getDateBirth()))
                .dateBirthFormated2(DateUtilities.getFormat2(user.getDateBirth()))
                .edad(DateUtilities.calcularEdad(user.getDateBirth()))
                .build();
    }

    public User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .firstName(userDTO.getFirstName())
                .secondName(userDTO.getSecondName())
                .firstLastName(userDTO.getFirstLastName())
                .secondLastName(userDTO.getSecondLastName())
                .dateBirth(userDTO.getDateBirth())
                .build();
    }
}
