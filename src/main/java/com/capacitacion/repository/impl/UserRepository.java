package com.capacitacion.repository.impl;

import com.capacitacion.model.entity.User;
import com.capacitacion.repository.IUserRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {
    @Override
    public User findById(Integer id) {
        User user = User.builder()
                .id(id)
                .firstName("Jhonnier")
                .secondName("Alberto")
                .firstLastName("Sánchez")
                .secondLastName("Dorado")
                .build();

        return user;
    }
}
