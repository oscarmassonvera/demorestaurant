package com.rabbit.demorest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.rabbit.demorest.entities.Restaurant;

@Repository
public interface IRestaurantRepo extends JpaRepository<Restaurant, Long> {
    // Aquí puedes agregar métodos personalizados si necesitas consultas específicas
}
