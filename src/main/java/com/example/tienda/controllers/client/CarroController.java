package com.example.tienda.controllers.client;

import com.example.tienda.models.Model;
import com.example.tienda.models.ProductoCarro;
import com.example.tienda.repositories.CarritoRepository;
import com.example.tienda.repositories.HistorialRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private final CarritoRepository carritoRepository = new CarritoRepository();
    private final HistorialRepository historialRepository = new HistorialRepository();
    public Label carro_status_lbl;

    ObservableList<ProductoCarro> ProductList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        precioCol.setCellValueFactory(new PropertyValueFactory<>("precio"));
        cantidadCol.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        subtotalCol.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        removeCol.setCellFactory(CarroBorrarCellFactory.forTableColumn(this));
        carro_tbl.setItems(ProductList);
        loadData();
        comprar_btn.setOnAction(event -> onComprarClick());
    }

    public void onBorrarClick(ProductoCarro p) {
        String username = Model.getInstance().getCurrentUser().getUsername();
        ProductList.remove(p);
        carritoRepository.reemplazarCarro(username, ProductList);
        actualizarPrecios();
    }

    private boolean validarCompra() {
        if (ProductList.isEmpty()) {
            carro_status_lbl.setText("Error: El carrito está vacío.");
            return false;
        }

//        for (ProductoCarro producto : ProductList) {
//            if (producto.getCantidad() > producto.revisarStock()) {
//                carro_status_lbl.setText("No hay suficiente stiock para el producto: " + producto.getNombre());
//                return false;
//            }
//        }

        return true;
    }

    private void onComprarClick() {
        if (!validarCompra()) {
            return;
        }

        String username = Model.getInstance().getCurrentUser().getUsername();

        // Guardar historial
        historialRepository.savePurchase(username, ProductList);

        // Actualizar stock
        // ProductList.forEach(product -> carritoRepository.deductStock(product.getNombre(), product.getCantidad()));

        // Limpiar carro
        // carritoRepository.limpiarCarro(username);
        // resetCampos();
    }

    private void resetCampos() {
        ProductList.clear();
        carro_tbl.refresh();
        subtotal_price_lbl.setText("$0.00");
        envio_price_lbl.setText("$0.00");
        total_price_lbl.setText("$0.00");
    }

    private void actualizarPrecios() {
        double subtotal = 0.0;
        double envio = 0.0;
        for (ProductoCarro pCarro : ProductList) {
            subtotal += pCarro.getSubtotal();
            envio++;
        }

        subtotal_price_lbl.setText(String.format("$%.2f", subtotal));
        envio_price_lbl.setText(String.format("$%.2f", envio * 10));
        total_price_lbl.setText(String.format("$%.2f", subtotal + envio));
    }

    private void loadData() {
        String user = Model.getInstance().getCurrentUser().getUsername();
        ProductList.clear();
        ProductList.addAll(carritoRepository.getAllProductsCarro(user));
        actualizarPrecios();
    }
}