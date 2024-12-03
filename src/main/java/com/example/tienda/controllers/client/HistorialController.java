package com.example.tienda.controllers.client;

import com.example.tienda.models.HistorialRecord;
import com.example.tienda.models.Model;
import com.example.tienda.repositories.HistorialRepository;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistorialController implements Initializable {
    public GridPane historyGrid;

    private final HistorialRepository historialRepository = new HistorialRepository();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reloadData();
    }

    public void reloadData() {
        String username = Model.getInstance().getCurrentUser().getUsername();
        List<HistorialRecord> historial = historialRepository.loadPurchaseHistory(username);

        int row = 0;

        try {
            for (HistorialRecord record : historial) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/HistorialCell.fxml"));
                AnchorPane box = loader.load();
                HistorialCellController controller = loader.getController();
                controller.setData(record);
                historyGrid.add(box, 0, row++);
                GridPane.setMargin(box, new Insets(10));
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
}