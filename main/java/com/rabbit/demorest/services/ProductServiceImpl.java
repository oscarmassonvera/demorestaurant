package com.rabbit.demorest.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabbit.demorest.entities.Producto;
import com.rabbit.demorest.repositories.IProductRepo;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepo productRepository;

    // Método para obtener todos los productos
    public List<Producto> getAllProducts() {
        return (List<Producto>) productRepository.findAll();
    }

    // Método para obtener un producto por su ID
    public Optional<Producto> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // Método para crear un nuevo producto
    public Producto createProduct(Producto product) {
        // Establece la fechaCreacion como la fecha y hora actuales
        product.setFechaCreacion(LocalDateTime.now());
        if (product.getEsCocinable()==true) {
            product.setCantidadEnStock(null);
        }
        // Guarda el producto en la base de datos
        return productRepository.save(product);
    }

    // Método para actualizar un producto existente

    // Con esto, cuando se llama al método updateProduct, 
    // se obtiene el producto existente de la base de datos 
    // utilizando su ID. Luego, se actualizan los campos del 
    // producto existente con los valores proporcionados en el 
    // producto actualizado. Finalmente, se establece el campo 
    // fecha_actualizacion con la fecha y hora actuales utilizando 
    // LocalDateTime.now(), y se guarda el producto actualizado 
    // en la base de datos.

    public Producto updateProduct(Long id, Producto updatedProduct) {
        Optional<Producto> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Producto existingProduct = optionalProduct.get();
            existingProduct.setNombre(updatedProduct.getNombre());
            existingProduct.setDescripcion(updatedProduct.getDescripcion());
            existingProduct.setPrecio(updatedProduct.getPrecio());
            existingProduct.setCantidadEnStock(updatedProduct.getCantidadEnStock());
            existingProduct.setCategoria(updatedProduct.getCategoria());
            existingProduct.setFechaActualizacion(LocalDateTime.now());
            existingProduct.setEsCocinable(updatedProduct.getEsCocinable());
            return productRepository.save(existingProduct);
        } else {
            throw new EntityNotFoundException("Product with ID " + id + " not found");
        }
    }

    // Método para eliminar un producto por su ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }








    // CREAR STOCK DISPONIBLE 
}
