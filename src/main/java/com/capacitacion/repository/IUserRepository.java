package com.capacitacion.repository;

import com.capacitacion.model.entity.User;

public interface IUserRepository {
    User findById(Integer id);
}
