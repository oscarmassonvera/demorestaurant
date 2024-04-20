package com.rabbit.demorest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabbit.demorest.entities.Restaurant;
import com.rabbit.demorest.entities.Rol;
import com.rabbit.demorest.entities.Users;
import com.rabbit.demorest.repositories.IUsersRepo;
import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    private IUsersRepo usersRepo;

    public ResponseEntity<?> createUser(Users user, Restaurant restaurant) {
        try {
            // Verificar si este es el primer usuario del restaurante
            List<Users> usersOfRestaurant = usersRepo.findByRestaurant(restaurant);
            if (usersOfRestaurant.isEmpty()) {
                // Si es el primer usuario, asignar automáticamente el rol de administrador
                user.setRol(Rol.ADMIN);
            } else {
                // Si no es el primer usuario, asignar el rol especificado en el objeto user
                Rol userRole = user.getRol();
                if (userRole == Rol.ADMIN) {
                    // Lanzar una excepción si se intenta asignar el rol de administrador
                    throw new IllegalArgumentException("No se puede asignar el rol de administrador a un usuario existente, ya existe un admin para este restaurant.");
                }
            }
            // Establecer la relación entre el usuario y el restaurante
            user.setRestaurant(restaurant);
            
            Users createdUser = usersRepo.save(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Capturar la excepción si se intenta asignar el rol de administrador
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo crear el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllUsers() {
        List<Users> users = (List<Users>) usersRepo.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>("No se encontraron usuarios", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<?> getUserById(Long id) {
        Optional<Users> userOptional = usersRepo.findById(id);
        if (userOptional.isPresent()) {
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateUser(Long id, Users updatedUser) {
        try {
            Optional<Users> userOptional = usersRepo.findById(id);
            if (userOptional.isPresent()) {
                updatedUser.setId(id);
                Users savedUser = usersRepo.save(updatedUser);
                return new ResponseEntity<>(savedUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo actualizar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteUser(Long id) {
        try {
            usersRepo.deleteById(id);
            return new ResponseEntity<>("Usuario eliminado exitosamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo eliminar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> findByUsername(String username) {
        Users user = usersRepo.findByUsername(username);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> findByRol(Rol rol) {
        List<Users> users = usersRepo.findByRol(rol);
        if (!users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Usuarios con el rol especificado no encontrados", HttpStatus.NOT_FOUND);
        }
    }
}
