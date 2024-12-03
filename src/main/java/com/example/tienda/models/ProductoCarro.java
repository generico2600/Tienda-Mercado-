package com.example.tienda.models;

public class ProductoCarro {
    private final String nombre;
    private final String marca;
    private final int cantidad;
    private final double precio;

    public ProductoCarro(String nombre, String marca, int cantidad, double precio) {
        this.nombre = nombre;
        this.marca = marca;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMarca() {
        return marca;
    }
    public String getIdent() {
        return marca + "|" + nombre;
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
