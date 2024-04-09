package com.rabbit.demorest.services;

import java.util.List;

import com.rabbit.demorest.entities.Rol;
import com.rabbit.demorest.entities.Users;

public interface IUserService {
    List<Users> findAll();
    Users saveUser(Users user);
    Users findByUsername(String username);
    List<Users> findByRol(Rol rol);
}
