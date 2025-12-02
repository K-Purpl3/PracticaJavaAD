package JDBC;

import java.sql.*;

public class JdbcBasico {
    public static void main(String[] args) {
        // URL de conexión para H2 en memoria
        String url = "jdbc:h2:mem:testdb";
        String usuario = "sa";
        String contraseña = "";

        Connection conexion = null;

        try {
            // 1. Cargar el driver (no necesario desde Java 6+)
            Class.forName("org.h2.Driver");

            // 2. Establecer conexión
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a H2");

            // 3. Crear una tabla
            String crearTablaSQL = """
                CREATE TABLE IF NOT EXISTS personas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(50) NOT NULL,
                    edad INT,
                    ciudad VARCHAR(50)
                )
                """;

            Statement stmt = conexion.createStatement();
            stmt.execute(crearTablaSQL);
            System.out.println("✅ Tabla 'personas' creada");

            // 4. Insertar datos
            String insertarSQL = """
                INSERT INTO personas (nombre, edad, ciudad) 
                VALUES ('Ana', 25, 'Madrid'),
                       ('Luis', 17, 'Barcelona'),
                       ('Carlos', 30, 'Valencia')
                """;

            int filasInsertadas = stmt.executeUpdate(insertarSQL);
            System.out.println("✅ " + filasInsertadas + " filas insertadas");

            // 5. Consultar datos
            String consultarSQL = "SELECT * FROM personas";
            ResultSet resultados = stmt.executeQuery(consultarSQL);

            System.out.println("\n📊 Datos en la tabla:");
            System.out.println("ID | Nombre  | Edad | Ciudad");
            System.out.println("-----------------------------");

            while (resultados.next()) {
                int id = resultados.getInt("id");
                String nombre = resultados.getString("nombre");
                int edad = resultados.getInt("edad");
                String ciudad = resultados.getString("ciudad");

                System.out.printf("%-2d | %-7s | %-4d | %s%n",
                        id, nombre, edad, ciudad);
            }

            // 6. Cerrar recursos
            resultados.close();
            stmt.close();

        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: Driver no encontrado");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error SQL: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 7. Cerrar conexión (IMPORTANTE)
            try {
                if (conexion != null) conexion.close();
                System.out.println("\n🔌 Conexión cerrada");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}