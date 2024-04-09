package com.rabbit.demorest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rabbit.demorest.entities.FacturaDetalleDTO;
import com.rabbit.demorest.services.IFacturaService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;


    // MODIFICAR LOS PARAMS 


    @PostMapping("/{descuento}/{impuesto}/{direccionEnvio}/{idOrden}")
    public ResponseEntity<Object> crearFactura(@PathVariable("descuento") Double descuento,
                                                @PathVariable("impuesto") Double impuesto,
                                                @PathVariable("direccionEnvio") String direccionEnvio,
                                                @PathVariable("idOrden") Long idOrden) {
        try {
            facturaService.crearFactura(descuento, impuesto, direccionEnvio, idOrden);
            return ResponseEntity.status(HttpStatus.CREATED).body("Factura creada exitosamente");
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno al crear la factura: " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasFacturas() {
        try {
            List<FacturaDetalleDTO> facturas = facturaService.obtenerTodasLasFacturas();
            return ResponseEntity.ok(facturas);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error interno del servidor: " + ex.getMessage());
        }
    }


    // OBTENER FACTURA POR ID

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFacturaPorId(@PathVariable Long id) {
        try {
            FacturaDetalleDTO facturaDetalleDTO = facturaService.obtenerFacturaPorId(id);
            return ResponseEntity.ok(facturaDetalleDTO);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("No se encontró la factura con ID: " + id);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Error interno del servidor: " + ex.getMessage());
        }
    }


    @DeleteMapping("/{id}")
        public ResponseEntity<?> eliminarFacturaPorId(@PathVariable("id") Long id) {
            try {
                facturaService.eliminarFacturaPorId(id);
                return new ResponseEntity<>("Factura eliminada exitosamente", HttpStatus.OK);
            } catch (ResourceNotFoundException ex) {
                return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
            } catch (Exception ex) {
                return new ResponseEntity<>("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }        
}

