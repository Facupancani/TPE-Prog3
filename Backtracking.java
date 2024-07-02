import java.util.*;

public class Backtracking {
    private HashMap<Procesador, LinkedList<Tarea>> solucion = new LinkedHashMap<>();
    private LinkedList<Procesador> procesadores;
    private LinkedList<Tarea> colaTareas;
    private int cantEstadosGenerados = 0;
    private int mejorTiempo = 0;
    private int tiempoMaximoNoRefrigerado;
    private Integer tiempoParcial;

    public Backtracking(String pathProcesadores, String pathTareas, int tiempoMax) {
        Reader reader = new Reader();
        String contenidoProcesadores = reader.CSVtoString(pathProcesadores);
        this.procesadores = reader.CargaProcesadores(contenidoProcesadores);
        String contenidoTareas = reader.CSVtoString(pathTareas);
        this.colaTareas = reader.CargaTareas(contenidoTareas);
        this.tiempoMaximoNoRefrigerado = tiempoMax;
    }

    /*
     * Para la solucion Backtracking implementada, se pone los procesadores en una lista y se crea una cola de tareas.
     * Se recorren los procesadores y se toma el primer elemento de la cola de tareas, eliminandolo de la cola.
     * Y tomo el primer procesador en el que puedo insertar la tarea, revisando que la asignacion cumpla las restricciones de tiempo, cant de criticas (segun corresponda) y no supere la poda. Esto hasta que encuentre un procesador valido (sino, no tiene solucion).
     * Una vez se termine la cola de tareas, se vuelve al estado anterior de la recursion, se quita la tarea del procesador para evaluar la asignación de la tarea en el siguiente procesador disponible.
     * Asi, probamos todas las combinaciones entre tareas y procesadores para poder encontrar la mejor solucion posible.
     */

    public Backtracking(String pathProcesadores, String pathTareas, int tiempoMax, Integer aproximacion) {
        this(pathProcesadores, pathTareas, tiempoMax);
        this.mejorTiempo = aproximacion;
    }

    public HashMap<Procesador, LinkedList<Tarea>> backtracking() {
        HashMap<Procesador, LinkedList<Tarea>> solucionParcial = new LinkedHashMap<Procesador, LinkedList<Tarea>>();
        for (Procesador p : procesadores) {
            solucion.put(p, new LinkedList<Tarea>());
            solucionParcial.put(p, new LinkedList<Tarea>());
        }
        backtracking(solucionParcial);
        if (solucionVacia()) {
            return new HashMap<>();
        }
        return solucion;
    }

    private void backtracking(HashMap<Procesador, LinkedList<Tarea>> solucionParcial) {
        this.cantEstadosGenerados++;
        tiempoParcial = getMaxTiempo(solucionParcial);
        if (tiempoParcial <= mejorTiempo || mejorTiempo==0) {
            if (colaTareas.isEmpty()) {
                reemplazarMejorSolucion(solucionParcial, tiempoParcial);
            } else {

                Tarea nextTarea = colaTareas.remove();
                for (Procesador procesador : this.procesadores) {
                    if (procesador.puedeInsertar(nextTarea, tiempoMaximoNoRefrigerado)) {
                        agregarTareaAProc(procesador, nextTarea, solucionParcial);
                        backtracking(solucionParcial);
                        sacarTareaAProc(procesador, nextTarea, solucionParcial);
                    }
                }

                colaTareas.add(0, nextTarea);
            }
        }
    }

    private void agregarTareaAProc(Procesador procesador, Tarea tarea,
        HashMap<Procesador, LinkedList<Tarea>> solucionParcial) {
        solucionParcial.get(procesador).add(tarea);
        procesador.insertar(tarea);
    }

    private void sacarTareaAProc(Procesador procesador, Tarea tarea,
            HashMap<Procesador, LinkedList<Tarea>> solucionParcial) {
        solucionParcial.get(procesador).remove(tarea);
        procesador.remover(tarea);
    }

    private Integer getMaxTiempo(HashMap<Procesador, LinkedList<Tarea>> solucionParcial) {
        Iterator<Procesador> itProcesadores = solucionParcial.keySet().iterator();
        Integer maxTiempo = 0;

        while (itProcesadores.hasNext()) {
            Procesador nextProcesador = itProcesadores.next();
            Integer tiempoNextProcesador = nextProcesador.getTiempo_ejecucion();
            if (tiempoNextProcesador > maxTiempo)
                maxTiempo = tiempoNextProcesador;
        }
        return maxTiempo;
    }

    private void reemplazarMejorSolucion(HashMap<Procesador, LinkedList<Tarea>> solucionParcial,
            Integer maxTiempoParcial) {
        for (Procesador procesador : procesadores) {
            LinkedList<Tarea> tareasSolucionParcial = solucionParcial.get(procesador);
            solucion.get(procesador).clear();
            solucion.get(procesador).addAll(tareasSolucionParcial);
        }
        this.mejorTiempo = maxTiempoParcial;
    }

    public Integer getTiempoMejorSolucion() {
        if (solucionVacia())
            return -1;
        return mejorTiempo;
    }

    private boolean solucionVacia() {
        boolean vacio = true;
        Iterator<Procesador> itProcesadores = solucion.keySet().iterator();

        while (itProcesadores.hasNext()) {
            Procesador nextProcesador = itProcesadores.next();
            if (solucion.get(nextProcesador).size() > 0) {
                vacio = false;
                return vacio;
            }
        }
        return vacio;
    }

    public int getCantEstadosGenerados() {
        return cantEstadosGenerados;
    }

    public void ImprimirResultado() {
        Iterator<Map.Entry<Procesador, LinkedList<Tarea>>> itProcesadores = solucion.entrySet().iterator();
        int iteracion = 1;
    
        System.out.println("\n\n===== RESULTADOS BACKTRACKING =====");
        while (itProcesadores.hasNext()) {
            Map.Entry<Procesador, LinkedList<Tarea>> entry = itProcesadores.next();
            Procesador nextProcesador = entry.getKey();
            LinkedList<Tarea> tareasAsignadas = entry.getValue();
    
            // Calcular el tiempo de ejecución sumando los tiempos de ejecución de las tareas asignadas
            int tiempoEjecucion = tareasAsignadas.stream().mapToInt(Tarea::getTiempo_ejecucion).sum();
    
            System.out.printf("\n Procesador %d:\n", iteracion);
            System.out.printf("  ID: %s\n", nextProcesador.getId());
            System.out.printf("  Tiempo Procesador: %d\n", tiempoEjecucion); 
            System.out.printf("  Refrigeracion: %s\n", nextProcesador.esta_refrigerado());
            if (!nextProcesador.esta_refrigerado()) { 
                System.out.println("    -Tiempo maximo no refrigerados: " + tiempoEjecucion);
            }
            System.out.println("  Tareas asignadas:");
            for (Tarea tarea : tareasAsignadas) {
                System.out.printf("    - %s\n", tarea);
            }
            iteracion++;
        }
    
        System.out.println("\n===== Tiempos =====");
        System.out.println(" Tiempo maximo para no refrigerados: " + this.tiempoMaximoNoRefrigerado);
        if (this.getTiempoMejorSolucion() == -1) {
            System.out.println("Tiempo máximo de solución: No hay solución");
        } else {
            System.out.printf("Tiempo máximo de solución: %d\n", this.getTiempoMejorSolucion());
        }
        
        System.out.println("Cantidad de estados generados: " + this.getCantEstadosGenerados());
    }
    
}