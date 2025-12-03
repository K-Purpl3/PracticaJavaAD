package semana2_practicaJSON_Jackson;

import lombok.Data;

@Data
public class Desarrollador {
    private String nombre;
    private String pais;

    public Desarrollador() {}

    public Desarrollador(String nombre, String pais){
        this.nombre = nombre;
        this.pais = pais;
    }

}
