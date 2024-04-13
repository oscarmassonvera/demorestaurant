package com.rabbit.demorest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabbit.demorest.entities.Producto;
import com.rabbit.demorest.services.IstockService;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private IstockService stockService;
    
    @GetMapping("/categoria")
    public ResponseEntity<List<Object[]>> getProductsCountByCategory() {
        List<Object[]> products = stockService.findProductCountByCategory();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<Producto>> getProductsByDate() {
        List<Producto> products = stockService.findProductsByDate();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/cantidad")
    public ResponseEntity<List<Producto>> getProductsByStock() {
        List<Producto> products = stockService.findProductsByStock();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/precio")
    public ResponseEntity<List<Producto>> getProductsByPrice() {
        List<Producto> products = stockService.findProductsByPrice();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
