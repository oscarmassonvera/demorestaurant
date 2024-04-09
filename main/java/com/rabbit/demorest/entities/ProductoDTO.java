package com.rabbit.demorest.entities;

import java.time.LocalDateTime;

public class ProductoDTO {
    private Long id;
    private Long cantidadEnStock;
    private String categoria;
    private String descripcion;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaCreacion;
    private String nombre;
    private double precio;

    // Constructor, getters y setters

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCantidadEnStock() {
        return this.cantidadEnStock;
    }

    public void setCantidadEnStock(Long cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return this.precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ProductoDTO(Long id, Long cantidadEnStock, String categoria, String descripcion, LocalDateTime fechaActualizacion, LocalDateTime fechaCreacion, String nombre, double precio) {
        this.id = id;
        this.cantidadEnStock = cantidadEnStock;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.fechaActualizacion = fechaActualizacion;
        this.fechaCreacion = fechaCreacion;
        this.nombre = nombre;
        this.precio = precio;
    }

    public ProductoDTO() {
    }

}
