package com.example.tienda.views;

import com.example.tienda.controllers.CatalogProductCellController;
import com.example.tienda.models.Producto;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class CatalogProductCellFactory extends ListCell<Producto> {
    @Override
    protected void updateItem(Producto producto, boolean empty) {
        super.updateItem(producto, empty);
        if (empty){
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CatalogProductCell.fxml"));
            CatalogProductCellController controller = new CatalogProductCellController(producto);
            loader.setController(controller);
            setText(null);
            try {
                setGraphic(loader.load());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
