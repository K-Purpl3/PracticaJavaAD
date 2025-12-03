package semana2_practicaJSON_Jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDate;

@Data
public class Review {
    private String comentario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fecha;

    private int rating;

    public Review(){}

    public Review(String comentario, LocalDate fecha, int rating){
        this.comentario = comentario;
        this.fecha = fecha;
        this.rating = rating;
    }
}
