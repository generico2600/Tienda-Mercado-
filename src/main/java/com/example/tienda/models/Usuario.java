package com.example.tienda.models;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private String username;
    private String email;
    private String password;
    private boolean isAdmin;
    private List<Producto> historialCompras;
    private List<Producto> listaDeseados;

    public Usuario(String username, String email, String password, boolean isAdmin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.historialCompras = new ArrayList<>();
        this.listaDeseados = new ArrayList<>();
    }

    public void agregarAlHistorial(Producto producto) {
        historialCompras.add(producto);
    }

    public void agregarALaListaDeseados(Producto producto) {
        listaDeseados.add(producto);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", admin='" + isAdmin + '\'' +
                ", historialCompras=" + historialCompras +
                ", listaDeseados=" + listaDeseados +
                '}';
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public List<Producto> getHistorialCompras() {
        return historialCompras;
    }

    public void setHistorialCompras(List<Producto> historialCompras) {
        this.historialCompras = historialCompras;
    }

    public List<Producto> getListaDeseados() {
        return listaDeseados;
    }

    public void setListaDeseados(List<Producto> listaDeseados) {
        this.listaDeseados = listaDeseados;
    }
}