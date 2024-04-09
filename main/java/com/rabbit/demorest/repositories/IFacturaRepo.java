package com.rabbit.demorest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rabbit.demorest.entities.Factura;

@Repository
public interface IFacturaRepo extends CrudRepository<Factura,Long> {

    @Query(value = "SELECT COUNT(*) FROM (SELECT SUBSTRING(numero_factura, 21) AS num_factura FROM facturas WHERE DATE(fecha_emision) = CURDATE() GROUP BY DATE_FORMAT(fecha_emision, '%Y-%m-%d'), numero_factura) AS subquery", nativeQuery = true)
    Integer obtenerUltimoNumeroFactura();
    










}
