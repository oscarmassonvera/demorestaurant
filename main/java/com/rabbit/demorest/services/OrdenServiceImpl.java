package com.rabbit.demorest.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabbit.demorest.entities.Orden;
import com.rabbit.demorest.repositories.IOrdenRepo;


@Service
public class OrdenServiceImpl implements IOrdenService {
    @Autowired
    private IOrdenRepo ordenRepo;

    @Transactional
    @Modifying
    public void agregarProductoAOrden(Long ordenId, Long productoId) {
        ordenRepo.agregarProductoAOrden(ordenId, productoId);
        ordenRepo.actualizarTotalOrden(ordenId);
    }

    @Transactional
    @Modifying
    public void eliminarProductosPorOrdenId(Long ordenId ) {
        ordenRepo.eliminarProductosDeOrden(ordenId);
        ordenRepo.actualizarTotalOrdenDespuesDeEliminarProductos(ordenId);
    }

    @Transactional
    @Modifying
    public void quitarProductoDeOrden(Long ordenId, Long productoId) {
        ordenRepo.eliminarProductDeOrden(ordenId, productoId);
        ordenRepo.actualizarTotalOrdenDespuesDeQuitarProducto(ordenId);
    }

    @Transactional(readOnly = true)
    public List<Orden> listarOrdenes() {
        return (List<Orden>) ordenRepo.findAll();
    }
    
    @Transactional
    @Modifying
    public Orden guardarOrden(Orden orden) {
        orden.setFecha(new Date());
        return ordenRepo.save(orden);
    }
    @Transactional(readOnly = true)
    public Orden obtenerOrdenPorId(Long id) {
        Optional<Orden> optionalOrden = ordenRepo.findById(id);
        return optionalOrden.orElse(null);
    }

    @Transactional
    @Modifying
    public void eliminarOrden(Long id) {
        ordenRepo.eliminarRegistrosPorOrdenId(id);
        ordenRepo.eliminarOrdenPorId(id);
        ordenRepo.actualizarTotalOrdenDespuesDeQuitarProducto(id);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> obtenerProductosPorOrdenId(Long ordenId) {


        Map<String, Object> resultado = new HashMap<>();
        List<Object[]> resultadosQuery = ordenRepo.obtenerCantidadTotalProductosPorOrdenId(ordenId);
        List<Long> idsProductos = new ArrayList<>();
        Map<Long, Long> cantidadPorProducto = new HashMap<>();

        for (Object[] fila : resultadosQuery) {
            Long idProducto = (Long) fila[0];
            Long cantidadTotal = (Long) fila[1];
            idsProductos.add(idProducto);
            cantidadPorProducto.put(idProducto, cantidadTotal);
        }

        resultado.put("ids_productos", idsProductos);
        resultado.put("cantidad_por_producto", cantidadPorProducto);

        return resultado;
    }

    // HISTORIAL DE ORDENES POR DIA Y HORA

    public List<Map<String, Object>> obtenerHistorialOrdenesFechaDiaria() {
        return ordenRepo.crearHistorialOrdenesFechaDiaria().stream()
                .map(resultado -> {
                    Map<String, Object> entradaHistorial = new HashMap<>();
                    entradaHistorial.put("fecha_diaria", resultado[0]);
                    entradaHistorial.put("hora_con_minutos", resultado[1]);
                    entradaHistorial.put("id_orden", resultado[2]);
                    return entradaHistorial;
                })
                .collect(Collectors.toList());
    }

}
