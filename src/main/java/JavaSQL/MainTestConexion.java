package JavaSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainTestConexion {

    public static void main(String[] args) {

        // Credenciales directas para AWS RDS PostgreSQL
        String url = "jdbc:postgresql://hogwartspg.caflk73swjr8.us-east-1.rds.amazonaws.com:5432/hogwartspg";
        String user = "postgres";
        String password = "Hola-2001";

        // Consulta básica para probar la conexión
        String sql = "SELECT * FROM estudiantes";

        // Try-with-resources para cerrar conexión automáticamente
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Conexión exitosa. Datos de la tabla 'estudiantes':");

            // Iterar sobre los resultados y mostrarlos
            while (rs.next()) {
                // Supongo que la tabla estudiantes tiene columnas: id_estudiante, nombre, apellido
                int id = rs.getInt("id_estudiante");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                System.out.println("ID: " + id + " | Nombre: " + nombre + " " + apellido);
            }

        } catch (SQLException e) {
            System.err.println("Error al conectar o consultar la base de datos:");
            e.printStackTrace();
        }
    }
}
