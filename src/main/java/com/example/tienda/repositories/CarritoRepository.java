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

    private String getPath(String username) {
        return Constants.CART_FILE_PREFIX + username + Constants.FILE_SUFFIX;
    }

    public List<ProductoCarro> getAllProductsCarro(String username) {
        List<ProductoCarro> productos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getPath(username)))) {
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
        // Asegurarse de que el archivo existe
        Path filePath = new File(getPath(username)).toPath();
        Files.createDirectories(filePath.getParent());

        // Añadir al final de la lista
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), true))) {
            writer.write(producto.getNombre().get() + "," +
                    cantidad + "," +
                    producto.getPrecio().get());
            writer.newLine();
        }
    }

    public void reemplazarCarro(String username, List<ProductoCarro> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), false))) {
            for (ProductoCarro producto : productos) {
                    writer.write(producto.getNombre() + "," +
                            producto.getCantidad() + "," +
                            producto.getPrecio());
                    writer.newLine();
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error pobblando el carrito: " , e);
        }
    }

    public void limpiarCarro(String username) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), false))) {
            writer.write("");
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error limpiando el carrito: " , e);
        }
    }
}
