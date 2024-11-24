package com.example.tienda;

import com.example.tienda.models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;

import static com.example.tienda.Constants.STORAGE_PATH;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Model.getInstance().getViewFactory().showLoginView();
    }
    
    public static void main(String[] args) {
        new File(STORAGE_PATH).mkdirs();
        launch();
    }
}
