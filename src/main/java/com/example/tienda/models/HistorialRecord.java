package com.example.tienda.models;

import java.time.LocalDate;
import java.util.List;

public class HistorialRecord {
    private final LocalDate date;
    private final List<ProductoCarro> products;

    public HistorialRecord(LocalDate date, List<ProductoCarro> products) {
        this.date = date;
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<ProductoCarro> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(date.toString());
        builder.append(",");
        for (ProductoCarro product : products) {
            builder.append(product.getNombre()).append(",")
                    .append(product.getCantidad()).append(",")
                    .append(product.getPrecio()).append(";");
        }
        return builder.toString();
    }
}
