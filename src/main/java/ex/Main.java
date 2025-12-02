package ex;

import ex.Persona;

import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {
        List<Persona> personas = Arrays.asList(
                new Persona("Ana", 25, "Madrid"),
                new Persona("Luis", 17, "Barcelona"),
                new Persona("Carlos", 30, "Valencia"),
                new Persona("Marta", 16, "Sevilla"),
                new Persona("Pedro", 22, "Bilbao")
        );

        // TU CÓDIGO AQUÍ

        List<String> nombresMayores = personas.stream()
                .filter(persona -> persona.getEdad() > 18)
                .map(Persona::getNombre)
                .sorted()
                .collect(Collectors.toList());
        System.out.println(nombresMayores);
    }
}