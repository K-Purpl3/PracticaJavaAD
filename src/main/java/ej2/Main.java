package ej2;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static List<String> leerArchivo(String ruta) throws ArchivoNoEncontradoException {

        Path path = Paths.get(ruta);
        // TU CÓDIGO AQUÍ
    if (!Files.exists(Paths.get(ruta))) {
        throw new ArchivoNoEncontradoException(ruta);
    }

    try(Stream<String> lineas = Files.lines(path)){
        return lineas.collect(Collectors.toList());
    } catch (IOException e){
        throw new ArchivoNoEncontradoException(ruta);
    }
    }

    public static List<Map<String, String>> leerCSV(String ruta) throws ArchivoNoEncontradoException {
        Path path = Paths.get(ruta);
        if (!Files.exists(Paths.get(ruta))) {
            throw new ArchivoNoEncontradoException(ruta);
        }

        List<Map<String, String>> resultado = new ArrayList<>();

        try {
            List<String> lineas = Files.readAllLines(path);
            if (lineas.isEmpty()) {
                return new ArrayList<>();
            }

            //array de strings, empezamos por la posicion 0, el regex es la coma
            String[] cabeceras = lineas.get(0).split(",");

            //Procesar cada linea, empezando desde el principio
            for (int i = 1; i < lineas.size(); i++) {
                String linea = lineas.get(i);
                if(linea.trim().isEmpty()) continue; //salta lineas vacias

                String[] valores = linea.split(",");

                //crear Map para cada linea

                Map<String, String> fila = new HashMap<>();
                for (int j = 0; j < cabeceras.length; j++) {
                    String clave = cabeceras[j].trim();
                    String valor = (j<valores.length) ? valores[j].trim():"";

                    fila.put(clave, valor);
                }

                resultado.add(fila);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }


    public static void crearArchivosDePrueba() {
        try {
            // Crear archivo CSV de prueba
            String csvContenido = "nombre,edad,ciudad\n" +
                    "Ana,25,Madrid\n" +
                    "Luis,17,Barcelona\n" +
                    "Carlos,30,Valencia\n" +
                    "Marta,16,Sevilla\n" +
                    "Pedro,22,Bilbao";

            Files.write(Paths.get("./personas2.csv"), csvContenido.getBytes());

            // Crear archivo TXT de prueba
            String txtContenido = "Este es un archivo de prueba.\n" +
                    "Línea 2 del archivo.\n" +
                    "Línea 3 del archivo.";

            Files.write(Paths.get("./LoremIpsum2.txt"), txtContenido.getBytes());

            System.out.println("Archivos de prueba creados exitosamente.");

        } catch (IOException e) {
            System.out.println("Error creando archivos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        crearArchivosDePrueba();
        try {
            System.out.println("Lectura de datos csv 2");
            List<String> lineas = leerArchivo("personas2.csv");
            lineas.forEach(System.out::println);
        } catch (ArchivoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            System.out.println("Lectura de datos txt");
            List<String> lineas = leerArchivo("LoremIpsum.txt");
            lineas.forEach(System.out::println);
        } catch (ArchivoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }

        try {
            System.out.println("Lectura de datos csv");
            List<Map<String, String>> datos = leerCSV("personas.csv");

            //recorrer lista de map
            for (Map<String, String> fila : datos) {
                System.out.println("--------");
                for (Map.Entry<String, String> entrada : fila.entrySet()) {
                    System.out.println(entrada.getKey() + ": " + entrada.getValue());
                }
            }

        } catch (ArchivoNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}