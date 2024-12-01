package com.example.tienda;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppInitializer {

    public static void initializeApp() {
        // Create folders
        createFolder(Constants.STORAGE_PATH);
        createFolder(Constants.IMAGE_STORAGE_PATH);

        // Create files
        createFile(Constants.USERS_FILE_PATH);
        createFile(Constants.PRODUCTS_FILE_PATH);

        // Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Carpetas creadas con éxito");
    }

    private static void createFolder(String path) {
        File folder = new File(path);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created) {
                Logger.getLogger(AppInitializer.class.getName()).log(Level.INFO, "Carpeta creada: " + path);
            } else {
                Logger.getLogger(AppInitializer.class.getName()).log(Level.SEVERE, "Error creando: " + path);
            }
        }
    }

    private static void createFile(String path) {
        File file = new File(path);
        try {
            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    Logger.getLogger(AppInitializer.class.getName()).log(Level.INFO, "Archivo creado: " + path);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(AppInitializer.class.getName()).log(Level.SEVERE, "Error creando: " + path + " - " +  e.getMessage());
        }
    }
}

