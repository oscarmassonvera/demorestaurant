package com.rabbit.demorest.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.rabbit.demorest.entities.Orden;



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
    @Query(value = "UPDATE ordenes o " +
               "SET o.total = COALESCE((SELECT ROUND(SUM(p.precio * op.cantidad_producto), 2) " +
                                      "FROM productos p JOIN orden_producto op ON p.id = op.producto_id " +
                                      "WHERE op.orden_id = :ordenId), 0) " +
               "WHERE o.id = :ordenId",
        nativeQuery = true)
    void actualizarTotalOrdenDespuesDeQuitarProducto(@Param("ordenId") Long ordenId);

    // OBTENER PRODUCTOS POR EL ID DE LA ORDEN

    @Query(value = "SELECT op.producto_id, COUNT(op.producto_id) AS cantidad_total FROM orden_producto op WHERE op.orden_id = :ordenId GROUP BY op.producto_id",
            nativeQuery = true)
    List<Object[]> obtenerCantidadTotalProductosPorOrdenId(Long ordenId);

    // ELIMINAR ORDEN POR ID 

    @Modifying
    @Query(value = "DELETE FROM orden_producto WHERE orden_id = :ordenId", nativeQuery = true)
    void eliminarRegistrosPorOrdenId(@Param("ordenId") Long ordenId);
    
    @Modifying
    @Query(value = "DELETE FROM Orden o WHERE o.id = :ordenId")
    void eliminarOrdenPorId(@Param("ordenId") Long ordenId);    

    // CREAR HISTORIAL DE ORDENES CON FECHA DIARIA DE ACUERDO AL DATE EN QUE FUE CREADO LA ORDEN 

    @Query(value = "SELECT DATE_FORMAT(o.fecha, '%Y-%m-%d') AS fecha_diaria, " +
    "DATE_FORMAT(o.fecha, '%H:%i') AS hora_con_minutos, " +
    "o.id AS id_orden " +
    "FROM ordenes o " +
    "GROUP BY DATE_FORMAT(o.fecha, '%Y-%m-%d'), DATE_FORMAT(o.fecha, '%H:%i'), o.id",
    nativeQuery = true)
    List<Object[]> crearHistorialOrdenesFechaDiaria();






}
