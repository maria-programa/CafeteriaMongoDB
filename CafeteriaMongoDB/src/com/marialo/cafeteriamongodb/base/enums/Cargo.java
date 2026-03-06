package com.marialo.cafeteriamongodb.base.enums;

public enum Cargo {
    ENCARGADA("Encargada/o"),
    BARISTA("Barista"),
    REPOSTERA("Repostera/o");

    private final String cargo;

    Cargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return cargo;
    }
}
