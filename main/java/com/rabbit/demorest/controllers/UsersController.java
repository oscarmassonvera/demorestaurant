package com.rabbit.demorest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.demorest.entities.Users;
import com.rabbit.demorest.entities.Rol;
import com.rabbit.demorest.entities.Restaurant;
import com.rabbit.demorest.services.UsersService;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody Users user, @RequestBody Restaurant restaurant) {
        return usersService.createUser(user, restaurant);
    }

    @GetMapping("/all")
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

    @GetMapping("/role/{role}")
    public ResponseEntity<?> findByRole(@PathVariable Rol role) {
        return usersService.findByRol(role);
    }
}
