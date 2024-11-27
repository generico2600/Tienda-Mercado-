package com.example.tienda.controllers;

import com.example.tienda.models.Model;
import com.example.tienda.views.AppViewState;
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
        topbar_usuario_icon.setOnMouseClicked(_ -> onUser());
        topbar_historial_btn.setOnAction(_ -> onHistorial());
        topbar_deseados_btn.setOnAction(_ -> onDeseados());
        topbar_cart_icon.setOnMouseClicked(_ -> onCarrito());
    }

    private void onInicio() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set(AppViewState.VIEW_INICIO);
    }

    private void onUser() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set(AppViewState.VIEW_USUARIO);
    }

    private void onHistorial() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set(AppViewState.VIEW_HISTORIAL);
    }

    private void onDeseados() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set(AppViewState.VIEW_DESEADOS);
    }

    private void onCarrito() {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().set(AppViewState.VIEW_CARRITO);
    }
}
