package JavaSQL;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class OperacionesSQL {

    // Variables para almacenar la información de conexión a la base de datos
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static void main(String[] args) {

        // Cargar las propiedades de conexión desde el archivo db.properties
        loadDatabaseProperties();

        // Conectar a la base de datos utilizando DriverManager
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            /*
            // Bloque comentado que contiene ejemplos de SQL Injection y operaciones CRUD
            // 1. Mostrar vulnerabilidad de SQL Injection
            // 2. Ejecución segura con PreparedStatement
            // 3. Inserción de usuario
            // 4. Modificación de usuario
            // 5. Borrado de usuario
            */

            // Crear objeto de Operaciones para trabajar con asignaturas y estudiantes
            Operaciones op = new Operaciones();

            // Paso 4: listar todas las asignaturas de la base de datos
            op.listarAsignaturas().forEach(System.out::println);

            // Paso 5: Ejemplos de operaciones CRUD sobre estudiantes y asignaturas
            op.estudiantesPorCasa("Gryffindor"); // Listar estudiantes de una casa
            op.mascotaDeEstudiante("Hermione", "Granger"); // Obtener mascota de un estudiante
            op.numeroEstudiantesPorCasa(); // Contar estudiantes por casa
            op.insertarAsignatura("Programación Mágica", "Aula 5B", 1); // Insertar nueva asignatura
            op.modificarAula(10, "Aula 9A"); // Modificar aula de asignatura con id 10
            op.eliminarAsignatura(10); // Eliminar asignatura con id 10

        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    /**
     * Método para cargar las propiedades de conexión desde el archivo db.properties.
     * Asigna URL, USER y PASSWORD a las variables estáticas de la clase.
     */
    private static void loadDatabaseProperties() {
        Properties properties = new Properties();
        try (InputStream input = OperacionesSQL.class.getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input); // Cargar propiedades del archivo
            URL = properties.getProperty("db.url"); // Obtener URL de la BD
            USER = properties.getProperty("db.user"); // Obtener usuario
            PASSWORD = properties.getProperty("db.password"); // Obtener contraseña
        } catch (IOException ex) {
            System.err.println("Error al cargar el archivo de propiedades: " + ex.getMessage());
        }
    }

    /**
     * Ejemplo de SQL Injection.
     * Este método muestra cómo una consulta construida concatenando strings es vulnerable.
     * @param conn Conexión activa a la base de datos
     * @param username Nombre de usuario
     * @param password Contraseña ingresada
     */
    public static void mostrarSQLInjection(Connection conn, String username, String password) {
        try (Statement stmt = conn.createStatement()) { // Statement vulnerable a SQL Injection
            // Construcción de la consulta concatenando parámetros directamente
            String sql = "SELECT * FROM usuarios WHERE username = '" + username + "' AND password = '" + password + "';";
            System.out.println("Ejecutando consulta vulnerable: " + sql);

            ResultSet rs = stmt.executeQuery(sql); // Ejecutar consulta

            // Mostrar resultados en consola
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_usuario") +
                        " - Usuario: " + rs.getString("username") +
                        " - Contraseña: " + rs.getString("password"));
            }

            rs.close(); // Cerrar ResultSet
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    /**
     * Ejemplo de consulta segura usando PreparedStatement.
     * Evita la vulnerabilidad de SQL Injection.
     * @param conn Conexión activa a la base de datos
     * @param username Nombre de usuario
     * @param password Contraseña ingresada
     */
    public static void mostrarSQLSegura(Connection conn, String username, String password) {
        try {
            // Consulta parametrizada usando PreparedStatement
            String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username); // Asignar primer parámetro
            pstmt.setString(2, password); // Asignar segundo parámetro

            ResultSet rs = pstmt.executeQuery(); // Ejecutar consulta
            System.out.println("Ejecutando consulta segura: " + sql);

            // Mostrar resultados
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id_usuario") +
                        " - Usuario: " + rs.getString("username") +
                        " - Contraseña: " + rs.getString("password"));
            }

            // Cerrar recursos
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    /**
     * Inserta un nuevo usuario en la tabla 'usuarios'.
     * Devuelve el ID generado automáticamente.
     * @param conn Conexión activa
     * @param username Nombre de usuario
     * @param password Contraseña
     * @param email Correo electrónico
     * @return ID generado del nuevo usuario
     */
    public static int insertarUsuario(Connection conn, String username, String password, String email) {
        int idGenerado = -1;  // Para almacenar el ID del nuevo usuario
        try {
            String sql = "INSERT INTO usuarios (username, password, email) VALUES (?, ?, ?)";
            // PreparedStatement con RETURN_GENERATED_KEYS para obtener ID autogenerado
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);

            int filasAfectadas = pstmt.executeUpdate(); // Ejecutar inserción
            System.out.println("Filas afectadas al insertar: " + filasAfectadas);

            // Obtener el ID generado
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
            System.out.println("Usuario insertado con ID: " + idGenerado);
        } catch (SQLException e) {
            System.err.println("Error al insertar el usuario: " + e.getMessage());
            return -1;
        }
        return idGenerado;
    }

    /**
     * Modifica la contraseña de un usuario existente.
     * @param conn Conexión activa
     * @param username Nombre de usuario a modificar
     * @param nuevaPassword Nueva contraseña
     */
    public static void modificarUsuario(Connection conn, String username, String nuevaPassword) {
        try {
            String sql = "UPDATE usuarios SET password = ? WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nuevaPassword); // Asignar nueva contraseña
            pstmt.setString(2, username); // Indicar usuario a modificar

            int filasAfectadas = pstmt.executeUpdate(); // Ejecutar actualización
            System.out.println("Filas afectadas al modificar: " + filasAfectadas);
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error al modificar el usuario: " + e.getMessage());
        }
    }

    /**
     * Borra un usuario de la tabla 'usuarios' dado su ID.
     * @param conn Conexión activa
     * @param idUsuario ID del usuario a borrar
     */
    public static void borrarUsuario(Connection conn, int idUsuario) {
        try {
            String sqlBorrarUsuario = "DELETE FROM usuarios WHERE id_usuario = ?";
            PreparedStatement pstmtUsuario = conn.prepareStatement(sqlBorrarUsuario);
            pstmtUsuario.setInt(1, idUsuario); // Asignar ID del usuario a eliminar
            pstmtUsuario.executeUpdate(); // Ejecutar borrado
            pstmtUsuario.close();

            System.out.println("Usuario y cuenta borrados con éxito.");
        } catch (SQLException e) {
            System.err.println("Error al borrar el usuario: " + e.getMessage());
        }
    }
}
