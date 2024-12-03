package com.example.tienda.controllers.client;

import com.example.tienda.models.Model;
import com.example.tienda.models.Producto;
import com.example.tienda.models.ProductoCarro;
import com.example.tienda.repositories.ProductRepository;
import com.example.tienda.repositories.WishlistRepository;
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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WishListController implements Initializable {
    private List<Producto> productos;
    private List<Producto> wishlist;
    public GridPane catalogGrid;

    private final ProductRepository productRepository = new ProductRepository();
    private final WishlistRepository wishlistRepository = new WishlistRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void reloadData() {
        productos = productRepository.getAllProducts();
        String username = Model.getInstance().getCurrentUser().getUsername();
        List<String> temp = wishlistRepository.getWishlist(username);
        wishlist = new ArrayList<>();

        for (String s : temp) {
            Optional<Producto> r = productos.stream().filter(p -> p.getIdent().equalsIgnoreCase(s)).findFirst();
            if (r.isPresent()) {
                Producto p = r.get();
                wishlist.add(p);
            }
        }

        int column = 0;
        int row = 0;

        try {
            for (Producto producto : wishlist) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/WishedProductCell.fxml"));
                AnchorPane box = loader.load();
                WishedProductCellController controller = loader.getController();
                controller.delete_btn.setOnMouseClicked(event -> {controller.onBorrar(producto); reloadData();});
                controller.productos = productos;
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
}
