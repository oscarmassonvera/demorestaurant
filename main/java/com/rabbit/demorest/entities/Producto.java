package com.rabbit.demorest.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precio;
    private String descripcion;
    private String categoria;
    @Column(name = "cantidad_stock")
    private Long cantidadEnStock;
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    @Column(name="es_cocinable")
    private boolean esCocinable; // Nuevo atributo para indicar si el producto es cocinable o no

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<OrdenProducto> ordenProductos = new ArrayList<>();
    
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getCantidadEnStock() {
        return this.cantidadEnStock;
    }

    public void setCantidadEnStock(Long cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }


    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    


    public LocalDateTime getFechaActualizacion() {
        return this.fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public boolean isEsCocinable() {
        return this.esCocinable;
    }

    public boolean getEsCocinable() {
        return this.esCocinable;
    }

    public void setEsCocinable(boolean esCocinable) {
        this.esCocinable = esCocinable;
    }

    public List<OrdenProducto> getOrdenProductos() {
        return this.ordenProductos;
    }

    public void setOrdenProductos(List<OrdenProducto> ordenProductos) {
        this.ordenProductos = ordenProductos;
    }
    

}
