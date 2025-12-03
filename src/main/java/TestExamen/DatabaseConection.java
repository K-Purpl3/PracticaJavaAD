package TestExamen;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConection {
    private static Connection connection = null;

    // Configuración para PostgreSQL (cambia según tu entorno)
    private static final String URL = "jdbc:postgresql://localhost:5432/tienda";
    private static final String USER = "postgres";
    private static final String PASSWORD = "tu_contraseña";

    // Para H2 (en memoria, solo para pruebas)
    // private static final String URL = "jdbc:h2:mem:tienda;DB_CLOSE_DELAY=-1";
    // private static final String USER = "sa";
    // private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Para PostgreSQL
                Class.forName("org.postgresql.Driver");
                // Para H2
                // Class.forName("org.h2.Driver");

                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver no encontrado", e);
            }
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}