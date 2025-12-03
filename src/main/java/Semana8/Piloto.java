package Semana8;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@NoArgsConstructor
@DynamoDbBean
public class Piloto {
    private String nombre;
    private int numero;
    private String fechaNacimiento;
    private String nacionalidad;

    @DynamoDbAttribute("name")
    public String getNombre() {
        return nombre;
    }

    @DynamoDbAttribute("number")
    public int getNumero() {
        return numero;
    }

    @DynamoDbAttribute("nationality")
    public String getNacionalidad() {
        return nacionalidad;
    }

    @DynamoDbAttribute("birthDate")
    public String getFechaNacimiento() {
        return fechaNacimiento;
    }
}
