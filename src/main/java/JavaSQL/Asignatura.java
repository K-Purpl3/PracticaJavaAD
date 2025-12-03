package JavaSQL;

public class Asignatura {
    private int id;
    private String nombre;
    private String aula;
    private int profesorId;

    public Asignatura(int id, String nombre, String aula, int profesorId) {
        this.id = id;
        this.nombre = nombre;
        this.aula = aula;
        this.profesorId = profesorId;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getAula() { return aula; }
    public int getProfesorId() { return profesorId; }

    @Override
    public String toString() {
        return "Asignatura [id=" + id + ", nombre=" + nombre + ", aula=" + aula + ", profesorId=" + profesorId + "]";
    }
}