package com.rabbit.demorest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabbit.demorest.entities.Menu;

@Repository
public interface IMenuRepo extends JpaRepository<Menu, Long> {
    // Aquí puedes agregar métodos personalizados si necesitas consultas específicas
}