package com.example.tienda.controllers.client;

import com.example.tienda.models.ProductoCarro;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.geometry.Insets;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class CarroBorrarCellFactory extends TableCell<ProductoCarro, String> {
    private final CarroController controller;

    public CarroBorrarCellFactory(CarroController controller) {
        this.controller = controller;
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            MaterialDesignIconView deleteIcon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE);
            deleteIcon.getStyleClass().add("delete-icon");

            deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                ProductoCarro producto = controller.carro_tbl.getSelectionModel().getSelectedItem();
                controller.onBorrarClick(producto);
            });

            HBox managebtn = new HBox(deleteIcon);
            managebtn.getStyleClass().add("-fx-alignment:center; -fx-background-color: transparent;");
            HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));

            setGraphic(managebtn);
            setText(null);
        }
    }

    public static Callback<TableColumn<ProductoCarro, String>, TableCell<ProductoCarro, String>> forTableColumn(CarroController controller) {
        return param -> new CarroBorrarCellFactory(controller);
    }
}
