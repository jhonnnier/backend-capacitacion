package com.capacitacion.repository.impl;

import com.capacitacion.annotations.TrackExecution;
import com.capacitacion.model.entity.User;
import com.capacitacion.repository.IUserRepository;
import com.capacitacion.util.DateUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class UserRepository implements IUserRepository {

    @TrackExecution
    @Override
    public User findById(Integer id) {

        return User.builder()
                .id(id)
                .firstName("Jhonnier")
                .secondName("Alberto")
                .firstLastName("Sánchez")
                .secondLastName("Dorado")
                .dateBirth(LocalDateTime.parse("2024-11-28T00:00:00.00"))
                .build();
    }
}
