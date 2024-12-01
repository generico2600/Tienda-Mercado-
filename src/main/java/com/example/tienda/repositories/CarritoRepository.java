package com.example.tienda.repositories;

import com.example.tienda.models.Producto;
import com.example.tienda.Constants;
import com.example.tienda.models.ProductoCarro;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarritoRepository {

    public List<ProductoCarro> getAllProductsCarro(String username) {
        String carritoPath = Constants.CART_FILE_PREFIX + username + Constants.CART_FILE_SUFFIX;
        List<ProductoCarro> productos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(carritoPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                ProductoCarro pCarro = new ProductoCarro(
                        data[0],                    // Nombre
                        Integer.parseInt(data[1]),   // Cantidad
                        Double.parseDouble(data[2]) // Coste Base
                );
                productos.add(pCarro);
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error leyendo el archivo de carrito: " , e);
        }

        return productos;
    }

    public void agregarProductoAlCarrito(String username, Producto producto, int cantidad) throws IOException {
        String carritoPath = Constants.CART_FILE_PREFIX + username + Constants.CART_FILE_SUFFIX;

        // Asegurarse de que el archivo existe
        Path filePath = new File(carritoPath).toPath();
        Files.createDirectories(filePath.getParent());

        // Añadir al final de la lista
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(carritoPath, true))) {
            writer.write(producto.getNombre().get() + "," +
                    cantidad + "," +
                    producto.getPrecio().get());
            writer.newLine();
        }
    }
}
