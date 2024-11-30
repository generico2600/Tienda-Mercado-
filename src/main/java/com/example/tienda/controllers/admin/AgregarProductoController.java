package com.example.tienda.controllers.admin;

import com.example.tienda.models.Producto;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.tienda.Constants.IMAGE_STORAGE_PATH;
import static com.example.tienda.Constants.PRODUCTS_FILE_PATH;

public class AgregarProductoController implements Initializable {
    public Spinner<Integer> stock_spn;
    public TextField nombreProducto_fld;
    public TextField precio_fld;
    public TextField marca_fld;
    public Label error_lbl;
    public Button agregar_btn;
    public Button agregar_img_btn;
    public ImageView product_img;
    private String img_path = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addListener();
    }

    private void addListener() {
        stock_spn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        agregar_btn.setOnAction(action -> onAgregar());
        agregar_img_btn.setOnAction(action -> onAgregarImg());
    }

    private void onAgregarImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));

        File selectedFile = fileChooser.showOpenDialog(agregar_img_btn.getScene().getWindow());

        if (selectedFile != null) {
            // Usar timestamps para evitar colisiones de nombre
            long timestamp = System.currentTimeMillis() / 1000;  // timestamp en segundos
            String imagen = timestamp + getImgExtension(selectedFile);

            // Define the destination directory and file path
            Path carpeta_destino = new File(IMAGE_STORAGE_PATH).toPath();
            Path archivo_destino = carpeta_destino.resolve(imagen);

            try {
                // Más vale que esa carpeta exista, quizá sea buena idea mover esto a un sitio más apropiado
                Files.createDirectories(carpeta_destino);

                // Hacer una copia y guardas rutas relativas
                Files.copy(selectedFile.toPath(), archivo_destino, StandardCopyOption.REPLACE_EXISTING);
                img_path = IMAGE_STORAGE_PATH + imagen;

                Image image = new Image(archivo_destino.toUri().toString());
                product_img.setImage(image);
            } catch (IOException e) {
                error_lbl.setText("Error al cargar la imagen");
                Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "No cargó la imagen", e);
            }
        }
    }

    // Helper method to get the file extension
    private String getImgExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            return fileName.substring(dotIndex);
        }
        return ""; // Sin extensión ?????
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
        String p = "NOIMAGE";

        if (img_path != null) {
            p = img_path;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE_PATH, true))) {
            writer.write(producto.getNombre().getValue() + "," +
                    producto.getPrecio().getValue() + "," +
                    producto.getCantidadEnStock().getValue() + "," +
                    producto.getMarca().getValue() + "," +
                    p); // TODO: tags
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