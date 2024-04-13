package com.rabbit.demorest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rabbit.demorest.entities.Producto;

@Repository
public interface IProductRepo extends CrudRepository<Producto,Long> {
    
    @Modifying
    @Query(value = "UPDATE Producto p " +
                    "SET p.cantidadEnStock = p.cantidadEnStock - 1 " +
                    "WHERE p.id = :productoId")
    void restarCantidadStockProducto(@Param("productoId") Long productoId);
    
    @Modifying
    @Query(value = "UPDATE Producto p " +
                    "SET p.cantidadEnStock = p.cantidadEnStock + 1 " +
                    "WHERE p.id = :productoId")
    void sumarCantidadStockProducto(@Param("productoId") Long productoId);

    @Query("SELECT op.producto.id, COUNT(op) " +
           "FROM OrdenProducto op " +
           "WHERE op.orden.id = :ordenId " +
           "GROUP BY op.producto.id")
    List<Object[]> obtenerCantidadProductosPorOrdenId(@Param("ordenId") Long ordenId);

    @Modifying
    @Query("UPDATE Producto p SET p.cantidadEnStock = p.cantidadEnStock + :cantidad WHERE p.id = :productoId")
    void sumarCantidadStockProducto(@Param("productoId") Long productoId, @Param("cantidad") Integer cantidad);
    
    // EL APARTADO DE STOCK PARA BUSCAR PRODUCTOS Y AGRUPARLOS DE DISTINTA MANERA.

    // Consulta para obtener todos los datos del producto agrupados por categoría
    @Query("SELECT p.categoria, COUNT(p), p.fechaCreacion, p.cantidadEnStock, p.precio " + 
    "FROM Producto p " +
    "GROUP BY p.categoria, p.fechaCreacion, p.cantidadEnStock, p.precio")
    List<Object[]> findProductDataGroupedByCategory();

    // Consulta para obtener todos los datos del producto ordenados por fecha de creación
    @Query("SELECT p " + 
    "FROM Producto p " +
    "ORDER BY p.fechaCreacion")
    List<Producto> findProductsOrderByDate();

    // Consulta para obtener todos los datos del producto ordenados por cantidad en stock
    @Query("SELECT p " + 
    "FROM Producto p " +
    "ORDER BY p.cantidadEnStock")
    List<Producto> findProductsOrderByStock();

    // Consulta para obtener todos los datos del producto ordenados por precio
    @Query("SELECT p " + 
    "FROM Producto p " +
    "ORDER BY p.precio")
    List<Producto> findProductsOrderByPrice();
}


