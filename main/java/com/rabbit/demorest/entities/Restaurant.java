package com.rabbit.demorest.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "restaurants")
public class Restaurant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre de usuario no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9]{5,50}$", message = "El username debe contener solo letras y números y tener entre 5 y 50 caracteres")
    private String username;

    @NotBlank
    @Size(max = 200)
    private String ubicacion;

    @Pattern(regexp = "\\d{10,15}", message = "El número de teléfono debe tener entre 10 y 15 dígitos")
    @Column(name = "celular")
    private String celular;

    @NotEmpty(message = "El correo no puede estar vacío")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Formato de correo inválido")
    private String correo;

    @NotEmpty(message = "La contraseña no puede estar vacía")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", 
             message = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula,"+
                       "una letra minúscula, un número y un caractér especial")
    private String password;

    private boolean enabled;

    // --------------------- RELACIONES ---------------------

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Users> usuarios;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Reservacion> reservaciones;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Producto> productos;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Orden> ordenes;
    
    // --------------------- GETTERS AND SETTERS ---------------------


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUbicacion() {
        return this.ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCelular() {
        return this.celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return this.correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Users> getUsuarios() {
        return this.usuarios;
    }

    public void setUsuarios(List<Users> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Menu> getMenus() {
        return this.menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Reservacion> getReservaciones() {
        return this.reservaciones;
    }

    public void setReservaciones(List<Reservacion> reservaciones) {
        this.reservaciones = reservaciones;
    }

    public List<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public List<Orden> getOrdenes() {
        return this.ordenes;
    }

    public void setOrdenes(List<Orden> ordenes) {
        this.ordenes = ordenes;
    }


}