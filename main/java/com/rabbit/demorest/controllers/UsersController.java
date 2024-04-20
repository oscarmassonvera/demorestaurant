package com.rabbit.demorest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.rabbit.demorest.entities.Rol;
import com.rabbit.demorest.entities.Users;
import com.rabbit.demorest.services.UsersService;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        return usersService.createUser(user);
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return usersService.getUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users updatedUser) {
        return usersService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return usersService.deleteUser(id);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        return usersService.findByUsername(username);
    }

    @GetMapping("/rol/{rol}")
    public ResponseEntity<?> findByRol(@PathVariable Rol rol) {
        return usersService.findByRol(rol);
    }
}
