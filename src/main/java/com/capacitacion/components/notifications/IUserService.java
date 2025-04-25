package com.capacitacion.components.notifications;

import com.capacitacion.model.dto.UserDTO;

public interface IUserService {
    UserDTO findById(Integer id);
}
