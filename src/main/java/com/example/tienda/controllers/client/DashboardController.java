package com.example.tienda.controllers.client;

import com.example.tienda.models.Model;
import com.example.tienda.views.AppViewState;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Button catalog_btn;
    public Label welcome_lbl;
    public Button addItems_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        catalog_btn.setOnAction(action -> onCatalog());
        welcome_lbl.setText("¡Bienvenido/a " + Model.getInstance().getCurrentUser().getUsername() + "!");

        if (Model.getInstance().getCurrentUser().getIsAdmin()) {
            addItems_btn.setVisible(true);
            addItems_btn.setManaged(true);
            addItems_btn.setOnAction(action -> onAgregarProducto());
        }
        else {
            addItems_btn.setVisible(false);
            addItems_btn.setManaged(false);
        }
    }

    private void onCatalog() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set(AppViewState.VIEW_CATAlOGO);
    }

    private void onAgregarProducto() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set(AppViewState.VIEW_AGREGAR_PRODUCTO);
    }
}
