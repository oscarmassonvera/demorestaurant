package com.rabbit.demorest.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rabbit.demorest.entities.Restaurant;
import com.rabbit.demorest.repositories.IRestaurantRepo;

@Service
public class RestaurantServiceImplement implements IRestaurantService {
    @Autowired
    private IRestaurantRepo restaurantRepo;

    public ResponseEntity<?> createRestaurant(Restaurant restaurant) {
        try {
            Restaurant createdRestaurant = restaurantRepo.save(restaurant);
            return new ResponseEntity<>(createdRestaurant, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo crear el restaurante", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        if (restaurants.isEmpty()) {
            return new ResponseEntity<>("No se encontraron restaurantes", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }

    public ResponseEntity<?> getRestaurantById(Long id) {
        Optional<Restaurant> restaurantOptional = restaurantRepo.findById(id);
        if (restaurantOptional.isPresent()) {
            return new ResponseEntity<>(restaurantOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Restaurante no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> updateRestaurant(Long id, Restaurant updatedRestaurant) {
        try {
            Optional<Restaurant> restaurantOptional = restaurantRepo.findById(id);
            if (restaurantOptional.isPresent()) {
                updatedRestaurant.setId(id); // Asegurarse de que el ID del restaurante a actualizar sea el correcto
                Restaurant savedRestaurant = restaurantRepo.save(updatedRestaurant);
                return new ResponseEntity<>(savedRestaurant, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Restaurante no encontrado", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo actualizar el restaurante", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> deleteRestaurant(Long id) {
        try {
            restaurantRepo.deleteById(id);
            return new ResponseEntity<>("Restaurante eliminado exitosamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Restaurante no encontrado", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo eliminar el restaurante", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
