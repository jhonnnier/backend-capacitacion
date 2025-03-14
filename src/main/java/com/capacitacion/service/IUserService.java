package com.capacitacion.service;

import com.capacitacion.model.dto.UserDTO;

public interface IUserService {
    UserDTO findById(Integer id);
}
