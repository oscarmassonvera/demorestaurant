package com.rabbit.demorest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rabbit.demorest.entities.Reservacion;
import com.rabbit.demorest.services.ReservacionServiceImplement;

@RestController
@RequestMapping("/api/reservacion")
public class ReservacionController {

    @Autowired
    private ReservacionServiceImplement reservacionService;

    @PostMapping
    public ResponseEntity<?> createReservacion(@RequestBody Reservacion reservacion) {
        return reservacionService.createReservacion(reservacion);
    }

    @GetMapping
    public ResponseEntity<?> getAllReservaciones() {
        return reservacionService.getAllReservaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservacionById(@PathVariable Long id) {
        return reservacionService.getReservacionById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservacion(@PathVariable Long id, @RequestBody Reservacion updatedReservacion) {
        return reservacionService.updateReservacion(id, updatedReservacion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservacion(@PathVariable Long id) {
        return reservacionService.deleteReservacion(id);
    }
}
