// package com.rabbit.demorest.controllers;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.rabbit.demorest.entities.Producto;
// import com.rabbit.demorest.services.IProductService;

// @RestController
// @RequestMapping("/api/products")
// public class ProductController {
//     @Autowired
//     private IProductService productService;

//     @GetMapping
//     public ResponseEntity<List<Producto>> getAllProducts() {
//         List<Producto> products = productService.getAllProducts();
//         return ResponseEntity.ok(products);
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Producto> getProductById(@PathVariable Long id) {
//         Optional<Producto> product = productService.getProductById(id);
//         if (product.isPresent()) {
//             return ResponseEntity.ok(product.get());
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @PostMapping
//     public ResponseEntity<Producto> createProduct(@RequestBody Producto product) {
//         Producto createdProduct = productService.createProduct(product);
//         return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Producto> updateProduct(@PathVariable Long id, @RequestBody Producto product) {
//         Producto updatedProduct = productService.updateProduct(id, product);
//         if (updatedProduct != null) {
//             return ResponseEntity.ok(updatedProduct);
//         } else {
//             return ResponseEntity.notFound().build();
//         }
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
//         productService.deleteProduct(id);
//         return ResponseEntity.noContent().build();
//     }
// }

package com.rabbit.demorest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.demorest.entities.Producto;
import com.rabbit.demorest.services.IProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Producto> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener todos los productos: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Optional<Producto> product = productService.getProductById(id);
            return product.map(p -> ResponseEntity.ok((Object)p))
                          .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener el producto: " + e.getMessage());
        }
    }
    


    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Producto product) {
        try {
            Producto createdProduct = productService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el producto: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProduct(@PathVariable Long id, @RequestBody Producto product) {
        Producto updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
