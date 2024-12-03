package com.example.tienda.controllers.client;

import com.example.tienda.models.HistorialRecord;
import com.example.tienda.models.Model;
import com.example.tienda.models.ProductoCarro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class HistorialCellController implements Initializable {
    public TableView<ProductoCarro> carro_tbl;
    public TableColumn<ProductoCarro, String> nombreCol;
    public TableColumn<ProductoCarro, String> precioCol;
    public TableColumn<ProductoCarro, String> cantidadCol;
    public TableColumn<ProductoCarro, String> subtotalCol;
    public Label top_lbl;

    ObservableList<ProductoCarro> ProductList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));
        cantidadCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        carro_tbl.setItems(ProductList);
    }

    public void setData(HistorialRecord record) {
        String user = Model.getInstance().getCurrentUser().getUsername();
        ProductList.clear();
        ProductList.addAll(record.getProducts());
        top_lbl.setText("Compra hecha el " + record.getDate());
    }
}