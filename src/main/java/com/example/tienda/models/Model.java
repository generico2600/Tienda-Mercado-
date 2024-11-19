package com.example.tienda.models;

import com.example.tienda.views.ViewFactory;

public class Model {
    private static Model model;
    private final ViewFactory vFactory;
    private Usuario currentUser;

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario currentUser) {
        this.currentUser = currentUser;
    }

    private Model() {
        this.vFactory = new ViewFactory();
    }

    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return vFactory;
    }
}
