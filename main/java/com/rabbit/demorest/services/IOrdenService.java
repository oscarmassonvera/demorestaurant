package com.rabbit.demorest.services;

import java.util.List;
import java.util.Map;

import com.rabbit.demorest.entities.Orden;


public interface IOrdenService {

    public Orden guardarOrden(Orden orden);
    public void eliminarOrden(Long id);
    public Orden obtenerOrdenPorId(Long id);
    public List<Orden> listarOrdenes();
    public Map<String, Object> obtenerProductosPorOrdenId(Long ordenId);
    public void eliminarProductosPorOrdenId(Long ordenId);
    public void agregarProductoAOrden(Long ordenId, Long productoId);
    public List<Map<String, Object>> obtenerHistorialOrdenesFechaDiaria();
}
