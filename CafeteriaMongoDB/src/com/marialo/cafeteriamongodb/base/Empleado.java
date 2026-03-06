package com.marialo.cafeteriamongodb.base;

import org.bson.types.ObjectId;

import java.time.LocalDate;

public class Empleado {
    private ObjectId id;
    private String codigoEmpleado;
    private String nombre;
    private String apellidos;
    private String cargo;
    private LocalDate fechaContratacion;
    private ObjectId idTienda;

    public Empleado() {

    }

    public Empleado(String codigoEmpleado, String nombre, String apellidos, String cargo, LocalDate fechaContratacion, ObjectId idTienda) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cargo = cargo;
        this.fechaContratacion = fechaContratacion;
        this.idTienda = idTienda;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public ObjectId getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(ObjectId idTienda) {
        this.idTienda = idTienda;
    }

    @Override
    public String toString() {
        return codigoEmpleado + " | " + nombre + " " + apellidos + " | " + cargo + " | " + fechaContratacion.toString();
    }
}
