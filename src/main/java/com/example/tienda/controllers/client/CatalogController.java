package com.example.tienda.controllers.client;

import com.example.tienda.models.Producto;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.tienda.Constants.PRODUCTS_FILE_PATH;

public class CatalogController implements Initializable {
    public Button agg_carro_btn;
    public Spinner picker_spn;
    public Label focus_prod_title_lbl;
    public ImageView focus_prod_img;
    public Label focus_prod_desc_lbl;
    public Label focus_prod_stock_lbl;
    public Label carro_status_lbl;
    private List<Producto> productos;
    public GridPane catalogGrid;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: Usar listener u Observable (ver ViewFactory)
        // reloadData();
    }

    private void onCellClicked(AnchorPane box) {
        Producto p = (Producto) box.getUserData();
        // Pidamos perdón, no permiso
        try {
            File file = new File(p.getImagePath());
            Image image = new Image(file.toURI().toString());

            // Set the image to the ImageView
            focus_prod_img.setImage(image);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No se pudo cargar la imagen: ", e);
        }
        focus_prod_title_lbl.setText(p.getNombre().get());
        focus_prod_stock_lbl.setText(String.valueOf(p.getCantidadEnStock().get()));
        focus_prod_desc_lbl.setText(p.getMarca().get());
    }

    public void reloadData() {
        productos = new ArrayList<>();
        leerProductosDesdeArchivo();

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

                // Set the cursor to 'HAND' on hover
                box.setOnMouseEntered(event -> box.setCursor(Cursor.HAND));
                box.setOnMouseExited(event -> box.setCursor(Cursor.DEFAULT));

                catalogGrid.add(box, column++, row);
                box.setUserData(producto);
                box.setOnMouseClicked(event -> {onCellClicked(box);});
                GridPane.setMargin(box, new Insets(10));
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void leerProductosDesdeArchivo() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Producto producto = new Producto(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]), data[3], data[4]);
                productos.add(producto);
            }
        } catch (FileNotFoundException ignore) {

        } catch (IOException e) {
            System.out.println("Error al leer productos: " + e.getMessage());
        }
    }
}
