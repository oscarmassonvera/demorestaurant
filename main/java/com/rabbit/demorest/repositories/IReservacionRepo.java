package com.rabbit.demorest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rabbit.demorest.entities.Reservacion;

@Repository
public interface IReservacionRepo extends JpaRepository<Reservacion, Long> {
    // Aquí puedes agregar métodos personalizados si necesitas consultas específicas
}