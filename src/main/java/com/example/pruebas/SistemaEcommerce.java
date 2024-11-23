package com.example.pruebas;
import java.io.*;
import java.util.*;

public class SistemaEcommerce {

    private static final String USERS_FILE_PATH = "usuarios.txt";
    private static final String PRODUCTS_FILE_PATH = "productos.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== E-commerce de Papelería ===");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("Elige una opción: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    registrarUsuario(scanner);
                    break;
                case 2:
                    iniciarSesion(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (choice != 3);

        scanner.close();
    }

    private static void registrarUsuario(Scanner scanner) {
        System.out.println("\n=== Registro de Usuario ===");
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();

        System.out.print("Correo electrónico: ");
        String email = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        System.out.print("Rol (admin/cliente): ");
        String rol = scanner.nextLine();

        if (isUserExists(username)) {
            System.out.println("El nombre de usuario ya existe. Intenta con otro.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE_PATH, true))) {
            writer.write(username + "," + email + "," + password + "," + rol.equalsIgnoreCase("admin"));
            writer.newLine();
            System.out.println("Usuario registrado exitosamente.");
        } catch (IOException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    private static void iniciarSesion(Scanner scanner) {
        System.out.println("\n=== Inicio de Sesión ===");
        System.out.print("Nombre de usuario: ");
        String username = scanner.nextLine();

        System.out.print("Contraseña: ");
        String password = scanner.nextLine();

        Usuario usuario = validarUsuario(username, password);
        if (usuario != null) {
            System.out.println("Inicio de sesión exitoso. ¡Bienvenido " + usuario.getUsername() + "!");
            manejarSesion(usuario, scanner);
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
        }
    }

    private static void manejarSesion(Usuario usuario, Scanner scanner) {
        String historialFile = "historial_" + usuario.getUsername() + ".txt";
        String deseadosFile = "deseados_" + usuario.getUsername() + ".txt";

        usuario.setHistorialCompras(leerProductosDesdeArchivo(historialFile));
        usuario.setListaDeseados(leerProductosDesdeArchivo(deseadosFile));

        int choice;
        do {
            System.out.println("\n=== Menú de Usuario ===");
            System.out.println("1. Ver historial de compras");
            System.out.println("2. Ver lista de deseados");
            System.out.println("3. Agregar producto a lista de deseados");
            System.out.println("4. Comprar producto");
            if (usuario.getRol().equalsIgnoreCase("true")) {
                System.out.println("5. Crear producto");
                System.out.println("6. Modificar producto");
            }
            System.out.println("0. Cerrar sesión");
            System.out.print("Elige una opción: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    mostrarHistorial(usuario);
                    break;
                case 2:
                    mostrarListaDeseados(usuario);
                    break;
                case 3:
                    agregarAListaDeseados(usuario, scanner);
                    guardarProductosEnArchivo(deseadosFile, usuario.getListaDeseados());
                    break;
                case 4:
                    comprarProducto(usuario, scanner);
                    guardarProductosEnArchivo(historialFile, usuario.getHistorialCompras());
                    break;
                case 5:
                    if (usuario.getRol().equalsIgnoreCase("true")) {
                        crearProducto(scanner);
                    }
                    break;
                case 6:
                    if (usuario.getRol().equalsIgnoreCase("true")) {
                        modificarProducto(scanner);
                    }
                    break;
                case 0:
                    System.out.println("Cerrando sesión...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (choice != 0);
    }


    private static void mostrarHistorial(Usuario usuario) {
        System.out.println("\n=== Historial de Compras ===");
        if (usuario.getHistorialCompras().isEmpty()) {
            System.out.println("No tienes compras registradas.");
        } else {
            usuario.getHistorialCompras().forEach(System.out::println);
        }
    }

    private static void mostrarListaDeseados(Usuario usuario) {
        System.out.println("\n=== Lista de Deseados ===");
        if (usuario.getListaDeseados().isEmpty()) {
            System.out.println("Tu lista de deseados está vacía.");
        } else {
            usuario.getListaDeseados().forEach(System.out::println);
        }
    }

    private static void agregarAListaDeseados(Usuario usuario, Scanner scanner) {
        System.out.println("\n=== Agregar a Lista de Deseados ===");
        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();
        Producto producto = buscarProducto(nombre);

        if (producto != null) {
            usuario.agregarALaListaDeseados(producto);
            System.out.println("Producto agregado a la lista de deseados.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    private static void comprarProducto(Usuario usuario, Scanner scanner) {
        listarProductosDisponibles();

        System.out.println("\n=== Comprar Producto ===");
        System.out.print("Nombre del producto: ");
        String nombre = scanner.nextLine();
        Producto producto = buscarProducto(nombre);

        if (producto != null) {
            if (producto.getCantidadEnStock() > 0) {
                usuario.agregarAlHistorial(producto);
                producto.setCantidadEnStock(producto.getCantidadEnStock() - 1);
                actualizarProductoEnArchivo(producto);
                System.out.println("Compra realizada con éxito. Stock restante: " + producto.getCantidadEnStock());
            } else {
                System.out.println("El producto está agotado.");
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }



    private static void crearProducto(Scanner scanner) {
        System.out.println("\n=== Crear Producto ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();

        System.out.print("Precio: ");
        double precio = scanner.nextDouble();

        System.out.print("Cantidad en stock: ");
        int cantidadEnStock = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Marca: ");
        String marca = scanner.nextLine();

        System.out.print("Etiquetas (separadas por comas): ");
        String[] etiquetas = scanner.nextLine().split(",");

        Producto producto = new Producto(nombre, precio, cantidadEnStock, marca, Arrays.asList(etiquetas));
        guardarProducto(producto);
        System.out.println("Producto creado exitosamente.");
    }

    private static Producto buscarProducto(String nombre) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCTS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(nombre)) {
                    List<String> tags = Arrays.asList(data[4].split(";"));
                    return new Producto(data[0], Double.parseDouble(data[1]),
                            Integer.parseInt(data[2]), data[3], tags);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al buscar el producto: " + e.getMessage());
        }
        return null;
    }

    private static void guardarProducto(Producto producto) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCTS_FILE_PATH, true))) {
            writer.write(producto.getNombre() + "," +
                    producto.getPrecio() + "," +
                    producto.getCantidadEnStock() + "," +
                    producto.getMarca() + "," +
                    String.join(";", producto.getTags()));
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el producto: " + e.getMessage());
        }
    }

    private static boolean isUserExists(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {

        }
        return false;
    }

    private static Usuario validarUsuario(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userDetails = line.split(",");
                if (userDetails[0].equals(username) && userDetails[2].equals(password)) {
                    return new Usuario(userDetails[0], userDetails[1], userDetails[2], userDetails[3]);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return null;
    }

    private static void guardarProductosEnArchivo(String filename, List<Producto> productos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Producto producto : productos) {
                writer.write(producto.getNombre() + "," +
                        producto.getPrecio() + "," +
                        producto.getCantidadEnStock() + "," +
                        producto.getMarca() + "," +
                        String.join(";", producto.getTags()));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar productos: " + e.getMessage());
        }
    }


    private static List<Producto> leerProductosDesdeArchivo(String filename) {
        List<Producto> productos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                List<String> tags = Arrays.asList(data[4].split(";"));
                Producto producto = new Producto(data[0], Double.parseDouble(data[1]),
                        Integer.parseInt(data[2]), data[3], tags);
                productos.add(producto);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            System.out.println("Error al leer productos: " + e.getMessage());
        }
        return productos;
    }
    private static void actualizarProductoEnArchivo(Producto productoModificado) {
        List<Producto> productos = leerProductosDesdeArchivo(PRODUCTS_FILE_PATH);

        for (int i = 0; i < productos.size(); i++) {
            if (productos.get(i).getNombre().equalsIgnoreCase(productoModificado.getNombre())) {
                productos.set(i, productoModificado);
                break;
            }
        }


        guardarProductosEnArchivo(PRODUCTS_FILE_PATH, productos);
    }
    private static void modificarProducto(Scanner scanner) {
        listarProductosDisponibles();

        System.out.println("\n=== Modificar Producto ===");
        System.out.print("Nombre del producto a modificar: ");
        String nombre = scanner.nextLine();
        Producto producto = buscarProducto(nombre);

        if (producto != null) {
            System.out.println("Producto encontrado: " + producto);
            System.out.println("Deja el campo vacío si no deseas cambiar un atributo.");

            System.out.print("Nuevo nombre (" + producto.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.isEmpty()) producto.setNombre(nuevoNombre);

            System.out.print("Nuevo precio (" + producto.getPrecio() + "): ");
            String nuevoPrecio = scanner.nextLine();
            if (!nuevoPrecio.isEmpty()) producto.setPrecio(Double.parseDouble(nuevoPrecio));

            System.out.print("Nueva cantidad en stock (" + producto.getCantidadEnStock() + "): ");
            String nuevaCantidad = scanner.nextLine();
            if (!nuevaCantidad.isEmpty()) producto.setCantidadEnStock(Integer.parseInt(nuevaCantidad));

            System.out.print("Nueva marca (" + producto.getMarca() + "): ");
            String nuevaMarca = scanner.nextLine();
            if (!nuevaMarca.isEmpty()) producto.setMarca(nuevaMarca);

            System.out.print("Nuevas etiquetas separadas por comas (" + producto.getTags() + "): ");
            String nuevasEtiquetas = scanner.nextLine();
            if (!nuevasEtiquetas.isEmpty()) {
                producto.setTags(Arrays.asList(nuevasEtiquetas.split(",")));
            }

            actualizarProductoEnArchivo(producto);
            System.out.println("Producto modificado exitosamente.");
        } else {
            System.out.println("Producto no encontrado.");
        }
    }
    private static void listarProductosDisponibles() {
        List<Producto> productos = leerProductosDesdeArchivo(PRODUCTS_FILE_PATH);
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles.");
        } else {
            System.out.println("\n=== Productos Disponibles ===");
            for (Producto producto : productos) {
                System.out.println("Nombre: " + producto.getNombre() +
                        ", Precio: " + producto.getPrecio() +
                        ", Stock: " + producto.getCantidadEnStock() +
                        ", Marca: " + producto.getMarca() +
                        ", Etiquetas: " + producto.getTags());
            }
        }
    }

}
