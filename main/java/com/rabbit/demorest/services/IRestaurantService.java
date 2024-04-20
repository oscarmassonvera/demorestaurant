package com.rabbit.demorest.services;

import org.springframework.http.ResponseEntity;

import com.rabbit.demorest.entities.Restaurant;

public interface IRestaurantService {
    public ResponseEntity<?> createRestaurant(Restaurant restaurant);
    public ResponseEntity<?> getAllRestaurants();
    public ResponseEntity<?> getRestaurantById(Long id);
    public ResponseEntity<?> updateRestaurant(Long id, Restaurant updatedRestaurant);
    public ResponseEntity<?> deleteRestaurant(Long id);
}
