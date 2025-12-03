package TestExamen;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductDAO productoDAO = new ProductDAO();

        // Ejercicio 1: Obtener todos los productos y mostrarlos
        System.out.println("=== EJERCICIO 1: Obtener todos los productos ===");
        List<Producto> productos = productoDAO.obtenerTodos();
        productos.forEach(System.out::println);

        // Ejercicio 2: Exportar productos a JSON
        System.out.println("\n=== EJERCICIO 2: Exportar productos a JSON ===");
        try {
            String jsonProductos = JsonUtil.toJson(productos);
            System.out.println("Productos en formato JSON:");
            System.out.println(jsonProductos);

            // Guardar en archivo
            JsonUtil.guardarEnArchivo(productos, "productos.json");
            System.out.println("✅ Productos guardados en 'productos.json'");
        } catch (JsonProcessingException e) {
            System.err.println("Error convirtiendo a JSON: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error guardando archivo: " + e.getMessage());
        }

        // Ejercicio 3: Leer un archivo JSON y cargar productos
        System.out.println("\n=== EJERCICIO 3: Leer productos desde JSON ===");
        try {
            // Simulamos leer desde un archivo, pero primero guardemos uno de ejemplo
            Producto nuevoProducto = Producto.builder()
                    .nombre("Nuevo Producto")
                    .precio(99.99)
                    .stock(10)
                    .categoria("Prueba")
                    .build();

            JsonUtil.guardarEnArchivo(nuevoProducto, "nuevo_producto.json");
            System.out.println("✅ Archivo 'nuevo_producto.json' creado");

            // Leer el producto desde el archivo
            Producto productoLeido = JsonUtil.leerDesdeArchivo("nuevo_producto.json", Producto.class);
            System.out.println("Producto leído desde JSON: " + productoLeido);

            // Insertar en la base de datos
            productoDAO.insertar(productoLeido);
            System.out.println("✅ Producto insertado en la base de datos");
        } catch (IOException e) {
            System.err.println("Error leyendo/guardando archivo: " + e.getMessage());
        }

        // Ejercicio 4: Actualizar un producto
        System.out.println("\n=== EJERCICIO 4: Actualizar un producto ===");
        productoDAO.obtenerPorId(1).ifPresent(producto -> {
            System.out.println("Producto antes de actualizar: " + producto);
            producto.setPrecio(producto.getPrecio() * 0.9); // Aplicar 10% de descuento
            productoDAO.actualizar(producto);
            System.out.println("Producto después de actualizar: " + producto);
        });

        // Ejercicio 5: Eliminar un producto
        System.out.println("\n=== EJERCICIO 5: Eliminar un producto ===");
        // Primero insertamos un producto para luego eliminarlo
        Producto productoEliminar = Producto.builder()
                .nombre("Producto a eliminar")
                .precio(10.0)
                .stock(5)
                .categoria("Temporal")
                .build();
        productoDAO.insertar(productoEliminar);
        System.out.println("Producto insertado para eliminar: " + productoEliminar);

        if (productoEliminar.getId() != null) {
            productoDAO.eliminar(productoEliminar.getId());
            System.out.println("✅ Producto eliminado");
        }

        // Mostrar estado final
        System.out.println("\n=== ESTADO FINAL DE LA BASE DE DATOS ===");
        productoDAO.obtenerTodos().forEach(System.out::println);
    }
}