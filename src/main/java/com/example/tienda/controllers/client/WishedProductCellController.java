package com.example.tienda.controllers.client;

import com.example.tienda.models.Producto;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class WishedProductCellController implements Initializable {
    public Label product_price_lbl;
    public Label product_name_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setData(Producto p) {
        product_name_lbl.setText(p.getNombre().get());
        product_price_lbl.setText("$" + p.getPrecio().get());
    }
}
