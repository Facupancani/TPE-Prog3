import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class Servicios {

    HashMap<Integer, Tarea> tareaMap;

    ArrayList<Tarea> TareasCriticas;
    ArrayList<Tarea> TareasNoCriticas;




    //Completar con las estructuras y métodos privados que se requieran.
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


        //Creo HashMap para el Servicio1
        tareaMap = new HashMap<>();
        for (Tarea tarea : tareas) {
            tareaMap.put(tarea.getId(), tarea);
        }


        //Creo ArrayLists para las tareas criticas y no criticas
        for(Tarea tarea : tareas){
            if(tarea.esCritica() == true){
                TareasCriticas.add(tarea);
            }else TareasNoCriticas.add(tarea);
        }

    }

    
    //La complejidad temporal del servicio 1 es O(1).
    public Tarea servicio1(int ID) {
        return tareaMap.get(ID);
    }
    

    
    // Expresar la complejidad temporal del servicio 2.
    public List<Tarea> servicio2(boolean esCritica) {
        return null;
    }
    
    // Expresar la complejidad temporal del servicio 3.
    public List<Tarea> servicio3(int prioridadInferior, int prioridadSuperior) {
        return null;
    }

        public static void main(String[] args) {
            Servicios serv = new Servicios( "procesadores.csv", "tareas.csv");
            System.out.println(serv.servicio1(11));
        }

}