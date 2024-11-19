package com.example.tienda.models;

import javafx.beans.property.*;
import javafx.collections.ObservableList;

import java.util.List;

public class Producto {
    private final StringProperty nombre;
    private final DoubleProperty precio;
    private final IntegerProperty cantidadEnStock;
    private final StringProperty marca;
    private final ListProperty<String> tags;

    public Producto(String nombre, double precio, int cantidadEnStock, String marca, List<String> tags) {
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleDoubleProperty(precio);
        this.cantidadEnStock = new SimpleIntegerProperty(cantidadEnStock);
        this.marca = new SimpleStringProperty(marca);
        this.tags = new SimpleListProperty<String>((ObservableList<String>) tags);
    }

    @Override
    public String toString() {
        return "Producto{" +
                "nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidadEnStock=" + cantidadEnStock +
                ", marca='" + marca + '\'' +
                ", tags=" + tags +
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

    public ListProperty<String> getTags() {
        return tags;
    }
}
