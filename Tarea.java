public class Tarea{

  int id_tarea;
  String nombre_tarea;
  int tiempo_ejecucion;
  Boolean es_critica;
  int nivel_prioridad;

  public Tarea(int id, String nombre, int tiempo, Boolean esCritica, int nivel) {
    this.id_tarea = id;
    this.nombre_tarea = nombre;
    this.tiempo_ejecucion = tiempo;
    this.es_critica = esCritica;
    this.nivel_prioridad = nivel;
  }

  public int getId() {
    return this.id_tarea;
  }

  public Boolean esCritica() {
    return this.es_critica;
  }

  @Override
  public String toString() {
    return "Tarea{" +
        "id_tarea=" + id_tarea +
        ", nombre_tarea='" + nombre_tarea + '\'' +
        ", tiempo_ejecucion=" + tiempo_ejecucion +
        ", es_critica=" + es_critica +
        ", nivel_prioridad=" + nivel_prioridad +
        '}';
  }

  public String getNombre_tarea() {
    return nombre_tarea;
  }

  public int getTiempo_ejecucion() {
    return tiempo_ejecucion;
  }

  public int getNivel_prioridad() {
    return nivel_prioridad;
  }
}