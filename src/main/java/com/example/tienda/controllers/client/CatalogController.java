package com.example.tienda.controllers.client;

import com.example.tienda.models.Model;
import com.example.tienda.models.Producto;
import com.example.tienda.repositories.CarritoRepository;
import com.example.tienda.repositories.ProductRepository;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CatalogController implements Initializable {
    public Button agg_carro_btn;
    public Spinner<Integer> picker_spn;
    public Label focus_prod_title_lbl;
    public ImageView focus_prod_img;
    public Label focus_prod_desc_lbl;
    public Label focus_prod_stock_lbl;
    public Label carro_status_lbl;
    public GridPane catalogGrid;

    private final ProductRepository productRepository = new ProductRepository();
    private final CarritoRepository carritoRepository = new CarritoRepository();
    private Producto currentProducto;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Usar listener u Observable (ver ViewFactory)
        // reloadData();
        setupListeners();
    }

    private void onCellClicked(Producto p) {
        currentProducto = p;
        setProducto(p);
    }

    public void reloadData() {
        List<Producto> productos = productRepository.getAllProducts();

        int column = 0;
        int row = 0;

        try {
            for (Producto producto : productos) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/CatalogProductCell.fxml"));
                AnchorPane box = loader.load();
                CatalogProductCellController controller = loader.getController();
                controller.setData(producto);
                if (column == 4) {
                    column = 0;
                    row++;
                }

                // Usar icono de "mano" al poner el mouse encima
                box.setOnMouseEntered(event -> box.setCursor(Cursor.HAND));
                box.setOnMouseExited(event -> box.setCursor(Cursor.DEFAULT));

                catalogGrid.add(box, column++, row);
                box.setUserData(producto);
                box.setOnMouseClicked(event -> onCellClicked(producto));
                GridPane.setMargin(box, new Insets(10));
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void setupListeners() {
        agg_carro_btn.setOnAction(action -> onAgregarCarrito());
        picker_spn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
    }

    public void setProducto(Producto p) {
        focus_prod_img.setImage(p.getImage());
        focus_prod_title_lbl.setText(p.getNombre().get());
        focus_prod_stock_lbl.setText(String.valueOf(p.getCantidadEnStock().get()));
        focus_prod_desc_lbl.setText(p.getMarca().get());
        picker_spn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, p.getCantidadEnStock().get()));
        agg_carro_btn.setOnAction(event -> onAgregarCarrito());
    }

    private void onAgregarCarrito() {
        if (currentProducto == null) {
            carro_status_lbl.setText("Error: No hay producto seleccionado.");
            return;
        }

        int quantity = picker_spn.getValue();
        if (quantity <= 0) {
            carro_status_lbl.setText("Error: La cantidad debe ser mayor a 0.");
            return;
        }

        try {
            carritoRepository.agregarProductoAlCarrito(Model.getInstance().getCurrentUser().getUsername(),
                    currentProducto, quantity);
            carro_status_lbl.setText("Producto agregado al carrito con éxito.");
        } catch (Exception e) {
            carro_status_lbl.setText("Error al agregar producto al carrito.");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error al agregar producto al carrito", e);
        }
    }
}
