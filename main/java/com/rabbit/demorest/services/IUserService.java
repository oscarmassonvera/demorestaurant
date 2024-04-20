package com.rabbit.demorest.services;

import org.springframework.http.ResponseEntity;

import com.rabbit.demorest.entities.Rol;
import com.rabbit.demorest.entities.Users;

public interface IUserService {
    public ResponseEntity<?> createUser(Users user);
    public ResponseEntity<?> getAllUsers();
    public ResponseEntity<?> getUserById(Long id);
    public ResponseEntity<?> updateUser(Long id, Users updatedUser);
    public ResponseEntity<?> deleteUser(Long id);
    public ResponseEntity<?> findByUsername(String username);
    public ResponseEntity<?> findByRol(Rol rol);
}
