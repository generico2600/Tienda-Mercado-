package com.example.tienda.models;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Image getImage() {
        Image image;

        // Pidamos perdón, no permiso
        try {
            File file = new File(imagePath);
            image = new Image(file.toURI().toString());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No se pudo cargar la imagen: ", e);
            image = new Image(Objects.requireNonNull(getClass().getResource("/Img/no-image.png")).toExternalForm());
        }
        return image;
    }
}
