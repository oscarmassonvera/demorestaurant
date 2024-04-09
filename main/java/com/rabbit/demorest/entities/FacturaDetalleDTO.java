package com.rabbit.demorest.entities;

import java.util.Date;
import java.util.List;

public class FacturaDetalleDTO {
    private Long facturaId;
    private String numeroFactura;
    private Date fechaEmision;
    private Date fechaVencimiento;
    private Double descuentos;
    private Double subtotal;
    private Double impuestos;
    private Double montoTotal;
    private String direccionEnvio;
    private String metodoPago;
    private Long ordenId;
    private Date fechaOrden;
    private String estadoOrden;
    private Double totalOrden;
    private List<ProductoDTO> productos;

    // Constructor, getters y setters

    public Long getFacturaId() {
        return this.facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
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

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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

    public Double getMontoTotal() {
        return this.montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
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

    public Long getOrdenId() {
        return this.ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public Date getFechaOrden() {
        return this.fechaOrden;
    }

    public void setFechaOrden(Date fechaOrden) {
        this.fechaOrden = fechaOrden;
    }

    public String getEstadoOrden() {
        return this.estadoOrden;
    }

    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public Double getTotalOrden() {
        return this.totalOrden;
    }

    public void setTotalOrden(Double totalOrden) {
        this.totalOrden = totalOrden;
    }

    public List<ProductoDTO> getProductos() {
        return this.productos;
    }

    public void setProductos(List<ProductoDTO> productos) {
        this.productos = productos;
    }

    public FacturaDetalleDTO(Long facturaId, String numeroFactura, Date fechaEmision, Date fechaVencimiento, Double descuentos, Double subtotal, Double impuestos, Double montoTotal, String direccionEnvio, String metodoPago, Long ordenId, Date fechaOrden, String estadoOrden, Double totalOrden, List<ProductoDTO> productos) {
        this.facturaId = facturaId;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.descuentos = descuentos;
        this.subtotal = subtotal;
        this.impuestos = impuestos;
        this.montoTotal = montoTotal;
        this.direccionEnvio = direccionEnvio;
        this.metodoPago = metodoPago;
        this.ordenId = ordenId;
        this.fechaOrden = fechaOrden;
        this.estadoOrden = estadoOrden;
        this.totalOrden = totalOrden;
        this.productos = productos;
    }

    public FacturaDetalleDTO() {
    }

}

