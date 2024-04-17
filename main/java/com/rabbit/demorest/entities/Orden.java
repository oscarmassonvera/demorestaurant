package com.rabbit.demorest.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "ordenes")
public class Orden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fecha;
    private EstadoOrden estado;
    @Pattern(regexp = "^\\d*\\.?\\d+$", message = "El total debe ser un valor numérico no negativo")
    private double total;
    

    @OneToMany(mappedBy = "orden")
    @JsonIgnore // Evita la serialización de la relación bidireccional
    private List<OrdenProducto> ordenProducto = new ArrayList<>();


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }


    public EstadoOrden getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }
    

    public double getTotal() {
        return this.total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public List<OrdenProducto> getOrdenProducto() {
        return this.ordenProducto;
    }

    public void setOrdenProducto(List<OrdenProducto> ordenProducto) {
        this.ordenProducto = ordenProducto;
    }
    

}
