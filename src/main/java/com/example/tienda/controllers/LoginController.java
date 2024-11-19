package com.example.tienda.controllers;

import com.example.pruebas.Usuario;
import com.example.tienda.models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private static final String USERS_FILE_PATH = "usuarios.txt";

    public Button login_btn;
    public PasswordField login_pass_fld;
    public TextField login_user_fld;
    public TextField regis_email_fld;
    public Button regis_btn;
    public TextField regis_pass_fld;
    public Label login_status_lbl;
    public Label regis_status_lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        login_btn.setOnAction(event -> onLogin());
        regis_btn.setOnAction(event -> onRegister());
    }

    private void onLogin() {
        String username = login_user_fld.getText();
        String password = login_pass_fld.getText();

        if (username.isEmpty()) {
            login_status_lbl.setText("Error: El nombre de usuario no puede estar vacio.");
            return;
        }

        if (password.isEmpty()) {
            login_status_lbl.setText("Error: La contraseña no puede estar vacia.");
            return;
        }

        if (validarUsuario(username, password) != null) {
            Stage stage = (Stage) login_btn.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showCatalogView();
        }
    }

    private void onRegister(){
        String username = regis_email_fld.getText();
        String password = regis_pass_fld.getText();

        if (username.isEmpty()) {
            regis_status_lbl.setText("Error: El nombre de usuario no puede estar vacio.");
            return;
        }

        if (password.isEmpty()) {
            regis_status_lbl.setText("Error: La contraseña no puede estar vacia.");
            return;
        }

        registrarUsuario(username, password);

        // No debería fallar, pero...
        if (isUserExists(username)) {
            regis_status_lbl.setText("Registro exitoso!");
        } else {
            regis_status_lbl.setText("Error: Ocurrió un error.");
        }
    }

    // TODO: Controller propio?
    private void registrarUsuario(String username, String password) {
        boolean isAdmin = false; // TODO
        String email = ""; // TODO

        if (isUserExists(username)) {
            regis_status_lbl.setText("Error: El nombre de usuario ya existe. Intenta con otro.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH, true))) {
            writer.write(username + "," + email + "," + password + "," + isAdmin);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    private Usuario validarUsuario(String username, String password) {
        if (!isUserExists(username)) {
            login_status_lbl.setText("Error: El nombre de usuario no existe.");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username) && userDetails[2].equals(password)) {
                    return new Usuario(userDetails[0], userDetails[1], userDetails[2], userDetails[3]);
                }
            }
        } catch (IOException e) {
            // TODO: not here
            login_status_lbl.setText("Error: Ocurrió un error.");
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }

    private boolean isUserExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {

        }
        return false;
    }
}
