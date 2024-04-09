package com.rabbit.demorest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener la orden: " + e.getMessage());
        }
    }

    
                                                                          //?ordenId=valor&productoId=valor
    @PostMapping("/{ordenId}/productos/{productoId}")
    public ResponseEntity<?> agregarProductoAOrden(@PathVariable Long ordenId, @PathVariable Long productoId) {
        try {
            ordenService.agregarProductoAOrden(ordenId, productoId);
            return ResponseEntity.ok("Producto agregado a la orden correctamente.");
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
    public ResponseEntity<?> obtenerProductosPorOrdenId(@PathVariable Long ordenId) {
        try {
            Map<String, Object> resultado = ordenService.obtenerProductosPorOrdenId(ordenId);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error al obtener los productos de la orden: " + e.getMessage());
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
    @GetMapping("/historial")
    public ResponseEntity<List<Map<String, Object>>> obtenerHistorialOrdenesFechaDiaria() {
        try {
            List<Map<String, Object>> historial = ordenService.obtenerHistorialOrdenesFechaDiaria();
            return ResponseEntity.ok(historial);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Error al obtener el historial de órdenes: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of(errorResponse));
        }
    }

}
