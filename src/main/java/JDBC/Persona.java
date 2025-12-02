package JDBC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor  // Constructor sin parámetros
@AllArgsConstructor // Constructor con todos los parámetros
public class Persona {
    private int id;
    private String nombre;
    private int edad;
    private String ciudad;
}