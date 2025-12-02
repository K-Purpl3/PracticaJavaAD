package JDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcConLombok {
    public static void main(String[] args) {
        List<Persona> personas = new ArrayList<>();
        String url = "jdbc:h2:mem:testdb";

        try (Connection conexion = DriverManager.getConnection(url, "sa", "");
             Statement stmt = conexion.createStatement()) {

            // Crear tabla
            stmt.execute("""
                CREATE TABLE personas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(50),
                    edad INT,
                    ciudad VARCHAR(50)
                )
                """);

            // Insertar datos
            stmt.executeUpdate("""
                INSERT INTO personas (nombre, edad, ciudad) 
                VALUES ('Ana', 25, 'Madrid'),
                       ('Luis', 17, 'Barcelona'),
                       ('Carlos', 30, 'Valencia')
                """);

            // Consultar y mapear a objetos Persona
            String sql = "SELECT * FROM personas";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Persona p = new Persona();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setEdad(rs.getInt("edad"));
                p.setCiudad(rs.getString("ciudad"));

                personas.add(p);
            }

            rs.close();

            // Mostrar usando toString() generado por Lombok
            System.out.println("👥 Lista de personas:");
            personas.forEach(System.out::println);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}