package com.rabbit.demorest.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.demorest.entities.Orden;
import com.rabbit.demorest.services.OrdenServiceImpl;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    @Autowired
    private OrdenServiceImpl ordenService;

    @PostMapping
    public ResponseEntity<?> crearOrden(@RequestBody Orden orden) {
        try {
            Orden nuevaOrden = ordenService.guardarOrden(orden);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaOrden);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la orden: Datos incorrectos");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la orden: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarOrdenes() {
        try {
            List<Orden> ordenes = ordenService.listarOrdenes();
            return ResponseEntity.ok(ordenes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener las órdenes: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOrden(@PathVariable Long id) {
        try {
            ordenService.eliminarOrden(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la orden: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerOrdenPorId(@PathVariable Long id) {
        try {
            Orden orden = ordenService.obtenerOrdenPorId(id);
            if (orden != null) {
                return ResponseEntity.ok(orden);
            } else {
                return ((BodyBuilder) ResponseEntity.notFound()).body("La orden con el ID " + id + " no fue encontrada.");
            }
        } catch (Exception e) {
            // En producción, sería mejor loggear el error para propósitos de debugging.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la orden: " + e.getMessage());
        }
    }


    
                                                    //?ordenId=valor&productoId=valor
    @PostMapping("/{ordenId}/productos/{productoId}")
    public ResponseEntity<?> agregarProductoAOrden(@PathVariable Long ordenId, @PathVariable Long productoId) {
            try {
                return ordenService.agregarProductoAOrden(ordenId, productoId);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto a la orden: " + e.getMessage());
        }
    }
                                                                          

    @DeleteMapping("/{ordenId}/productos/{productoId}")
    public ResponseEntity<String> quitarProductoDeOrden(@PathVariable Long ordenId, @PathVariable Long productoId) {
        try {
            ordenService.quitarProductoDeOrden(ordenId, productoId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al quitar el producto de la orden: " + e.getMessage());
        }
    }

    @GetMapping("/{ordenId}/productos")
    public ResponseEntity<List<Map<String, Object>>> obtenerProductosPorOrdenConCantidad(@PathVariable Long ordenId) {
        List<Map<String, Object>> productosPorOrden = ordenService.obtenerProductosPorOrdenConCantidad(ordenId);
        if (productosPorOrden.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(productosPorOrden, HttpStatus.OK);
        }
    }










    

    @DeleteMapping("/{ordenId}/productos")
    public ResponseEntity<String> eliminarProductosPorOrdenId(@PathVariable Long ordenId) {
        try {
            ordenService.eliminarProductosPorOrdenId(ordenId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar los productos de la orden: " + e.getMessage());
        }
    }



    // Ejemplo de uso
    // /historial-ordenes?fechaInicio=2024-04-01T00:00:00&fechaFin=2024-04-30T23:59:59

    // En este JSON, fechaInicio representa el primer día del mes de marzo de 2022 a las 00:00:00 horas,
    // y fechaFin representa el último día del mismo mes a las 23:59:59 horas. Estas fechas de ejemplo 
    // pueden ser utilizadas para obtener el historial de órdenes con productos durante todo el mes 
    // de marzo de 2022.



    @GetMapping("/historial-ordenes")
    public ResponseEntity<?> obtenerHistorialOrdenesConProductosPorDiaCompleto(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaInicio, 
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fechaFin) {
        try {
            List<Map<String, Object>> historialOrdenes = ordenService.obtenerHistorialOrdenesConProductosPorDiaCompleto(fechaInicio, fechaFin);
            return ResponseEntity.ok(historialOrdenes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el historial de ordenes con productos: " + e.getMessage());
        }
    }

}
