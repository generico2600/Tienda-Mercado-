package com.example.tienda.repositories;

import com.example.tienda.Constants;
import com.example.tienda.models.HistorialRecord;
import com.example.tienda.models.Producto;
import com.example.tienda.models.ProductoCarro;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WishlistRepository {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String getPath(String username) {
        return Constants.WISHLIST_FILE_PREFIX + username + Constants.FILE_SUFFIX;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void ensureFile(String username) {
        File file = new File(getPath(username));
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error Creando el archivo de deseados: " , e);
        }
    }

    public void saveWishItem(String username, Producto producto) {
        ensureFile(username);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), true))) {
            writer.write(producto.getIdent());
            writer.newLine();
        } catch (FileNotFoundException ignore) {
            // No history file yet
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error carggando deseados: " , e);
        }
    }

    public List<String> getWishlist(String username) {
        ensureFile(username);
        List<String> wishes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getPath(username)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wishes.add(line);
            }
        } catch (FileNotFoundException ignore) {
            // No history file yet
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error carggando deseados: " , e);
        }

        return wishes;
    }

    public void reemplazarWishlist(String username, List<Producto> productos) {
        ensureFile(username);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), false))) {
            for (Producto producto : productos) {
                writer.write(producto.getIdent());
                writer.newLine();
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error pobblando deseados: " , e);
        }
    }
}
