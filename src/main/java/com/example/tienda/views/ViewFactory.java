package com.example.tienda.views;

import com.example.tienda.controllers.NavigationController;
import com.example.tienda.controllers.client.CatalogController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
    // Vistas del cliente
    private final ObjectProperty<AppViewState> clientSelectedMenuItem;
    private AnchorPane loginView;
    private AnchorPane userView;
    private AnchorPane catalogView;
    private AnchorPane dashboardView;
    private AnchorPane agregarProductoView;
    private CatalogController catalogController;

    public ViewFactory() {
        this.clientSelectedMenuItem = new SimpleObjectProperty<>(AppViewState.VIEW_INICIO);
    }

    public ObjectProperty<AppViewState> getClienSelectedMenuItem() {
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
                FXMLLoader ipl = new FXMLLoader(getClass().getResource("/Fxml/Catalog.fxml"));
                catalogView = ipl.load();
                catalogController = ipl.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO: Usar listener u Observable List
        catalogController.reloadData();
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

    public AnchorPane getAgregarProductoView() {
        if (agregarProductoView == null) {
            try {
                agregarProductoView = new FXMLLoader(getClass().getResource("/Fxml/AgregarProducto.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return agregarProductoView;
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

    public void closeStage(Stage stage) {
        stage.close();
    }
}
