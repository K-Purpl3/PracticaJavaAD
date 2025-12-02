package JDBC;

import java.sql.*;

public class JdbcPreparedStatement {
    public static void main(String[] args) {
        String url = "jdbc:h2:mem:testdb";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "");
             Statement stmt = conexion.createStatement()) {

            // Crear tabla
            stmt.execute("""
                CREATE TABLE productos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100),
                    precio DECIMAL(10,2),
                    stock INT
                )
                """);

            // INSERT con PreparedStatement
            String insertSQL = "INSERT INTO productos (nombre, precio, stock) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = conexion.prepareStatement(insertSQL)) {
                // Insertar primer producto
                pstmt.setString(1, "Laptop");
                pstmt.setDouble(2, 999.99);
                pstmt.setInt(3, 10);
                pstmt.executeUpdate();

                // Insertar segundo producto
                pstmt.setString(1, "Mouse");
                pstmt.setDouble(2, 25.50);
                pstmt.setInt(3, 50);
                pstmt.executeUpdate();

                System.out.println("✅ Productos insertados");
            }

            // SELECT con parámetro usando PreparedStatement
            String selectSQL = "SELECT * FROM productos WHERE precio > ?";

            try (PreparedStatement pstmt = conexion.prepareStatement(selectSQL)) {
                pstmt.setDouble(1, 50.0);  // Buscar productos más caros de 50€
                ResultSet rs = pstmt.executeQuery();

                System.out.println("\n🛒 Productos caros:");
                while (rs.next()) {
                    System.out.printf("%s - %.2f€ (Stock: %d)%n",
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("stock"));
                }
                rs.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}