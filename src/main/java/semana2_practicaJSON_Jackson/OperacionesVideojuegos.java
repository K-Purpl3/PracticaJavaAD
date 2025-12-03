package semana2_practicaJSON_Jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class OperacionesVideojuegos {

    /*
    Lee un archivo json y lo convierte en una lista de objetos Videojuegos
    @param ruta Ruta del archivo JSON
    @return Lista de videojuegos leidos
     */

    public static List<Videojuego> leerVideojuegos(String ruta) {

        //objectmapper es la clase principal de jackson para convertir de JSON a objetos java y viceversa
        ObjectMapper mapper = new ObjectMapper()
                //necesario para manejar el localdate correctamente
                .registerModule(new JavaTimeModule());

        try(Reader reader = Files.newBufferedReader(Path.of(ruta))){
            // readValue convierte el JSON en una lista (TypeReference indica el tipo genérico)
            return mapper.readValue(reader, new TypeReference<>() {});
        } catch (IOException e){
            System.out.println("Error en OperacionesVideojuegos.leerVideojuegos()");
            return List.of(); //Lista vacia si falla
        }
    }


    /**
     * Serializa (convierte) una lista de videojuegos a un archivo JSON.
     * @param lista Lista de videojuegos.
     * @param ruta Ruta del archivo destino.
     */
    public static void escribirVideojuegosAJSON(List<Videojuego> lista, String ruta) {

        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());

        //Actva el formato bonito del json
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try(Writer writer = Files.newBufferedWriter(Path.of(ruta))){
            //writeValue genera el JSON a partir de la lista y lo escribe en el archivo
            mapper.writeValue(writer, lista);
        } catch (IOException e){
            System.out.println("Error al escribir json");
        }
    }

    public static Videojuego crearNuevoVideojuego() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el nombre del juego: ");
        String nombre = sc.nextLine();

        System.out.println("plataforma");
        String plataforma = sc.nextLine();

        System.out.println("genero");
        String genero = sc.nextLine();

        return new Videojuego();
    }
}
