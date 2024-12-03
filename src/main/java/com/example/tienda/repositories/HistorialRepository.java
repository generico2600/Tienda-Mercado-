package com.example.tienda.repositories;

import com.example.tienda.Constants;
import com.example.tienda.models.ProductoCarro;
import com.example.tienda.models.HistorialRecord;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistorialRepository {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String getPath(String username) {
        return Constants.HISTORY_FILE_PREFIX + username + Constants.FILE_SUFFIX;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void ensureFile(String username) {
        File file = new File(getPath(username));
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error Creando el archivo de carrito: " , e);
        }
    }

    public void savePurchase(String username, List<ProductoCarro> products) {
        LocalDate currentDate = LocalDate.now();
        HistorialRecord record = new HistorialRecord(currentDate, products);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), true))) {
            writer.write(record.toString());
            writer.newLine();
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error guardando historial: " , e);
        }
    }

    public List<HistorialRecord> loadPurchaseHistory(String username) {
        List<HistorialRecord> history = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getPath(username)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                LocalDate date = LocalDate.parse(parts[0], DATE_FORMAT);
                String[] productData = parts[1].split(";");

                List<ProductoCarro> products = new ArrayList<>();
                for (String productEntry : productData) {
                    if (productEntry.trim().isEmpty()) continue;

                    String[] productParts = productEntry.split(",");
                    String nombre = productParts[0];
                    String marca = productParts[1];
                    int cantidad = Integer.parseInt(productParts[2]);
                    double precio = Double.parseDouble(productParts[3]);

                    products.add(new ProductoCarro(nombre, marca, cantidad, precio));
                }

                history.add(new HistorialRecord(date, products));
            }
        } catch (FileNotFoundException ignore) {
            // No history file yet
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error carggando historial: " , e);
        }

        return history;
    }
}
