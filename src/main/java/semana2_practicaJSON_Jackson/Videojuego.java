package semana2_practicaJSON_Jackson;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Videojuego {
    private String title;
    private Plataforma plataforma;
    private String genre;
    private List<Desarrollador> developers;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private List<Review> reviews;

    public Videojuego() {}

    public Videojuego(String title,
                      Plataforma platforma,
                      String genre,
                      List<Desarrollador> developers,
                      LocalDate releaseDate,
                      List<Review> reviews) {
        this.title = title;
        this.plataforma = platforma;
        this.genre = genre;
        this.developers = developers;
        this.releaseDate = releaseDate;
        this.reviews = reviews;
    }
}