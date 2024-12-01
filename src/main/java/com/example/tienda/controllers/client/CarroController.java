package com.example.tienda.controllers.client;

import com.example.tienda.models.Model;
import com.example.tienda.models.ProductoCarro;
import com.example.tienda.repositories.CarritoRepository;
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
import java.util.ResourceBundle;

public class CarroController implements Initializable {
    public TableView<ProductoCarro> carro_tbl;

    public TableColumn<ProductoCarro, String> nombreCol;
    public TableColumn<ProductoCarro, String> precioCol;
    public TableColumn<ProductoCarro, String> removeCol;
    public TableColumn<ProductoCarro, String> cantidadCol;
    public TableColumn<ProductoCarro, String> subtotalCol;
    public Label subtotal_price_lbl;
    public Label envio_price_lbl;
    public Label total_price_lbl;
    public Button comprar_btn;
    private ProductoCarro producto = null;

    private final CarritoRepository carritoRepository = new CarritoRepository();

    ObservableList<ProductoCarro> ProductList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Usar listener u Observable (ver ViewFactory)
        nombreCol.setResizable(false);
        precioCol.setResizable(false);
        removeCol.setResizable(false);
        cantidadCol.setResizable(false);
        subtotalCol.setResizable(false);
        String user = Model.getInstance().getCurrentUser().getUsername();
        ProductList.addAll(carritoRepository.getAllProductsCarro(user));
        carro_tbl.setItems(ProductList);
        loadData();
    }

    private void loadData() {

        // refreshTable();

        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));
        cantidadCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        //add cell of button edit
        Callback<TableColumn<ProductoCarro, String>, TableCell<ProductoCarro, String>> cellFactory = (TableColumn<ProductoCarro, String> param) -> {
            // make cell containing buttons
            final TableCell<ProductoCarro, String> cell = new TableCell<>() {
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
        removeCol.setCellFactory(cellFactory);

        carro_tbl.setItems(ProductList);
    }
}