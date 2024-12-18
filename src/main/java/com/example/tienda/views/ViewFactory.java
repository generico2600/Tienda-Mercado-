package com.example.tienda.views;

import com.example.tienda.controllers.NavigationController;
import com.example.tienda.controllers.client.CatalogController;
import com.example.tienda.controllers.client.WishListController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

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
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return dashboardView;
    }

    public AnchorPane getLoginView() {
        if (loginView == null) {
            try {
                loginView = new FXMLLoader(getClass().getResource("/Fxml/LoginRegis.fxml")).load();
            } catch (Exception e) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
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
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
        }
        return catalogView;
    }

    public AnchorPane getCartView() {
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/Cart.fxml")).load();
        } catch (Exception e) {
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
        }
        return catalogView;
    }

    public AnchorPane getHistoryView() {
        try {
            return new FXMLLoader(getClass().getResource("/Fxml/Historial.fxml")).load();
        } catch (Exception e) {
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
        }
        return catalogView;
    }

    public AnchorPane getWishListView() {
        try {
            FXMLLoader ipl = new FXMLLoader(getClass().getResource("/Fxml/WishList.fxml"));
            AnchorPane p = ipl.load();
            // TODO: Usar listener u Observable List
            ((WishListController)ipl.getController()).reloadData();
            return p;
        } catch (Exception e) {
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
        }
        return catalogView;
    }


    public AnchorPane getAgregarProductoView() {
        if (true) {
            try {
                agregarProductoView = new FXMLLoader(getClass().getResource("/Fxml/AgregarProducto.fxml")).load();
            } catch (Exception e) {
                Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
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
            Logger.getLogger(ViewFactory.class.getName()).log(Level.SEVERE, null, e);
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
