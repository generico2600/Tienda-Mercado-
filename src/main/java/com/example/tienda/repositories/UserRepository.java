package com.example.tienda.repositories;

import com.example.tienda.models.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.tienda.Constants.USERS_FILE_PATH;

public class UserRepository {
    public List<Usuario> loadUsers() {
        List<Usuario> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                users.add(new Usuario(
                        userDetails[0], // Username
                        userDetails[1], // Email
                        userDetails[2], // Password
                        Boolean.parseBoolean(userDetails[3]) // IsAdmin
                ));
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error leyendo el archivo de usuarios: " , e);
        }
        return users;
    }

    public void saveUser(Usuario user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH, true))) {
            writer.write(String.join(",",
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    String.valueOf(user.getIsAdmin())));
            writer.newLine();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error Guardando usuario: " , e);
        }
    }

    public boolean userExists(String username) {
        return loadUsers().stream().anyMatch(user -> user.getUsername().equals(username));
    }
}

