package com.example.tienda.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    // Vistas del cliente
    private AnchorPane loginView;

    public ViewFactory() {}

    public AnchorPane getLoginView() {
        if (loginView == null) {
            try {
                loginView = new FXMLLoader(getClass().getResource("/Fxml/LoginRegis.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return loginView;
    }

    public void showLoginView() {
        FXMLLoader ipl = new FXMLLoader(getClass().getResource("/Fxml/LoginRegis.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(ipl.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Papelería Clips&Shapes");
        stage.show();
    }

    public void showCatalogView() {
        FXMLLoader ipl = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml"));
        Scene scene = null;

        try {
            scene = new Scene(ipl.load());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Papelería Clips&Shapes");
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }
}
