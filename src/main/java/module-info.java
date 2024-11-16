module com.example.tienda {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tienda to javafx.fxml;
    exports com.example.tienda;
}