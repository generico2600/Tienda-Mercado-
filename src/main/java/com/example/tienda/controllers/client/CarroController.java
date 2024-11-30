package com.example.tienda.controllers.client;

import com.example.tienda.models.Producto;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CarroController implements Initializable {
    public TableView<Producto> carro_tbl;

    public TableColumn<Producto, String> nombreCol;
    public TableColumn<Producto, String> precioCol;
    public TableColumn<Producto, String> removeCol;
    public TableColumn<Producto, String> cantidadCol;
    public TableColumn<Producto, String> subtotalCol;
    public Label subtotal_price_lbl;
    public Label envio_price_lbl;
    public Label total_price_lbl;
    public Button comprar_btn;
    private Producto producto = null;

    ObservableList<Producto> ProductList = FXCollections.observableArrayList();
    ArrayList<Producto> productos = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Usar listener u Observable (ver ViewFactory)
        nombreCol.setResizable(false);
        precioCol.setResizable(false);
        removeCol.setResizable(false);
        cantidadCol.setResizable(false);
        subtotalCol.setResizable(false);
        ProductList.addAll(productos);
        for (int i = 0; i < 20; i++) {
            ProductList.add(new Producto("ababa" + i, 10 * i, 1, "Marca" + i, null));
        }
        carro_tbl.setItems(ProductList);
        loadDate();
    }

    private void loadDate() {

        // refreshTable();

        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));

        //add cell of button edit
        Callback<TableColumn<Producto, String>, TableCell<Producto, String>> cellFoctory = (TableColumn<Producto, String> param) -> {
            // make cell containing buttons
            final TableCell<Producto, String> cell = new TableCell<Producto, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        MaterialDesignIconView deleteIcon = new MaterialDesignIconView(MaterialDesignIcon.CLOSE_CIRCLE_OUTLINE );

                        deleteIcon.getStyleClass().add("delete-icon");
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            producto = carro_tbl.getSelectionModel().getSelectedItem();
                            // refreshTable();
                            System.out.println(producto);
                        });
                        HBox managebtn = new HBox(deleteIcon);
                        managebtn.getStyleClass().add("-fx-alignment:center;" +
                                "-fx-background-color: transparent;\n" +
                                "-fx-background: transparent;");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        removeCol.setCellFactory(cellFoctory);

        carro_tbl.setItems(ProductList);
    }
}