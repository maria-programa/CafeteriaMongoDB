package com.marialo.cafeteriamongodb.base;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Producto {
    private ObjectId id;
    private String codigo;
    private String categoria;
    private String nombre;
    private double precio;
    private int stock;
    private ObjectId idTienda;

    public Producto() {

    }

    public Producto(String categoria, String codigo, String nombre, double precio, int stock, ObjectId idTienda) {
        this.categoria = categoria;
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.idTienda = idTienda;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ObjectId getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(ObjectId idTienda) {
        this.idTienda = idTienda;
    }

    @Override
    public String toString() {
        return codigo + " | " + nombre + " | " + categoria + " | " + stock + " | " + precio;
    }
}
