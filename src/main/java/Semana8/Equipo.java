package Semana8;

import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.List;

@Data
@NoArgsConstructor
@DynamoDbBean
public class Equipo {
    private String nombre;
    private String nacionalidad;
    private List<Piloto> pilotos;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("name")
    public String getNombre() {
        return nombre;
    }

    @DynamoDbAttribute("nationality")
    public String getNacionalidad() {
        return nacionalidad;
    }

    @DynamoDbAttribute("drivers")
    public List<Piloto> getPilotos() {
        return pilotos;
    }
}
