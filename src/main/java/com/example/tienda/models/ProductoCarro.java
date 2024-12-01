package com.example.tienda.models;

public class ProductoCarro {
    private String nombre;
    private int cantidad;
    private double precio;

    public ProductoCarro(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getSubtotal() {
        return precio * cantidad;
    }

    public double getPrecio() {
        return precio;
    }
}
