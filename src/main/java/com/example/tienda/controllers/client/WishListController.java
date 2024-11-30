package com.example.tienda.controllers.client;

import com.example.tienda.models.Producto;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WishListController implements Initializable {
    private static final String PRODUCTS_FILE_PATH = "productos.txt";
    private List<Producto> productos;
    public GridPane catalogGrid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void reloadData() {
        productos = new ArrayList<>();
        leerProductosDesdeArchivo();

        int column = 0;
        int row = 0;

        try {
            for (Producto producto : productos) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/WishedProductCell.fxml"));
                AnchorPane box = loader.load();
                WishedProductCellController controller = loader.getController();
                controller.setData(producto);
                if (column == 2) {
                    column = 0;
                    row++;
                }

                catalogGrid.add(box, column++, row);
                GridPane.setMargin(box, new Insets(5));
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
                List<String> tags = Arrays.asList(data[4].split(";"));
                Producto producto = new Producto(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]), data[3], "");
                productos.add(producto);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            System.out.println("Error al leer productos: " + e.getMessage());
        }
    }
}
