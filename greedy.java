import java.util.*;

public class Greedy{
    private HashMap<Procesador, LinkedList<Tarea>> solucion = new LinkedHashMap<>();
    private int TiempoMaxGreedy = 0;
    private int tiempoMaximoNoRefrigerado = 0;
    LinkedList<Procesador> procesadores = new LinkedList<Procesador>();
    LinkedList<Tarea> tareas = new LinkedList<Tarea>();
    ArrayList<Tarea> TareasCriticas = new ArrayList<Tarea>();
    ArrayList<Tarea> TareasNoCriticas = new ArrayList<Tarea>();

    public Greedy(String pathProcesadores, String pathTareas, int tiempoMax) {
        Reader reader = new Reader();
        String contenidoProcesadores = reader.CSVtoString(pathProcesadores);
        this.procesadores = reader.CargaProcesadores(contenidoProcesadores);
        String contenidoTareas = reader.CSVtoString(pathTareas);
        this.tareas = reader.CargaTareas(contenidoTareas);
        this.tiempoMaximoNoRefrigerado = tiempoMax;

        for (Procesador p : procesadores) {
            solucion.put(p, new LinkedList<Tarea>());
        }

        for (Tarea tarea : tareas) {
            if (tarea.esCritica() == true) {
                TareasCriticas.add(tarea);
            } else
                TareasNoCriticas.add(tarea);
        }
    }

    public void greedy() {
        Collections.sort(this.TareasCriticas, Tarea.TiempoEjecucionComparator);
        Collections.sort(TareasNoCriticas, Tarea.TiempoEjecucionComparator);
        Collections.sort(procesadores, Procesador.RefrigeradoComparator);

        // Caso en que las tareas criticas no entren
        if (this.TareasCriticas.size() > (procesadores.size() * 2)) {
            System.out.println("No hay procesadores suficientes para manejar las tareas criticas");
            return;
        }
        for (Tarea tc : TareasCriticas) {
            Procesador p = BuscarProcesadorMenor(tc);
            agregarTareaAProc(p, tc);
        }
        for (Tarea t : TareasNoCriticas) {
            Procesador p = BuscarProcesadorMenor(t);
            agregarTareaAProc(p, t);
        }
        this.getTiempo();
        ImprimirResultado();
    }

    public Procesador BuscarProcesadorMenor(Tarea t){
        Procesador procesadorMenor = null;
        int tiempoMenor = -1;
        
        //O(n) n= largo ArrProcesadores
        for (Procesador p : procesadores){
            if( p.tiempo_ejecucion <= tiempoMenor || tiempoMenor == -1){
                if (p.puedeInsertar(t, this.tiempoMaximoNoRefrigerado)){
                    procesadorMenor = p;
                    tiempoMenor = p.tiempo_ejecucion;
                }
            }
        }
        return procesadorMenor;
    }


    private void agregarTareaAProc(Procesador procesador, Tarea tarea) {
        solucion.get(procesador).add(tarea);
        procesador.incrementarTiempoEjecucion(tarea.getTiempo_ejecucion());
        if (tarea.esCritica()) {
            procesador.incrementarTareasCriticas();
        }
    }

    private Integer getTiempo(){
        Iterator<Procesador> itProcesadores = solucion.keySet().iterator();
        Integer maxTiempo = 0;

        while (itProcesadores.hasNext()) {
            Procesador nextProcesador = itProcesadores.next();
            Integer tiempoNextProcesador = nextProcesador.getTiempo_ejecucion();
            if (tiempoNextProcesador > maxTiempo)
                maxTiempo = tiempoNextProcesador;
        }
        return maxTiempo;
    }

    public HashMap<Procesador, LinkedList<Tarea>> getMejorAproximacion() {
        return this.solucion;
    }

    public void ImprimirResultado() {
        Iterator<Map.Entry<Procesador, LinkedList<Tarea>>> itProcesadores = solucion.entrySet().iterator();
        int iteracion = 1;

        System.out.println("===== Aproximacion GREEDY =====");
        while (itProcesadores.hasNext()) {
            Map.Entry<Procesador, LinkedList<Tarea>> entry = itProcesadores.next();
            Procesador nextProcesador = entry.getKey();
            LinkedList<Tarea> tareasAsignadas = entry.getValue();

            System.out.printf("Procesador %d:\n", iteracion);
            // Asumiendo que Procesador tiene un método getId()
            System.out.printf("  ID: %s\n", nextProcesador.getId());
            System.out.printf("  Tiempo Procesador: %s\n", nextProcesador.getTiempo_ejecucion());
            System.out.printf("  Refrigeracion: %s\n", nextProcesador.esta_refrigerado());
            System.out.println("  Tareas asignadas:");
            for (Tarea tarea : tareasAsignadas) {
                // Asumiendo que Tarea tiene un método getDescripcion()
                System.out.printf("    - %s\n", tarea);
            }
            iteracion++;
        }

        System.out.println("===== Tiempos =====");
        System.out.println(" Tiempo maximo no refrigerados: "+ this.tiempoMaximoNoRefrigerado);
        System.out.printf(" Tiempo máximo de aproximación greedy: %d\n", this.getTiempo());
    }
}