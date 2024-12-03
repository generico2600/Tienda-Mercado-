package com.example.tienda.controllers.admin;

import com.example.tienda.models.Producto;
import com.example.tienda.repositories.ProductRepository;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgregarProductoController implements Initializable {
    public Spinner<Integer> stock_spn;
    public TextField nombreProducto_fld;
    public TextField precio_fld;
    public TextField marca_fld;
    public Label error_lbl;
    public Button agregar_btn;
    public Button agregar_img_btn;
    public ImageView product_img;
    private String imgPath = null;

    private final ProductRepository productoRepository = new ProductRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupListeners();
    }

    private void setupListeners() {
        stock_spn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        agregar_btn.setOnAction(action -> onAgregar());
        agregar_img_btn.setOnAction(action -> onAgregarImg());
    }

    private void onAgregarImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(agregar_img_btn.getScene().getWindow());
        if (selectedFile != null) {
            try {
                imgPath = productoRepository.saveProductImage(selectedFile);
                Image image = new Image(new File(imgPath).toURI().toString());
                product_img.setImage(image);
            } catch (Exception e) {
                error_lbl.setText("Error al cargar la imagen");
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "No se pudo cargar la imagen", e);
            }
        }
    }

    private void onAgregar() {
        Producto producto = validarCampos();
        if (producto != null) {
            try {
                productoRepository.saveProduct(producto, imgPath);
                error_lbl.setText("Producto agregado con éxito");
            } catch (Exception e) {
                error_lbl.setText("Error al guardar el producto");
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error al guardar el producto", e);
            }
        }
    }

    private Producto validarCampos() {
        String nombre = nombreProducto_fld.getText();
        String marca = marca_fld.getText();
        double precio;
        int stock;

        if (nombre.isEmpty()) {
            error_lbl.setText("Ingrese el nombre del producto");
            return null;
        }

        if (precio_fld.getText().isEmpty()) {
            error_lbl.setText("Ingrese el precio del producto");
            return null;
        }

        try {
            precio = Double.parseDouble(precio_fld.getText());
        } catch (NumberFormatException e) {
            error_lbl.setText("Ha ingresado un precio inválido");
            return null;
        }

        if (marca.isEmpty()) {
            error_lbl.setText("Ingrese la marca del producto");
            return null;
        }

        if (imgPath == null) {
            error_lbl.setText("Escoja una imagen para el producto");
            return null;
        }

        stock = stock_spn.getValue();
        Producto p = new Producto(nombre, precio, stock, marca, imgPath);

        if (productoRepository.productExists(p.getIdent())) {
            error_lbl.setText("El producto ya existe");
            return null;
        }

        return p;
    }
}
