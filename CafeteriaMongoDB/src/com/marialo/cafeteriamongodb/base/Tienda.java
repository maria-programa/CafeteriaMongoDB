package com.marialo.cafeteriamongodb.base;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Tienda {
    private ObjectId id;
    private String nombre;
    private String direccion;
    private LocalDate fechaInauguracion;
    private String telefono;

    public Tienda() {

    }

    public Tienda(String nombre, String direccion, LocalDate fechaInauguracion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaInauguracion = fechaInauguracion;
        this.telefono = telefono;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaInauguracion() {
        return fechaInauguracion;
    }

    public void setFechaInauguracion(LocalDate fechaInauguracion) {
        this.fechaInauguracion = fechaInauguracion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return nombre + " | " + telefono + " | " + direccion + " | " + fechaInauguracion;
    }
}
