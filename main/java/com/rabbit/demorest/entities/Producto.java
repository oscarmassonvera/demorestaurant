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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "El nombre del producto debe contener solo letras, números o espacios")
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String nombre;

    @PositiveOrZero(message = "El precio debe ser un valor positivo o cero")
    private double precio;

    private String descripcion;
    
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "La categoría del producto debe contener solo letras o espacios")
    @NotBlank(message = "La categoría del producto no puede estar vacía")
    private String categoria;

    @PositiveOrZero(message = "La cantidad en stock debe ser un valor positivo o cero")
    @Column(name = "cantidad_stock")
    private Long cantidadEnStock;

    @NotNull(message = "La fecha de creación no puede ser nula")
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    @Column(name="es_cocinable")
    private boolean esCocinable; // Nuevo atributo para indicar si el producto es cocinable o no

    // --------------------- RELACIONES ---------------------

    @OneToMany(mappedBy = "producto")
    @JsonIgnore
    private List<OrdenProducto> ordenProductos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    
    @ManyToOne
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Users user;

    // --------------------- GETTERS AND SETTERS ---------------------

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
    

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }


    public Users getUser() {
        return this.user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    

}
