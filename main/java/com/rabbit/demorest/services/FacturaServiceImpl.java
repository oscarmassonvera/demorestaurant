package com.rabbit.demorest.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabbit.demorest.entities.EstadoOrden;
import com.rabbit.demorest.entities.Factura;
import com.rabbit.demorest.entities.FacturaDetalleDTO;
import com.rabbit.demorest.entities.Orden;
import com.rabbit.demorest.entities.OrdenProducto;
import com.rabbit.demorest.entities.Producto;
import com.rabbit.demorest.entities.ProductoDTO;
import com.rabbit.demorest.repositories.IFacturaRepo;
import com.rabbit.demorest.repositories.IOrdenRepo;

@Service
public class FacturaServiceImpl implements IFacturaService {

    @Autowired
    private IFacturaRepo facturaRepository;

    @Autowired
    private IOrdenRepo ordenRepository;
    @Transactional
    @Override
    public Factura crearFactura(Double descuentoMonto, Double impuestoMonto, String direccionEnvio, Long idOrden) {
        try {
            // Obtener la orden por su ID
            Orden orden = ordenRepository.findById(idOrden)
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró la orden con ID: " + idOrden));
            
            // Cambiar el estado de la orden a FACTURADO
            orden.setEstado(EstadoOrden.FACTURADO);
            
            // Calcular el subtotal como el total de la orden
            Double subtotal = orden.getTotal();
    
            // Validar que los porcentajes de descuento e impuesto estén en el rango adecuado (entre 0 y 100)
            if (descuentoMonto < 0 || descuentoMonto > 100 || impuestoMonto < 0 || impuestoMonto > 100) {
                throw new IllegalArgumentException("Los porcentajes de descuento e impuesto deben estar entre 0 y 100.");
            }
    
            // Aplicar descuento 
            Double descuento = subtotal * (descuentoMonto / 100);
            Double valorConDescuento = subtotal - descuento;
    
            // Aplicar impuesto
            Double impuesto = valorConDescuento * (impuestoMonto / 100);
            Double subtotalConImpuesto = valorConDescuento + impuesto;
    
            // Calcular el monto total sumando el impuesto al subtotal con descuento
            Double montoTotal = subtotalConImpuesto;
    
            // Redondear el monto total a dos decimales
            BigDecimal montoTotalRedondeado = BigDecimal.valueOf(montoTotal)
                    .setScale(2, RoundingMode.HALF_UP);
    
            // Obtener la fecha actual como fecha de emisión
            Date fechaEmision = new Date();
    
            // Calcular la fecha de vencimiento tres días después de la fecha de emisión
            Date fechaVencimiento = new Date(fechaEmision.getTime() + (3 * 24 * 60 * 60 * 1000)); // 3 días en milisegundos
    
            // Generar el número de factura
            String prefijo = "FACTURA-SERVICIO-";
            String numeroFactura = generarNumeroFactura(prefijo);
    
            // Un metodo de pago predefinido pero hay que aumentarle para que acepte mas metodos.
            String metodoPago = "Efectivo";
    
            // Crear una nueva factura
            Factura factura = new Factura();
            factura.setNumeroFactura(numeroFactura);
            factura.setFechaEmision(fechaEmision);
            factura.setMetodoPago(metodoPago);
            factura.setFechaVencimiento(fechaVencimiento);
            factura.setDescuentos(descuentoMonto);
            factura.setImpuestos(impuestoMonto);
            factura.setSubtotal(subtotal);
            factura.setMontoTotal(montoTotalRedondeado.doubleValue());
            factura.setDireccionEnvio(direccionEnvio);
            factura.setOrden(orden);
    
            // Guardar la factura en la base de datos
            return facturaRepository.save(factura);
        } catch (IllegalArgumentException ex) {
            // Manejar excepción de argumento ilegal
            throw ex;
        } catch (Exception ex) {
            // Manejar cualquier otra excepción y registrarla
            ex.printStackTrace();
            throw new RuntimeException("Error al crear la factura: " + ex.getMessage());
        }
    }
    
    private String generarNumeroFactura(String prefijo) {
        Integer ultimoNumero = facturaRepository.obtenerUltimoNumeroFactura();
        int nuevoNumero = (ultimoNumero != null) ? ultimoNumero + 1 : 1;
        String numeroFactura = String.format("%s%04d", prefijo, nuevoNumero);
        return numeroFactura;
    }
    

    
// ---------------------------- 

    @Override
    public FacturaDetalleDTO obtenerFacturaPorId(Long id) {
        Factura factura = facturaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("No se encontró la factura con ID: " + id));

