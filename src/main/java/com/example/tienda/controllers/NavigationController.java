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
        Model.getInstance().getViewFactory().getClienSelectedMenuItem().addListener(((observable, oldValue, newValue) -> {
            switch(newValue){
                case "Inicio" -> main_canvas.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case "Catalogo" -> main_canvas.setCenter(Model.getInstance().getViewFactory().getCatalogView());
                default -> main_canvas.setCenter(Model.getInstance().getViewFactory().getDashboardView());
            }
        }));
    }
}
