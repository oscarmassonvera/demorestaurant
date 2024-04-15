package com.rabbit.demorest.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabbit.demorest.entities.EstadoOrden;
import com.rabbit.demorest.entities.Orden;
import com.rabbit.demorest.entities.Producto;
import com.rabbit.demorest.repositories.IOrdenRepo;
import com.rabbit.demorest.repositories.IProductRepo;


@Service
public class OrdenServiceImpl implements IOrdenService {
    @Autowired
    private IOrdenRepo ordenRepo;
    @Autowired
    private IProductRepo productRepo;
    //                                                          OK
    @Transactional
    @Modifying
    public ResponseEntity<String> agregarProductoAOrden(Long ordenId, Long productoId) {
        try {
            // Verificar si la orden existe antes de agregar el producto
            Optional<Orden> ordenOptional = ordenRepo.findById(ordenId);
            if (ordenOptional.isPresent()) {
                // Verificar si el producto existe antes de agregarlo a la orden
                Optional<Producto> productoOptional = productRepo.findById(productoId);
                if (productoOptional.isPresent()) {
                    Producto producto = productoOptional.get();
                    if(producto.getEsCocinable()==true){
                        ordenRepo.agregarProductoAOrden(ordenId, productoId);
                        ordenRepo.actualizarTotalOrden(ordenId);
                        return ResponseEntity.ok("Producto agregado a la orden exitosamente.");
                    }else{
                        if (producto.getCantidadEnStock() != null && producto.getCantidadEnStock() > 0) {
                            ordenRepo.agregarProductoAOrden(ordenId, productoId);
                            ordenRepo.actualizarTotalOrden(ordenId);
                            productRepo.restarCantidadStockProducto(productoId);
                            return ResponseEntity.ok("Producto agregado a la orden exitosamente.");
                        }
                        else {
                            throw new Exception("La cantidad de este producto en stock es nula.");
                        }
                    }
                } else {
                    return ResponseEntity.notFound().build(); // Producto no encontrado
                }
            } else {
                return ResponseEntity.notFound().build(); // Orden no encontrada
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al agregar el producto a la orden: " + e.getMessage());
        }
    }

    //                                                              OK
    @Transactional
    public void eliminarProductosPorOrdenId(Long ordenId) {
        Optional<Orden> ordenOptional = ordenRepo.findById(ordenId);
        if (!ordenOptional.isPresent()) {
            throw new IllegalArgumentException("La orden con ID " + ordenId + " no existe.");
        }
        
        List<Producto> productosEnOrden = ordenRepo.obtenerProductosPorOrdenId(ordenId);
        
        if (productosEnOrden.isEmpty()) {
            throw new IllegalArgumentException("La orden con ID " + ordenId + " no contiene productos.");
        }
        
        List<Object[]> cantidadProductosPorIdList = productRepo.obtenerCantidadProductosPorOrdenId(ordenId);
        Map<Long, Integer> cantidadProductosPorId = new HashMap<>();
        for (Object[] obj : cantidadProductosPorIdList) {
            Long productoId = (Long) obj[0];
            Integer cantidad = ((Number) obj[1]).intValue();
            cantidadProductosPorId.put(productoId, cantidad);
        }

        for (Producto producto : productosEnOrden) {
            if (!ordenRepo.existeProductoEnOrden(ordenId, producto.getId())) {
                throw new IllegalArgumentException("El producto con ID " + producto.getId() + " no está en la orden.");
            }
            try {
                int cantidadProducto = cantidadProductosPorId.getOrDefault(producto.getId(), 0);
                if (producto.getEsCocinable()==true) {
                    ordenRepo.eliminarProductsDeOrden(ordenId, producto.getId());
                    ordenRepo.actualizarTotalOrdenDespuesDeEliminarProductos(ordenId);    
                }else{
                    productRepo.sumarCantidadStockProducto(producto.getId(), cantidadProducto);
                    ordenRepo.eliminarProductsDeOrden(ordenId, producto.getId());
                    ordenRepo.actualizarTotalOrdenDespuesDeEliminarProductos(ordenId);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar el producto de la orden: " + e.getMessage());
            }
        }
    }

    //                                                              OK
    @Transactional
    @Modifying
    public void quitarProductoDeOrden(Long ordenId, Long productoId) {
        Optional<Orden> ordenOptional = ordenRepo.findById(ordenId);
        if (!ordenOptional.isPresent()) {
            throw new IllegalArgumentException("La orden con ID " + ordenId + " no existe.");
        }
        
        Optional<Producto> productoOptional = productRepo.findById(productoId);
        if (!productoOptional.isPresent()) {
            throw new IllegalArgumentException("El producto con ID " + productoId + " no existe.");
        }
        
        if (!ordenRepo.existeProductoEnOrden(ordenId, productoId)) {
            throw new IllegalArgumentException("El producto con ID " + productoId + " no está en la orden.");
        }

        try {
            if (productoOptional.get().getEsCocinable()==true) {
                ordenRepo.eliminarProductDeOrden(ordenId, productoId);
                ordenRepo.actualizarTotalOrdenDespuesDeQuitarProducto(ordenId);                
            }
            else{
                ordenRepo.eliminarProductDeOrden(ordenId, productoId);
                ordenRepo.actualizarTotalOrdenDespuesDeQuitarProducto(ordenId);
                productRepo.sumarCantidadStockProducto(productoId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al quitar el producto de la orden: " + e.getMessage());
        }
    }

    //                                                          OK
    @Transactional(readOnly = true)
    public List<Orden> listarOrdenes() {
        return (List<Orden>) ordenRepo.findAll();
    }
    //                                                          OK
    @Transactional
    @Modifying
    public Orden guardarOrden(Orden orden) {
        orden.setFecha(new Date());
        orden.setEstado(EstadoOrden.PROCESANDO); // Establecer el estado como PROCESANDO
        return ordenRepo.save(orden);
    }
    //                                                          OK
    @Transactional(readOnly = true)
    public Orden obtenerOrdenPorId(Long id) {
        Optional<Orden> optionalOrden = ordenRepo.findById(id);
        return optionalOrden.orElse(null);
    }
    //                                                          OK
    @Transactional
    public void eliminarOrden(Long id) {
        // Obtener la orden por su ID
        Optional<Orden> ordenOptional = ordenRepo.findById(id);
        if (!ordenOptional.isPresent()) {
            throw new IllegalArgumentException("La orden con ID " + id + " no existe.");
        }
        
        // Obtener los productos de la orden
        List<Producto> productosEnOrden = ordenRepo.obtenerProductosPorOrdenId(id);
        
        // Verificar si la orden contiene productos
        if (productosEnOrden.isEmpty()) {
            throw new IllegalArgumentException("La orden con ID " + id + " no contiene productos.");
        }
        
        // Obtener la cantidad de productos por ID de producto
        List<Object[]> cantidadProductosPorIdList = productRepo.obtenerCantidadProductosPorOrdenId(id);
        Map<Long, Integer> cantidadProductosPorId = new HashMap<>();
        for (Object[] obj : cantidadProductosPorIdList) {
            Long productoId = (Long) obj[0];
            Integer cantidad = ((Number) obj[1]).intValue();
            cantidadProductosPorId.put(productoId, cantidad);
        }

        for (Producto producto : productosEnOrden) {
            try {
                // Obtener la cantidad de veces que se repite el producto en la orden
                int cantidadProducto = cantidadProductosPorId.getOrDefault(producto.getId(), 0);
                // Sumar la cantidad al stock del producto
                productRepo.sumarCantidadStockProducto(producto.getId(), cantidadProducto);
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar la cantidad en stock de los productos: " + e.getMessage());
            }
        }
        
        // Eliminar primero los registros de facturas asociados
        ordenRepo.eliminarRegistrosDeFacturasPorOrdenId(id);
        // Luego eliminar la orden y actualizar el total
        ordenRepo.eliminarRegistrosPorOrdenId(id);
        ordenRepo.eliminarOrdenPorId(id);
        ordenRepo.actualizarTotalOrdenDespuesDeQuitarProducto(id);
    }

    //                                                          OK    
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerProductosPorOrdenConCantidad(Long ordenId) {
        List<Object[]> resultados = ordenRepo.obtenerProductosPorOrdenConCantidad(ordenId);
        if (resultados.isEmpty()) {
            throw new RuntimeException("La orden con ID " + ordenId + " no fue encontrada");
        }
        return resultados.stream()
            .map(resultado -> {
                Map<String, Object> producto = new HashMap<>();
                producto.put("id_orden", resultado[0]);
                producto.put("estado_orden", resultado[1]);
                producto.put("total_orden", resultado[2]);
                producto.put("id_producto", resultado[3]);
                producto.put("nombre_producto", resultado[4]);
                producto.put("descripcion_producto", resultado[5]);
                producto.put("precio_producto", resultado[6]);
                producto.put("cantidad_en_stock", resultado[7]);
                producto.put("categoria_producto", resultado[8]);
                producto.put("cantidad de este producto por orden", resultado[9]);
                return producto;
            })
            .collect(Collectors.toList());
    }

    //                                                          OK
    // HISTORIAL DE ORDENES POR DIA Y HORA
    @Transactional(readOnly = true)
    public List<Map<String, Object>> obtenerHistorialOrdenesConProductosPorDiaCompleto(Date fechaInicio, Date fechaFin) {
        // Convertir las fechas a LocalDate
        LocalDate fechaInicioLocal = fechaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinLocal = fechaFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();// Obtener el inicio y final del día
        LocalDateTime inicioDelDia = fechaInicioLocal.atStartOfDay();
        LocalDateTime finDelDia = fechaFinLocal.atTime(LocalTime.MAX);
        
        // Convertir LocalDateTime a Date
        Date fechaInicioCompleta = Date.from(inicioDelDia.atZone(ZoneId.systemDefault()).toInstant());
        Date fechaFinCompleta = Date.from(finDelDia.atZone(ZoneId.systemDefault()).toInstant());
        
        List<Object[]> resultados = ordenRepo.obtenerHistorialOrdenesConProductosPorFechas(fechaInicioCompleta, fechaFinCompleta);
        
        return resultados.stream()
                .collect(Collectors.groupingBy(resultado -> resultado[2], LinkedHashMap::new, Collectors.toList())) // Agrupar por ID de orden
                .entrySet().stream()
                .map(entry -> {
                    Map<String, Object> entradaHistorial = new HashMap<>();
                    entradaHistorial.put("id_orden", entry.getKey());
                    entradaHistorial.put("fecha de creacion de la orden", entry.getValue().get(0)[0]);
                    entradaHistorial.put("hora con minutos de creacion de la orden", entry.getValue().get(0)[1]);
                    entradaHistorial.put("estado", entry.getValue().get(0)[3]);
                    entradaHistorial.put("total", entry.getValue().get(0)[4]);
        
                    List<Map<String, Object>> productos = entry.getValue().stream()
                            .map(resultado -> {
                                Map<String, Object> producto = new HashMap<>();
                                producto.put("id", resultado[5]);
                                producto.put("nombre", resultado[6]);
                                producto.put("descripcion", resultado[7]);
                                producto.put("precio", resultado[8]);
                                producto.put("cantidadEnStock", resultado[9]);
                                producto.put("categoria", resultado[10]);
                                producto.put("cantidad de este producto por orden", resultado[11]);
                                return producto;
                            })
                            .collect(Collectors.toList());
                            
                    entradaHistorial.put("productos", productos);
                    return entradaHistorial;
                })
                .collect(Collectors.toList());
        }

}
