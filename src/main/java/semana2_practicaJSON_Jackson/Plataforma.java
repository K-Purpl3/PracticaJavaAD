package semana2_practicaJSON_Jackson;


import lombok.Data;

@Data
public class Plataforma {
    private String nombre;
    private String fabricante;

    public Plataforma() {}

    public Plataforma(String nombre, String fabricante){
        this.nombre = nombre;
        this.fabricante = fabricante;
    }
}
