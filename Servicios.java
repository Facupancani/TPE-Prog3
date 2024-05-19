import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Servicios {

    HashMap<Integer, Tarea> tareaMap;

    ArrayList<Tarea> TareasCriticas = new ArrayList<Tarea>();
    ArrayList<Tarea> TareasNoCriticas = new ArrayList<Tarea>();

    ArbolTareas ArbolTareas;

    // Completar con las estructuras y métodos privados que se requieran.
    /*
     * Expresar la complejidad temporal del constructor.
     */
    public Servicios(String pathProcesadores, String pathTareas) {

        Reader reader = new Reader();

        // Carga tareas en un ArrayList
        String contenidoTareas = reader.CSVtoString(pathTareas);
        ArrayList<Tarea> tareas = reader.CargaTareas(contenidoTareas);

        // Carga procesadores en un ArrayList
        String contenidoProcesadores = reader.CSVtoString(pathProcesadores);
        ArrayList<Procesador> procesadores = reader.CargaProcesadores(contenidoProcesadores);

        // Creo HashMap para el Servicio1
        tareaMap = new HashMap<>();
        for (Tarea tarea : tareas) {
            tareaMap.put(tarea.getId(), tarea);
        }

        // Creo ArrayLists para las tareas criticas y no criticas

        for (Tarea tarea : tareas) {
            if (tarea.esCritica() == true) {
                TareasCriticas.add(tarea);
            } else
                TareasNoCriticas.add(tarea);
        }

        // Creo el arbol de tareas para el servicio 3

        ArbolTareas = new ArbolTareas();
        for (Tarea tarea : tareas) {
            ArbolTareas.insertar(tarea);
        }

    }

    // La complejidad temporal del servicio 1 es O(1). Ya que el HashMap devuelve en
    // una sola interaccion el elemento con el ID dado.
    public Tarea servicio1(int ID) {
        return tareaMap.get(ID);
    }

    // La complejidad temporal del servicio 2 es O(n), siendo n el tamaño del
    // arreglo TareasCriticas o TareasNoCriticas, segun el caso elegido por el
    // usuario.
    public List<Tarea> servicio2(boolean esCritica) {
        if (esCritica) {
            return TareasCriticas;
        } else
            return TareasNoCriticas;
    }

    /*
     * La complejidad temporal del servicio 3 depende de en que orden se haya
     * cargado el arbol.
     * 
     * En el mejor de los casos, cuando el árbol está balanceado, la complejidad
     * temporal del servicio 3 es O(log n + k), donde n es el número total de nodos
     * en el árbol y k es el número de elementos dentro del rango especificado.
     * 
     * En el peor de los casos, cuando el árbol se carga de manera lineal formando
     * una lista, la complejidad temporal del servicio 3 es O(n + k), donde n es el
     * número total de nodos en el árbol y k es el número de elementos dentro del
     * rango.
     */

    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        ArrayList<Tarea> listado = new ArrayList<Tarea>();
        listado = this.ArbolTareas.getRaiz().listarTareasPrio(ArbolTareas.getRaiz(), listado, prioridadInferior,
                prioridadSuperior);
        for (Tarea t : listado) {
            System.out.println("Tarea dentro del rango: " + t);
        }
        return listado;
    }

    public static void main(String[] args) {
        Servicios serv = new Servicios("procesadores.csv", "tareas.csv");
        serv.servicio3(1, 2);
    }

}