package com.example.pruebas;
import java.util.List;

public class Producto {
    private String nombre;
    private double precio;
    private int cantidadEnStock;
    private String marca;
    private List<String> tags;

    public Producto(String nombre, double precio, int cantidadEnStock, String marca, List<String> tags) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadEnStock = cantidadEnStock;
        this.marca = marca;
        this.tags = tags;
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

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void setCantidadEnStock(int cantidadEnStock) {
        this.cantidadEnStock = cantidadEnStock;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}

