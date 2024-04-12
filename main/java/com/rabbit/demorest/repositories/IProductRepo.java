package com.rabbit.demorest.repositories;

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
    @Modifying
    @Query("UPDATE Producto p " +
            "SET p.cantidadEnStock = p.cantidadEnStock + " +
            "(SELECT COALESCE(SUM(op.cantidadProducto), 0) " +
            "FROM OrdenProducto op " +
            "WHERE op.producto = p AND op.orden.id = :ordenId)")
    void sumarCantidadStockProductosEnMasaConMismoNombre(@Param("ordenId") Long ordenId);
}


// SEGUIR CREANDO ESTE ESPACIO DE STOCK COMPROBAR QUE NO HAY NADA EN STOCK Y RESTARLE O SUMARLE LA CANTIDAD EN STOCK MIENTRAS SE 
// AGREGA O QUITA DE LAS ORDENES Y CREAR UN ESPACIO LLAMADO STOCK DONDE SE VEAN LOS PRODUCTOS CON SUS DATOS Y SUS FECHAS DE 
// REGISTRO O ACTUALIZACION 