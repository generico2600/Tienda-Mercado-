package com.example.tienda.controllers;

import com.example.tienda.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class NavigationController implements Initializable {
    public BorderPane main_canvas;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().addListener(((p, q, newValue) -> {
            switch(newValue){
                case VIEW_INICIO -> main_canvas.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case VIEW_CATAlOGO -> main_canvas.setCenter(Model.getInstance().getViewFactory().getCatalogView());
                case VIEW_AGREGAR_PRODUCTO -> main_canvas.setCenter(Model.getInstance().getViewFactory().getAgregarProductoView());
                case VIEW_CARRITO -> main_canvas.setCenter(Model.getInstance().getViewFactory().getCartView());
                case VIEW_DESEADOS -> main_canvas.setCenter(Model.getInstance().getViewFactory().getWishListView());
                case VIEW_HISTORIAL ->  main_canvas.setCenter(Model.getInstance().getViewFactory().getHistoryView());
                default -> main_canvas.setCenter(Model.getInstance().getViewFactory().getWipView());
            }
        }));
    }
}
