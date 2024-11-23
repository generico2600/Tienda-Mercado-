package com.example.tienda.views;

import com.example.tienda.controllers.NavigationController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
    // Vistas del cliente
    private final StringProperty clientSelectedMenuItem;
    private AnchorPane loginView;
    private AnchorPane catalogView;
    private AnchorPane dashboardView;

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getClienSelectedMenuItem() {
        return clientSelectedMenuItem;
    }

    public AnchorPane getDashboardView() {
        if (dashboardView == null) {
            try {
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/Dashboard.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

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

    public AnchorPane getCatalogView() {
        if (catalogView == null) {
            try {
                catalogView = new FXMLLoader(getClass().getResource("/Fxml/Catalog.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return catalogView;
    }

    public AnchorPane getWipView() {
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/WorkInProgress.fxml")).load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catalogView;
    }

    public void showDashboardView() {
        FXMLLoader ipl = new FXMLLoader(getClass().getResource("/Fxml/MainWindow.fxml"));
        NavigationController cController = new NavigationController();
        ipl.setController(cController);
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
        FXMLLoader ipl = new FXMLLoader(getClass().getResource("/Fxml/MainWindow.fxml"));
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
