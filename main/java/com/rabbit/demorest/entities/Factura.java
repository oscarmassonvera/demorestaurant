package com.rabbit.demorest.entities;

import java.util.Date;
import java.util.List;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Número único de la factura.
    @Column(name = "numero_factura")
    private String numeroFactura;

    @Column(name = "fecha_emision")
    private Date fechaEmision;

    // Fecha de vencimiento de la factura.
    @Column(name = "fecha_vencimiento")
    private Date fechaVencimiento;

    // Total de descuentos aplicados a la factura.
    private Double descuentos = 0.0;

    // Subtotal de la factura antes de aplicar impuestos y descuentos.
    private Double subtotal;

    // Total de impuestos aplicados a la factura.
    private Double impuestos = 0.0;

    @Column(name = "monto_total")
    private double montoTotal;

    // Dirección de envío a la que se enviará la factura.
    @Column(name = "direccion_envio")
    private String direccionEnvio;

    // Método de pago utilizado para la factura (por ejemplo, efectivo, tarjeta de crédito, etc.).
    @Column(name = "metodo_pago")
    private String metodoPago;

    @OneToOne
    @JoinColumn(name = "orden_id", referencedColumnName = "id")
    private Orden orden;    

    // Lista de ítems asociados a la factura. Cada ítem puede representar un platillo o producto comprado.
    @Transient
    private List<Producto> items;
    

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return this.numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaEmision() {
        return this.fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return this.fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento2) {
        this.fechaVencimiento = fechaVencimiento2;
    }

    public Double getDescuentos() {
        return this.descuentos;
    }

    public void setDescuentos(Double descuentos) {
        this.descuentos = descuentos;
    }

    public Double getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getImpuestos() {
        return this.impuestos;
    }

    public void setImpuestos(Double impuestos) {
        this.impuestos = impuestos;
    }

    public double getMontoTotal() {
        return this.montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getDireccionEnvio() {
        return this.direccionEnvio;
    }

    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }

    public String getMetodoPago() {
        return this.metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Orden getOrden() {
        return this.orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public List<Producto> getItems() {
        return this.items;
    }

    public void setItems(List<Producto> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((numeroFactura == null) ? 0 : numeroFactura.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Factura other = (Factura) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (numeroFactura == null) {
            if (other.numeroFactura != null)
                return false;
        } else if (!numeroFactura.equals(other.numeroFactura))
            return false;
        return true;
    }
   

}
