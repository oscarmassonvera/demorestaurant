package com.rabbit.demorest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rabbit.demorest.entities.Restaurant;
import com.rabbit.demorest.entities.Rol;

@Repository
public interface IRestaurantRepo extends JpaRepository<Restaurant, Long> {
    // Aquí puedes agregar métodos personalizados si necesitas consultas específicas
    Optional<Restaurant> findByUser_Rol(Rol rol);
}
