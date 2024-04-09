package com.rabbit.demorest.services;

import java.util.List;
import java.util.Optional;

import com.rabbit.demorest.entities.Producto;

public interface IProductService {
    // Método para obtener todos los productos
    public List<Producto> getAllProducts() ;
    // Método para obtener un producto por su ID
    public Optional<Producto> getProductById(Long id);
    // Método para crear un nuevo producto
    public Producto createProduct(Producto product);
    // Método para actualizar un producto existente
    public Producto updateProduct(Long id, Producto updatedProduct) ;
    // Método para eliminar un producto por su ID
    public void deleteProduct(Long id);
}
