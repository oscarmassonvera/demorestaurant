package com.rabbit.demorest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.demorest.entities.Users;
import com.rabbit.demorest.services.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService ;


    @GetMapping
    public ResponseEntity<?> getUsers() {
        // Lógica para obtener usuarios desde el servicio
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        // Lógica para crear un usuario
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}


// Endpoints para la gestión de usuarios: Actualmente tienes un controlador 
// UserController que permite obtener y crear usuarios, pero podría ser útil
//  agregar endpoints adicionales para actualizar y eliminar usuarios si es 
//  necesario.