package com.rabbit.demorest.services;

import java.util.List;

import com.rabbit.demorest.entities.Factura;
import com.rabbit.demorest.entities.FacturaDetalleDTO;



public interface IFacturaService {
   public Factura crearFactura(Double descuento, Double impuesto, String direccionEnvio, Long idOrden);
   public List<FacturaDetalleDTO> obtenerTodasLasFacturas();
   public FacturaDetalleDTO obtenerFacturaPorId(Long id);
   public void eliminarFacturaPorId(Long id);
}
