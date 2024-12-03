package com.example.tienda.controllers.client;

import com.example.tienda.models.Model;
import com.example.tienda.models.Producto;
import com.example.tienda.repositories.ProductRepository;
import com.example.tienda.repositories.WishlistRepository;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class WishedProductCellController implements Initializable {
    public Label product_price_lbl;
    public Label product_name_lbl;
    public Spinner<Integer> amount_spn;
    public MaterialDesignIconView delete_btn;
    public List<Producto> productos;

    private final WishlistRepository wishlistRepository = new WishlistRepository();
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setData(Producto p) {
        product_name_lbl.setText(p.getNombre().get());
        product_price_lbl.setText("$" + p.getPrecio().get());
        amount_spn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, p.getCantidadEnStock().get()));
    }

    public void onBorrar(Producto p) {
        String username = Model.getInstance().getCurrentUser().getUsername();
        productos.remove(p);
        wishlistRepository.reemplazarWishlist(username, productos);
    }
}
