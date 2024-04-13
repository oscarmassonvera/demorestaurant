package com.rabbit.demorest.services;

import java.util.List;

import com.rabbit.demorest.entities.Producto;

public interface IstockService {
    public List<Object[]> findProductCountByCategory();
    public List<Producto> findProductsByDate();
    public List<Producto> findProductsByStock();
    public List<Producto> findProductsByPrice();
}
