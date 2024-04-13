package com.rabbit.demorest.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.rabbit.demorest.entities.Orden;
import com.rabbit.demorest.entities.Producto;



@Repository
public interface IOrdenRepo extends CrudRepository<Orden,Long> {

    // BUSCAR EL MODO DE QUE NO PASE EL BUCLE DE JSON AL MOMENTO DE VER LAS ORDENES PERO PARA Q EN PRODUCCION NO PASE ESO


    // AGREGAR PRODUCTO A LA ORDEN

    @Modifying
    @Query(value = "INSERT INTO orden_producto (orden_id, producto_id, cantidad_producto) " +
                "VALUES (:ordenId, :productoId, 1) " +
                "ON DUPLICATE KEY UPDATE cantidad_producto = cantidad_producto + 1", 
            nativeQuery = true)
    void agregarProductoAOrden(@Param("ordenId") Long ordenId, @Param("productoId") Long productoId);

    @Modifying
    @Query(value = "UPDATE ordenes o " +
                "SET o.total = (SELECT COALESCE(SUM(p.precio * op.cantidad_producto), 0) " +
                                "FROM productos p JOIN orden_producto op ON p.id = op.producto_id " +
                                "WHERE op.orden_id = :ordenId) " +
                "WHERE o.id = :ordenId",
            nativeQuery = true)
    void actualizarTotalOrden(@Param("ordenId") Long ordenId);

    // ELIMINAR TODOS LOS PRODCUTOS DE UNA ORDEN

    @Modifying
    @Query(value = "DELETE FROM orden_producto WHERE orden_id = :ordenId", nativeQuery = true)
    void eliminarProductosDeOrden(@Param("ordenId") Long ordenId);
    
    @Modifying
    @Query(value = "UPDATE ordenes SET total = 0 WHERE id = :ordenId", nativeQuery = true)
    void actualizarTotalOrdenDespuesDeEliminarProductos(@Param("ordenId") Long ordenId);

    // ELIMINAR PRODUCTO POR ID DE LA ORDEN   

   @Modifying
    @Query(value = "DELETE FROM orden_producto WHERE orden_id = :ordenId AND producto_id = :productoId LIMIT 1", nativeQuery = true)
    void eliminarProductDeOrden(@Param("ordenId") Long ordenId, @Param("productoId") Long productoId);

    @Modifying
    @Query(value = "DELETE FROM orden_producto WHERE orden_id = :ordenId AND producto_id = :productoId ", nativeQuery = true)
    void eliminarProductsDeOrden(@Param("ordenId") Long ordenId, @Param("productoId") Long productoId);

    @Modifying
    @Query(value = "UPDATE ordenes o " +
               "SET o.total = COALESCE((SELECT ROUND(SUM(p.precio * op.cantidad_producto), 2) " +
                                      "FROM productos p JOIN orden_producto op ON p.id = op.producto_id " +
                                      "WHERE op.orden_id = :ordenId), 0) " +
               "WHERE o.id = :ordenId",
        nativeQuery = true)
    void actualizarTotalOrdenDespuesDeQuitarProducto(@Param("ordenId") Long ordenId);

    @Query(value = "SELECT " +
    "o.id AS id_orden, " +
    "o.estado AS estado_orden, " +
    "o.total AS total_orden, " +
    "p.id AS id_producto, " +
    "p.nombre AS nombre_producto, " +
    "p.descripcion AS descripcion_producto, " +
    "p.precio AS precio_producto, " +
    "p.cantidad_stock AS cantidad_en_stock, " +
    "p.categoria AS categoria_producto, " +
    "COUNT(op.cantidad_producto) AS cantidad_producto " + // Agregar la cantidad del producto por orden
    "FROM ordenes o " +
    "JOIN orden_producto op ON o.id = op.orden_id " +
    "JOIN productos p ON op.producto_id = p.id " +
    "WHERE o.id = :ordenId " +
    "GROUP BY o.id, p.id", // Agrupar por id de orden y id de producto
    nativeQuery = true)
    List<Object[]> obtenerProductosPorOrdenConCantidad(@Param("ordenId") Long ordenId);

    // ELIMINAR ORDEN POR ID 

    @Modifying
    @Query(value = "DELETE FROM orden_producto WHERE orden_id = :ordenId", nativeQuery = true)
    void eliminarRegistrosPorOrdenId(@Param("ordenId") Long ordenId);
    
    @Modifying
    @Query(value = "DELETE FROM Orden o WHERE o.id = :ordenId")
    void eliminarOrdenPorId(@Param("ordenId") Long ordenId);    

    // CREAR HISTORIAL DE ORDENES CON FECHA DIARIA DE ACUERDO AL DATE EN QUE FUE CREADO LA ORDEN 

    @Query(value = "SELECT " +
    "DATE(o.fecha) AS fecha_diaria, " +
    "TIME(o.fecha) AS hora_con_minutos, " +
    "o.id AS id_orden, " +
    "o.estado AS estado_orden, " +
    "o.total AS total_orden, " +
    "p.id AS id_producto, " +
    "p.nombre AS nombre_producto, " +
    "p.descripcion AS descripcion_producto, " +
    "p.precio AS precio_producto, " +
    "p.cantidad_stock AS cantidad_en_stock, " +
    "p.categoria AS categoria_producto, " +
    "COUNT(op.cantidad_producto) AS cantidad_producto " +
    "FROM ordenes o " +
    "JOIN orden_producto op ON o.id = op.orden_id " +
    "JOIN productos p ON op.producto_id = p.id " +
    "WHERE DATE(o.fecha) BETWEEN :fechaInicio AND :fechaFin " +
    "GROUP BY DATE(o.fecha), TIME(o.fecha), o.id, p.id",
    nativeQuery = true)
List<Object[]> obtenerHistorialOrdenesConProductosPorFechas(@Param("fechaInicio") Date fechaInicio, @Param("fechaFin") Date fechaFin);











    @Modifying
    @Query(value = "DELETE FROM facturas WHERE orden_id = :ordenId", nativeQuery = true)
    void eliminarRegistrosDeFacturasPorOrdenId(@Param("ordenId") Long ordenId);









// PARA QUITAR PRODUCTO DE ORDEN

    @Query(value = "SELECT p FROM Producto p " +
            "JOIN OrdenProducto op ON p.id = op.producto.id " +
            "WHERE op.orden.id = :ordenId")
    List<Producto> obtenerProductosPorOrdenId(@Param("ordenId") Long ordenId);


    @Query(value = "SELECT COUNT(op) > 0 FROM OrdenProducto op " +
    "WHERE op.orden.id = :ordenId AND op.producto.id = :productoId")
    boolean existeProductoEnOrden(@Param("ordenId") Long ordenId, @Param("productoId") Long productoId);



    

}
