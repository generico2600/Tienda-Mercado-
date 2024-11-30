package com.example.tienda.services;

import com.example.tienda.models.Usuario;
import com.example.tienda.repositories.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Usuario login(String username, String password) {
        List<Usuario> users = userRepository.loadUsers();
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public String register(String username, String password, boolean isAdmin) {
        if (userRepository.userExists(username)) {
            return "Error: El nombre de usuario ya existe.";
        }

        Usuario newUser = new Usuario(username, "", password, isAdmin);
        userRepository.saveUser(newUser);
        return "Registro existoso!";
    }
}
