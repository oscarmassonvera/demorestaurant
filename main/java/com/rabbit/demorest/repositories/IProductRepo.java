package com.rabbit.demorest.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rabbit.demorest.entities.Producto;

@Repository
public interface IProductRepo extends CrudRepository<Producto,Long> {
    
}
