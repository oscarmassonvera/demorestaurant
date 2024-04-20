package com.rabbit.demorest.services;

import org.springframework.http.ResponseEntity;

import com.rabbit.demorest.entities.Reservacion;

public interface IReservacionService {
    public ResponseEntity<?> createReservacion(Reservacion reservacion);
    public ResponseEntity<?> getAllReservaciones();
    public ResponseEntity<?> getReservacionById(Long id);
    public ResponseEntity<?> updateReservacion(Long id, Reservacion updatedReservacion);
    public ResponseEntity<?> deleteReservacion(Long id);
}
