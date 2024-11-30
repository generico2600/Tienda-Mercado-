package com.example.tienda.controllers;

import com.example.tienda.models.Usuario;
import com.example.tienda.models.Model;
import com.example.tienda.repositories.UserRepository;
import com.example.tienda.services.UserService;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public Button login_btn;
    public PasswordField login_pass_fld;
    public TextField login_user_fld;
    public TextField regis_email_fld;
    public Button regis_btn;
    public TextField regis_pass_fld;
    public Label login_status_lbl;
    public Label regis_status_lbl;
    public CheckBox cbIsAdmin;

    private UserService userService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userService = new UserService(new UserRepository());
        login_btn.setOnAction(action -> onLogin());
        regis_btn.setOnAction(action -> onRegister());
    }

    private void onLogin() {
        String username = login_user_fld.getText();
        String password = login_pass_fld.getText();

        if (username.isEmpty() || password.isEmpty()) {
            login_status_lbl.setText("Error: Usuario y/o contraseña no puede estar vacío.");
            return;
        }

        Usuario user = userService.login(username, password);
        if (user != null) {
            Model.getInstance().setCurrentUser(user);
            Stage stage = (Stage) login_btn.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showDashboardView();
        } else {
            login_status_lbl.setText("Error: Usuario o contraseña inválido.");
        }
    }

    private void onRegister() {
        String username = regis_email_fld.getText();
        String password = regis_pass_fld.getText();

        if (username.isEmpty() || password.isEmpty()) {
            regis_status_lbl.setText("Error: Usuario y/o contraseña no pueden esta vacíos.");
            return;
        }

        String result = userService.register(username, password, cbIsAdmin.isSelected());
        regis_status_lbl.setText(result);
    }
}

