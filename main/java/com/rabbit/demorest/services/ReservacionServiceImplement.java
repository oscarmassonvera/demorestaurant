package com.rabbit.demorest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rabbit.demorest.entities.Reservacion;
import com.rabbit.demorest.repositories.IReservacionRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ReservacionServiceImplement implements IReservacionService { 
    @Autowired
    private IReservacionRepo reservacionRepo;

    // Método para crear una reservación
    public ResponseEntity<?> createReservacion(Reservacion reservacion) {
        try {
            Reservacion createdReservacion = reservacionRepo.save(reservacion);
            return new ResponseEntity<>(createdReservacion, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo crear la reservación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para obtener todas las reservaciones
    public ResponseEntity<?> getAllReservaciones() {
        List<Reservacion> reservaciones = reservacionRepo.findAll();
        if (reservaciones.isEmpty()) {
            return new ResponseEntity<>("No se encontraron reservaciones", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservaciones, HttpStatus.OK);
    }

    // Método para obtener una reservación por ID
    public ResponseEntity<?> getReservacionById(Long id) {
        Optional<Reservacion> reservacionOptional = reservacionRepo.findById(id);
        if (reservacionOptional.isPresent()) {
            return new ResponseEntity<>(reservacionOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Reservación no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // Método para actualizar una reservación
    public ResponseEntity<?> updateReservacion(Long id, Reservacion updatedReservacion) {
        try {
            Optional<Reservacion> reservacionOptional = reservacionRepo.findById(id);
            if (reservacionOptional.isPresent()) {
                updatedReservacion.setId(id); // Asegurarse de que el ID de la reservación a actualizar sea el correcto
                Reservacion savedReservacion = reservacionRepo.save(updatedReservacion);
                return new ResponseEntity<>(savedReservacion, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Reservación no encontrada", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo actualizar la reservación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Método para eliminar una reservación
    public ResponseEntity<?> deleteReservacion(Long id) {
        try {
            reservacionRepo.deleteById(id);
            return new ResponseEntity<>("Reservación eliminada exitosamente", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("Reservación no encontrada", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("No se pudo eliminar la reservación", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
