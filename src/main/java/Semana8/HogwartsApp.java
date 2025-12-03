package Semana8;



public class HogwartsApp {
    public static void main(String[] args) {
        OperacionesHogwarts operaciones = new OperacionesHogwarts();

        //Crear la tabla "CasasHogwarts"
        operaciones.crearTabla("CasasHogwarts");

        //Poblar con datos de ejemplo
        operaciones.poblarDatosEjemplo();

        //Mostrar todas las casas
        operaciones.imprimirTodasLasCasas();

        //Obtener una casa específica
        CasaHogwarts casa = operaciones.obtenerCasa("Dracorfan");
        System.out.println("Casa obtenida: " + casa);

        //Modificar: añadir nuevo estudiante (Cyrus Stormrider)
        if (casa != null) {
            Estudiante nuevoEstudiante = new Estudiante();
            nuevoEstudiante.setNombre("Cyrus Stormrider");
            nuevoEstudiante.setCurso(1);
            nuevoEstudiante.setFechaNacimiento("2011-12-20");
            nuevoEstudiante.setMascota("Tempus");

            // Añadir el nuevo estudiante a la lista existente
            casa.getEstudiantes().add(nuevoEstudiante);
            operaciones.actualizarCasa(casa);
            System.out.println("Casa actualizada con nuevo estudiante");
        }

        //Mostrar la casa actualizada
        operaciones.imprimirTodasLasCasas();

        //Borrar una casa
        operaciones.borrarCasa("Dracorfan");

        // Borrar la tabla al finalizar (opcional)
        // operaciones.borrarTabla("CasasHogwarts");
    }
}