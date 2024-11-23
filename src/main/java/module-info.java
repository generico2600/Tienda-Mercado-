module com.example.tienda {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.materialdesignicons;
    requires MaterialFX;

    opens com.example.tienda to javafx.fxml;
    exports com.example.tienda;
    exports com.example.tienda.controllers;
    exports com.example.tienda.models;
    exports com.example.tienda.views;
    exports com.example.tienda.controllers.client;
}