package JavaSQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Operaciones {

    /**
     * Método para listar todas las asignaturas de la base de datos.
     * @return Lista de objetos Asignatura con todos los registros de la tabla 'asignatura'.
     */
    public List<Asignatura> listarAsignaturas() {
        List<Asignatura> lista = new ArrayList<>(); // Lista donde se almacenarán las asignaturas
        String sql = "SELECT * FROM asignatura"; // Consulta SQL para obtener todas las asignaturas
        try (Connection conn = ConexionBD.conectar(); // Obtener conexión a la base de datos
             PreparedStatement ps = conn.prepareStatement(sql); // Preparar la sentencia SQL
             ResultSet rs = ps.executeQuery()) { // Ejecutar la consulta y obtener resultados
            while (rs.next()) { // Iterar sobre cada fila del ResultSet
                // Crear un objeto Asignatura con los datos de la fila y añadirlo a la lista
                lista.add(new Asignatura(
                        rs.getInt("id_asignatura"),
                        rs.getString("nombre"),
                        rs.getString("aula"),
                        rs.getInt("id_profesor")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asignaturas: " + e.getMessage());
        }
        return lista; // Devolver la lista con todas las asignaturas
    }

    /**
     * Método que muestra los estudiantes pertenecientes a una casa específica.
     * @param casa Nombre de la casa por la que filtrar.
     */
    public void estudiantesPorCasa(String casa) {
        String sql = "SELECT nombre, apellido FROM estudiante e " +
                "JOIN casa c ON e.id_casa = c.id_casa WHERE c.nombre = ?";
        try (Connection conn = ConexionBD.conectar(); // Conexión a la base de datos
             PreparedStatement ps = conn.prepareStatement(sql)) { // Preparar la consulta SQL
            ps.setString(1, casa); // Asignar el valor del parámetro 'casa' a la consulta
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta
            while (rs.next()) { // Iterar sobre los resultados
                // Mostrar en consola el nombre y apellido de cada estudiante
                System.out.println(rs.getString("nombre") + " " + rs.getString("apellido"));
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar estudiantes: " + e.getMessage());
        }
    }

    /**
     * Método que obtiene la mascota de un estudiante específico.
     * @param nombre Nombre del estudiante
     * @param apellido Apellido del estudiante
     */
    public void mascotaDeEstudiante(String nombre, String apellido) {
        String sql = "SELECT m.nombre FROM mascota m " +
                "JOIN estudiante e ON m.id_estudiante = e.id_estudiante " +
                "WHERE e.nombre = ? AND e.apellido = ?";
        try (Connection conn = ConexionBD.conectar(); // Conexión a la base de datos
             PreparedStatement ps = conn.prepareStatement(sql)) { // Preparar la sentencia
            ps.setString(1, nombre); // Asignar nombre del estudiante
            ps.setString(2, apellido); // Asignar apellido del estudiante
            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta
            if (rs.next()) // Si existe resultado
                System.out.println("Mascota: " + rs.getString("nombre")); // Mostrar el nombre de la mascota
            else
                System.out.println("No se encontró mascota."); // Si no hay mascota asociada
        } catch (SQLException e) {
            System.err.println("Error al consultar mascota: " + e.getMessage());
        }
    }

    /**
     * Método que cuenta el número de estudiantes por cada casa.
     * Muestra el nombre de la casa y el total de estudiantes en consola.
     */
    public void numeroEstudiantesPorCasa() {
        String sql = "SELECT c.nombre, COUNT(e.id_estudiante) AS total " +
                "FROM casa c LEFT JOIN estudiante e ON c.id_casa = e.id_casa " +
                "GROUP BY c.nombre";
        try (Connection conn = ConexionBD.conectar(); // Conexión a la base de datos
             Statement st = conn.createStatement(); // Crear un Statement simple
             ResultSet rs = st.executeQuery(sql)) { // Ejecutar la consulta
            while (rs.next()) { // Iterar sobre los resultados
                System.out.println(rs.getString("nombre") + ": " + rs.getInt("total")); // Mostrar casa y total
            }
        } catch (SQLException e) {
            System.err.println("Error al contar estudiantes: " + e.getMessage());
        }
    }

    /**
     * Método para insertar una nueva asignatura en la base de datos.
     * @param nombre Nombre de la asignatura
     * @param aula Aula donde se imparte
     * @param idProfesor Id del profesor responsable
     */
    public void insertarAsignatura(String nombre, String aula, int idProfesor) {
        String sql = "INSERT INTO asignatura (nombre, aula, id_profesor) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.conectar(); // Conexión
             PreparedStatement ps = conn.prepareStatement(sql)) { // Preparar la sentencia INSERT
            ps.setString(1, nombre); // Asignar nombre
            ps.setString(2, aula); // Asignar aula
            ps.setInt(3, idProfesor); // Asignar id del profesor
            ps.executeUpdate(); // Ejecutar inserción
            System.out.println("Asignatura insertada correctamente."); // Confirmación
        } catch (SQLException e) {
            System.err.println("Error al insertar asignatura: " + e.getMessage());
        }
    }

    /**
     * Método para modificar el aula de una asignatura existente.
     * @param idAsignatura Id de la asignatura a modificar
     * @param nuevaAula Nuevo aula asignada
     */
    public void modificarAula(int idAsignatura, String nuevaAula) {
        String sql = "UPDATE asignatura SET aula = ? WHERE id_asignatura = ?";
        try (Connection conn = ConexionBD.conectar(); // Conexión
             PreparedStatement ps = conn.prepareStatement(sql)) { // Preparar UPDATE
            ps.setString(1, nuevaAula); // Asignar nuevo aula
            ps.setInt(2, idAsignatura); // Indicar cuál asignatura modificar
            ps.executeUpdate(); // Ejecutar la actualización
            System.out.println("Aula modificada correctamente."); // Confirmación
        } catch (SQLException e) {
            System.err.println("Error al modificar aula: " + e.getMessage());
        }
    }

    /**
     * Método para eliminar una asignatura de la base de datos.
     * @param idAsignatura Id de la asignatura a eliminar
     */
    public void eliminarAsignatura(int idAsignatura) {
        String sql = "DELETE FROM asignatura WHERE id_asignatura = ?";
        try (Connection conn = ConexionBD.conectar(); // Conexión
             PreparedStatement ps = conn.prepareStatement(sql)) { // Preparar DELETE
            ps.setInt(1, idAsignatura); // Indicar la asignatura a eliminar
            ps.executeUpdate(); // Ejecutar eliminación
            System.out.println("Asignatura eliminada correctamente."); // Confirmación
        } catch (SQLException e) {
            System.err.println("Error al eliminar asignatura: " + e.getMessage());
        }
    }
}
