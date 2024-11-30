package com.example.tienda.models;

import javafx.beans.property.*;

public class Producto {
    private final StringProperty nombre;
    private final DoubleProperty precio;
    private final IntegerProperty cantidadEnStock;
    private final StringProperty marca;
    private final String imagePath;

    public Producto(String nombre, double precio, int cantidadEnStock, String marca, String imagePath) {
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleDoubleProperty(precio);
        this.cantidadEnStock = new SimpleIntegerProperty(cantidadEnStock);
        this.marca = new SimpleStringProperty(marca);
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidadEnStock=" + cantidadEnStock +
                ", marca='" + marca + '\'' +
                ", img=" + imagePath +
                '}';
    }

    public StringProperty getNombre() {
        return nombre;
    }

    public DoubleProperty getPrecio() {
        return precio;
    }

    public IntegerProperty getCantidadEnStock() {
        return cantidadEnStock;
    }

    public StringProperty getMarca() {
        return marca;
    }

    public String getImagePath() {
        return imagePath;
    }
}
