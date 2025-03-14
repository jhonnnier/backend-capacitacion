package com.capacitacion.service.impl;

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

    @Override
    public UserDTO findById(Integer id) {
        User user = userRepository.findById(1);
        return usuarioMapper.toDTO(user);
    }
}
