package com.example.tienda;

import com.example.tienda.models.Model;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginView();
    }
    
    public static void main(String[] args) {
        AppInitializer.initializeApp();
        launch();
    }
}
