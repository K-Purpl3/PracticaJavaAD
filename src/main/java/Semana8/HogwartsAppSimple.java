package Semana8;

public class HogwartsAppSimple {
    public static void main(String[] args) {
        System.out.println("Iniciando aplicacion Hogwarts");

        try {
            OperacionesHogwarts operaciones = new OperacionesHogwarts();

            // Crear tabla
            operaciones.crearTabla("CasasHogwarts");

            // Insertar datos de ejemplo
            operaciones.poblarDatosEjemplo();

            // Mostrar datos
            operaciones.imprimirTodasLasCasas();

            System.out.println("Aplicacion ejecutada exitosamente!");

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}