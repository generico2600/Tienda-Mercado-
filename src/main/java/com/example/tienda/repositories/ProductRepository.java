package com.example.tienda.repositories;

import com.example.tienda.models.Producto;
import com.example.tienda.Constants;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductRepository {

    public List<Producto> getAllProducts() {
        List<Producto> productos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(Constants.PRODUCTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Producto producto = new Producto(
                        data[0],                    // Name
                        Double.parseDouble(data[1]), // Price
                        Integer.parseInt(data[2]),   // Stock
                        data[3],                    // Brand
                        data[4]                     // Image path
                );
                productos.add(producto);
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error leyendo el archivo de productos: " , e);
        }

        return productos;
    }

    public void saveProduct(Producto producto, String imgPath) throws IOException {
        String imagePath = (imgPath == null) ? "NOIMAGE" : imgPath;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Constants.PRODUCTS_FILE_PATH, true))) {
            writer.write(producto.getNombre().getValue() + "," +
                    producto.getPrecio().getValue() + "," +
                    producto.getCantidadEnStock().getValue() + "," +
                    producto.getMarca().getValue() + "," +
                    imagePath);
            writer.newLine();
        }
    }

    public String saveProductImage(File selectedFile) throws IOException {
        long timestamp = System.currentTimeMillis();
        String extension = getFileExtension(selectedFile.getName());
        String imageName = timestamp + extension;

        Path destinationDir = new File(Constants.IMAGE_STORAGE_PATH).toPath();
        Path destinationFile = destinationDir.resolve(imageName);

        Files.createDirectories(destinationDir); // Ensure the folder exists
        Files.copy(selectedFile.toPath(), destinationFile, StandardCopyOption.REPLACE_EXISTING);

        return Constants.IMAGE_STORAGE_PATH + imageName;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex > 0) ? fileName.substring(dotIndex) : "";
    }

    public void replaceProducts(List<Producto> productos) {
        try (BufferedWriter ignored = new BufferedWriter(new FileWriter(Constants.PRODUCTS_FILE_PATH, false))) {
            for (Producto producto : productos) {
                saveProduct(producto, producto.getImagePath());
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error pobblando productos: " , e);
        }
    }

    public boolean productExists(String id) {
        return getAllProducts().stream().anyMatch(producto -> producto.getIdent().equals(id));
    }
}
