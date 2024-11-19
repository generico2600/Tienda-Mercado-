package com.example.tienda.controllers;

import com.example.tienda.models.Producto;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CatalogProductCellController implements Initializable {

    private final Producto producto;
    public Label product_name_lbl;
    public Label product_price_lbl;

    public CatalogProductCellController(Producto producto) {
        this.producto = producto;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
