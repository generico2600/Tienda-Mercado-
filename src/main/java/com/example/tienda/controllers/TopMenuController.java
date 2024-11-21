package com.example.tienda.controllers;

import com.example.tienda.models.Model;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TopMenuController implements Initializable {
    public MaterialDesignIconView topbar_cart_icon;
    public MaterialDesignIconView topbar_usuario_icon;
    public TextField topbar_buscar_fld;
    public Button topbar_inicio_btn;
    public Button topbar_historial_btn;
    public Button topbar_deseados_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        topbar_inicio_btn.setOnAction(_ -> onInicio());
    }

    private void onInicio() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set("Inicio");
    }
}
