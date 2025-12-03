package JavaSQL;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    // Variables estáticas para almacenar la información de conexión
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    /**
     * Bloque estático: se ejecuta una sola vez cuando la clase se carga en memoria.
     * Sirve para cargar las propiedades de conexión desde el archivo db.properties.
     */
    static {
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties")) {
            // Crear un objeto Properties para cargar las configuraciones
            Properties props = new Properties();
            props.load(input); // Cargar el contenido del archivo .properties

            // Asignar valores a las variables estáticas
            URL = props.getProperty("db.url");
            USER = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

        } catch (Exception e) {
            // Captura cualquier error al leer el archivo o cargar las propiedades
            System.err.println("Error cargando configuración: " + e.getMessage());
        }
    }

    /**
     * Método público para obtener una conexión a la base de datos.
     * Cada llamada devuelve una nueva conexión usando DriverManager.
     * @return Connection objeto de conexión a la base de datos
     * @throws SQLException si falla la conexión
     */
    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
