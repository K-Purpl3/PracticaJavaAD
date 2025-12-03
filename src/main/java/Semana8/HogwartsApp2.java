package Semana8;



public class HogwartsApp2 {
    public static void main(String[] args) {
        OperacionesHogwarts operaciones = new OperacionesHogwarts();

        //1-Crear la tabla
        operaciones.crearTabla("CasasHogwarts");

        //2-Poblar desde JSON
        operaciones.poblarCasasDesdeJson("CasasHogwarts.json");

        //3-Mostrar todas las casas
        operaciones.imprimirTodasLasCasas();

        //4-Resto de operaciones CRUD
        CasaHogwarts casa = operaciones.obtenerCasa("Dracorfan");

        //5-Añadir nuevo estudiante (modificacipn)
        if (casa != null) {
            Estudiante nuevoEstudiante = new Estudiante();
            nuevoEstudiante.setNombre("Cyrus Stormrider");
            nuevoEstudiante.setCurso(1);
            nuevoEstudiante.setFechaNacimiento("2011-12-20");
            nuevoEstudiante.setMascota("Tempus");

            casa.getEstudiantes().add(nuevoEstudiante);
            operaciones.actualizarCasa(casa);
        }

        //6-Mostrar resultado final
        operaciones.imprimirTodasLasCasas();
    }
}