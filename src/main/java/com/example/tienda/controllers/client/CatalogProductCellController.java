package com.example.tienda.controllers.client;

import com.example.tienda.models.Producto;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class CatalogProductCellController implements Initializable {
    public ImageView product_img;
    private Producto producto;
    public Label product_name_lbl;
    public Label product_price_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public Producto getProducto() {
        return producto;
    }

    public void setData(Producto p) {
        producto = p;
        product_name_lbl.setText(p.getNombre().get());
        product_price_lbl.setText("$" + p.getPrecio().get());
        product_img.setImage(p.getImage());
    }
}
