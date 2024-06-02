import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Servicios {

    ArrayList<Procesador> procesadores = new ArrayList<Procesador>();

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
        this.procesadores = reader.CargaProcesadores(contenidoProcesadores);


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

    /*
    *   Para la distribucion de las tareas sobre los procesadores, teniendo las restricciones en cuenta, planteo la siguiente forma de realizar las asignaciones con bactracking de manera optima;
    *    1. Separar las Tareas criticas y no criticas en dos arreglos ordenados de mayor a menor por tiempo de ejecucion
    *    2. Inserto primero las tareas criticas y luego las tareas no criticas (piedras y arena)
    *    3. "BuscarProcesadorMenor" devuelve el procesador compatible con el menor tiempo total de ejecucion de tareas asignadas
    */
    public void greedy(int TiempoMaxNoRefrigerados) {
        Collections.sort(TareasCriticas, Tarea.TiempoEjecucionComparator);
        Collections.sort(TareasNoCriticas, Tarea.TiempoEjecucionComparator);
        Collections.sort(procesadores, Procesador.RefrigeradoComparator);

        

        for(Tarea tc : TareasCriticas){
            Procesador p = BuscarProcesadorMenor(procesadores, tc, TiempoMaxNoRefrigerados);
            p.insertar(tc);
        }
        for(Tarea tc : TareasNoCriticas){
            Procesador p = BuscarProcesadorMenor(procesadores, tc, TiempoMaxNoRefrigerados);
            p.insertar(tc);
        }

    return;
    }

    public Procesador BuscarProcesadorMenor(ArrayList<Procesador> procesadores, Tarea t, int TiempoMaxNoRefrigerados){
        Procesador procesadorMenor = null;
        int tiempoMenor = 0;
        
        for (Procesador p : procesadores){
            if( p.tiempoEjecucion() < tiempoMenor){
                if (p.puedeInsertar(t,TiempoMaxNoRefrigerados)){
                    procesadorMenor = p;

                }
            }
        }
        return procesadorMenor;
    }



    public static void main(String[] args) {
        Servicios serv = new Servicios("procesadores.csv", "tareas.csv");
        serv.servicio3(1, 2);
    }

}