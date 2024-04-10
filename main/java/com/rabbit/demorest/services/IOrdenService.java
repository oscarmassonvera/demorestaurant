package com.rabbit.demorest.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.rabbit.demorest.entities.Orden;


public interface IOrdenService {

    public Orden guardarOrden(Orden orden);
    public void eliminarOrden(Long id);
    public Orden obtenerOrdenPorId(Long id);
    public List<Orden> listarOrdenes();
    List<Map<String, Object>> obtenerProductosPorOrdenConCantidad(Long ordenId);
    public void eliminarProductosPorOrdenId(Long ordenId);
    public ResponseEntity<String> agregarProductoAOrden(Long ordenId, Long productoId);
    public List<Map<String, Object>> obtenerHistorialOrdenesConProductosPorDiaCompleto(Date fechaInicio, Date fechaFin);
}
