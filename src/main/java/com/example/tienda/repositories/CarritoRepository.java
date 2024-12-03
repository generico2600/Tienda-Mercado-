package com.example.tienda.repositories;

import com.example.tienda.models.Producto;
import com.example.tienda.Constants;
import com.example.tienda.models.ProductoCarro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarritoRepository {

    private String getPath(String username) {
        return Constants.CART_FILE_PREFIX + username + Constants.FILE_SUFFIX;
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

    public List<ProductoCarro> getAllProductsCarro(String username) {
        List<ProductoCarro> productos = new ArrayList<>();

        ensureFile(username);

        try (BufferedReader reader = new BufferedReader(new FileReader(getPath(username)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                ProductoCarro pCarro = new ProductoCarro(
                        data[0],                    // Nombre
                        data[1],                    // marca
                        Integer.parseInt(data[2]),  // Cantidad
                        Double.parseDouble(data[3]) // Coste Base
                );
                productos.add(pCarro);
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error leyendo el archivo de carrito: " , e);
        }

        return productos;
    }

    public void agregarProductoAlCarrito(String username, Producto producto, int cantidad) throws IOException {
        ensureFile(username);

        // Añadir al final de la lista
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), true))) {
            writer.write(producto.getNombre().get() + "," +
                    producto.getMarca().get()+ "," +
                    cantidad + "," +
                    producto.getPrecio().get());
            writer.newLine();
        }
    }

    public void reemplazarCarro(String username, List<ProductoCarro> productos) {
        ensureFile(username);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), false))) {
            for (ProductoCarro producto : productos) {
                    writer.write(producto.getNombre() + "," +
                            producto.getMarca() + "," +
                            producto.getCantidad() + "," +
                            producto.getPrecio());
                    writer.newLine();
            }
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error pobblando el carrito: " , e);
        }
    }

    public void limpiarCarro(String username) {
        ensureFile(username);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getPath(username), false))) {
            writer.write("");
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Error limpiando el carrito: " , e);
        }
    }
}
