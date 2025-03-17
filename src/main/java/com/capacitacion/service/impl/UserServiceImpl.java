package com.capacitacion.service.impl;

import com.capacitacion.annotations.TrackExecution;
import com.capacitacion.model.mappers.UserMapper;
import com.capacitacion.model.dto.UserDTO;
import com.capacitacion.model.entity.User;
import com.capacitacion.repository.IUserRepository;
import com.capacitacion.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper usuarioMapper;

    @TrackExecution
    @Override
    public UserDTO findById(Integer id) {
        try {
            Thread.sleep(1500); // Simula una operación de 500 ms
            User user = userRepository.findById(1);
            return usuarioMapper.toDTO(user);
        } catch (Exception e) {

        }

        return new UserDTO();
    }
}
