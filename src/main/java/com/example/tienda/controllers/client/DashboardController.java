package com.example.tienda.controllers.client;

import com.example.tienda.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button catalog_btn;
    public Label welcome_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        catalog_btn.setOnAction(_ -> onCatalog());
        welcome_lbl.setText("¡Bienvenido/a " + Model.getInstance().getCurrentUser().getUsername() + "!");
    }

    private void onCatalog() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set("Catalogo");
    }
}
