package com.example.tienda.controllers.admin;

import com.example.tienda.models.Producto;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.tienda.Constants.PRODUCTS_FILE_PATH;

public class AgregarProductoController implements Initializable {
    public Spinner<Integer> stock_spn;
    public TextField nombreProducto_fld;
    public TextField precio_fld;
    public TextField marca_fld;
    public Label error_lbl;
    public Button agregar_btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        stock_spn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        agregar_btn.setOnAction(_ -> onAgregar());
    }

    private Producto validarCampos() {
        String nombre, marca;
        int stock;
        double precio;

        nombre = nombreProducto_fld.getText();
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
        }
        catch (NumberFormatException e) {
            error_lbl.setText("Ha ingresado un precio inválido");
            return null;
        }

        marca = marca_fld.getText();
        if (marca.isEmpty()) {
            error_lbl.setText("Ingrese la marca del producto");
            return null;
        }

        stock = stock_spn.getValue();
        return new Producto(nombre, precio, stock, marca, null);
    }

    private void guardarProducto(Producto producto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE_PATH, true))) {
            writer.write(producto.getNombre().getValue() + "," +
                    producto.getPrecio().getValue() + "," +
                    producto.getCantidadEnStock().getValue() + "," +
                    producto.getMarca().getValue() + "," +
                    String.join(";", "Generic")); // TODO: tags
            writer.newLine();
            error_lbl.setText("Producto agregado con éxito");
        } catch (IOException e) {
            error_lbl.setText("Error al guardar el producto: " + e.getMessage());
        }
    }

    private void onAgregar() {
        Producto prod = validarCampos();
        if (prod != null) {
            guardarProducto(prod);
        }
    }

}