package com.rabbit.demorest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rabbit.demorest.entities.Rol;
import com.rabbit.demorest.entities.Users;
import com.rabbit.demorest.repositories.IUsersRepo;

@Service
public class UserServiceImplement implements IUserService {

    @Autowired
    private IUsersRepo userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Users saveUser(Users user) {
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<Users> findByRol(Rol rol) {
        return userRepository.findByRol(rol);
    }

    @Override
    public List<Users> findAll() {
        return  (List<Users>) userRepository.findAll();
    }

}
