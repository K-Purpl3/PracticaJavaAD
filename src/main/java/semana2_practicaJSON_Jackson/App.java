package semana2_practicaJSON_Jackson;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static semana2_practicaJSON_Jackson.OperacionesVideojuegos.*;

public class App {

    public static void main(String[] args) {

        //1.Leer el JSON original
        //se obtiene una lista de videojuegos a partir del json
        //List<Videojuego> videojuegos = new ArrayList<>(OperacionesVideojuegos.leerVideojuegos("videojuegos.json"));

        List<Videojuego> videojuegos = new ArrayList<>(leerVideojuegos("videojuegos.json"));

        //imprimimos por pantalla lo que se ha leido
        System.out.println("Lista de videojuegos");
        videojuegos.forEach(System.out::println);

        //2. Crear un nuevo videojuego

        //Videojuego nuevoJuego = OperacionesVideojuegos.crearNuevoVideojuego();
        Videojuego nuevoJuego = crearNuevoVideojuego();
        videojuegos.add(nuevoJuego);

        System.out.println("Juego agregado");
        System.out.println(nuevoJuego);

        //3. Volver a escribir la lista en un nuevo archivo jsn
        // esto crea el archivo videojuegos_Actualizados.json

        escribirVideojuegosAJSON(
                videojuegos, "src/main/resources/videojuegos_actualizados.json"
        );
    }

    /**
     * Crea un objeto Videojuego manualmente.
     * Es un ejemplo de cómo "fabricar" un videojuego para añadirlo al JSON.
     */
    private static Videojuego crearNuevoVideojuego() {

        // Objeto plataforma del videojuego
        Plataforma plataforma = new Plataforma("PlayStation 5", "Sony");

        // Lista de desarrolladores
        Desarrollador d = new Desarrollador("Naughty Dog", "United States");
        List<Desarrollador> devs = new ArrayList<>();
        devs.add(d);

        // Lista de reviews
        List<Review> reviews = new ArrayList<>();
        reviews.add(new Review("Incredible visuals and story.", LocalDate.of(2022, 8, 5), 5));
        reviews.add(new Review("Best graphics ever!", LocalDate.of(2022, 9, 15), 5));

        // Finalmente construimos el videojuego
        return new Videojuego(
                "The Last of Us Part II",    // título
                plataforma,                  // objeto Platforma
                "Action",                    // género
                devs,                        // desarrolladores
                LocalDate.of(2020, 6, 19),   // fecha de lanzamiento
                reviews                      // lista de reviews
        );
    }
}

