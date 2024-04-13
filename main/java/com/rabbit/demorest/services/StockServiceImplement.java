package com.rabbit.demorest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rabbit.demorest.entities.Producto;
import com.rabbit.demorest.repositories.IProductRepo;

@Service
public class StockServiceImplement implements IstockService {
    @Autowired
    private IProductRepo productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Object[]> findProductCountByCategory() {
        return productRepository.findProductDataGroupedByCategory();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findProductsByDate() {
        return productRepository.findProductsOrderByDate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findProductsByStock() {
        return productRepository.findProductsOrderByPrice();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findProductsByPrice() {
        return productRepository.findProductsOrderByDate();
    }
}
