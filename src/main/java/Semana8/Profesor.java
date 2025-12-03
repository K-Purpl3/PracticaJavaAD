package Semana8;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

@Data
@NoArgsConstructor
@DynamoDbBean
public class Profesor {
    private String nombre;
    private String asignatura;

    @DynamoDbAttribute("nombre")
    public String getNombre() {
        return nombre;
    }

    @DynamoDbAttribute("asignatura")
    public String getAsignatura() {
        return asignatura;
    }
}