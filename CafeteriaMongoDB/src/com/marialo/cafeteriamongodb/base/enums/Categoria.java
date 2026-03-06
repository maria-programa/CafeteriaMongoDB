package com.marialo.cafeteriamongodb.base.enums;

public enum Categoria {
    BEBIDA("Bebida"),
    COMIDA("Comida");

    private final String categoria;

    Categoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return categoria;
    }
}