        FacturaDetalleDTO facturaDetalleDTO = new FacturaDetalleDTO();
        facturaDetalleDTO.setFacturaId(factura.getId());
        facturaDetalleDTO.setNumeroFactura(factura.getNumeroFactura());
        facturaDetalleDTO.setFechaEmision(factura.getFechaEmision());
        facturaDetalleDTO.setFechaVencimiento(factura.getFechaVencimiento());
        facturaDetalleDTO.setDescuentos(factura.getDescuentos());
        facturaDetalleDTO.setSubtotal(factura.getSubtotal());
        facturaDetalleDTO.setImpuestos(factura.getImpuestos());
        facturaDetalleDTO.setMontoTotal(factura.getMontoTotal());
        facturaDetalleDTO.setDireccionEnvio(factura.getDireccionEnvio());
        facturaDetalleDTO.setMetodoPago(factura.getMetodoPago());

        Orden orden = factura.getOrden();
        facturaDetalleDTO.setOrdenId(orden.getId());
        facturaDetalleDTO.setFechaOrden(orden.getFecha());
        facturaDetalleDTO.setEstadoOrden(orden.getEstado());
        facturaDetalleDTO.setTotalOrden(orden.getTotal());

        List<ProductoDTO> productosDTO = new ArrayList<>();
        List<OrdenProducto> ordenProductos = orden.getOrdenProducto();
        for (OrdenProducto op : ordenProductos) {
            ProductoDTO productoDTO = new ProductoDTO();
            Producto producto = op.getProducto();
            productoDTO.setId(producto.getId());
            productoDTO.setCantidadEnStock(producto.getCantidadEnStock());
            productoDTO.setCategoria(producto.getCategoria());
            productoDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setFechaActualizacion(producto.getFechaActualizacion());
            productoDTO.setFechaCreacion(producto.getFechaCreacion());
            productoDTO.setNombre(producto.getNombre());
            productoDTO.setPrecio(producto.getPrecio());
            productosDTO.add(productoDTO);
        }

        // Asigna la lista de productosDTO a facturaDetalleDTO
        facturaDetalleDTO.setProductos(productosDTO);

        return facturaDetalleDTO;
    }


// ----------------------------

@Override
public List<FacturaDetalleDTO> obtenerTodasLasFacturas() {
    List<Factura> facturas = (List<Factura>) facturaRepository.findAll();
    List<FacturaDetalleDTO> facturaDetalleDTOs = new ArrayList<>();

    for (Factura factura : facturas) {
        FacturaDetalleDTO facturaDetalleDTO = new FacturaDetalleDTO();
        facturaDetalleDTO.setFacturaId(factura.getId());
        facturaDetalleDTO.setNumeroFactura(factura.getNumeroFactura());
        facturaDetalleDTO.setFechaEmision(factura.getFechaEmision());
        facturaDetalleDTO.setFechaVencimiento(factura.getFechaVencimiento());
        facturaDetalleDTO.setDescuentos(factura.getDescuentos());
        facturaDetalleDTO.setSubtotal(factura.getSubtotal());
        facturaDetalleDTO.setImpuestos(factura.getImpuestos());
        facturaDetalleDTO.setMontoTotal(factura.getMontoTotal());
        facturaDetalleDTO.setDireccionEnvio(factura.getDireccionEnvio());
        facturaDetalleDTO.setMetodoPago(factura.getMetodoPago());

        Orden orden = factura.getOrden();
        facturaDetalleDTO.setOrdenId(orden.getId());
        facturaDetalleDTO.setFechaOrden(orden.getFecha());
        facturaDetalleDTO.setEstadoOrden(orden.getEstado());
        facturaDetalleDTO.setTotalOrden(orden.getTotal());

        List<ProductoDTO> productosDTO = new ArrayList<>();
        List<OrdenProducto> ordenProductos = orden.getOrdenProducto();
        for (OrdenProducto op : ordenProductos) {
            ProductoDTO productoDTO = new ProductoDTO();
            Producto producto = op.getProducto();
            productoDTO.setId(producto.getId());
            productoDTO.setCantidadEnStock(producto.getCantidadEnStock());
            productoDTO.setCategoria(producto.getCategoria());
            productoDTO.setDescripcion(producto.getDescripcion());
            productoDTO.setFechaActualizacion(producto.getFechaActualizacion());
            productoDTO.setFechaCreacion(producto.getFechaCreacion());
            productoDTO.setNombre(producto.getNombre());
            productoDTO.setPrecio(producto.getPrecio());
            productosDTO.add(productoDTO);
        }

        facturaDetalleDTO.setProductos(productosDTO);

        facturaDetalleDTOs.add(facturaDetalleDTO);
    }

    return facturaDetalleDTOs;
}


// ELIMINAR FACTURA POR ID




@Override
public void eliminarFacturaPorId(Long id) {
    try {
        // Verificar si la factura existe
        Factura factura = facturaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la factura con ID: " + id));

        // Eliminar la factura
        facturaRepository.delete(factura);
    } catch (ResourceNotFoundException ex) {
        // Manejar excepción si la factura no se encuentra
        throw ex;
    } catch (Exception ex) {
        // Manejar cualquier otra excepción y registrarla
        ex.printStackTrace();
        throw new RuntimeException("Error al eliminar la factura: " + ex.getMessage());
    }
}








// MODIFICAR FACTURA ???





















}
