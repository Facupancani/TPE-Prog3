import java.util.*;

public class Backtracking {
    private HashMap<Procesador, LinkedList<Tarea>> solucion = new LinkedHashMap<>();
    private LinkedList<Procesador> procesadores;
    private LinkedList<Tarea> colaTareas;
    private int cantEstadosGenerados = 0;
    private int maxTiempoActual = 0;
    private int tiempoMaximoNoRefrigerado;

    public Backtracking(LinkedList<Procesador> procesadores, LinkedList<Tarea> tareas, int tiempoMax) {
        this.colaTareas = tareas;
        this.procesadores = procesadores;
        this.tiempoMaximoNoRefrigerado = tiempoMax;
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
        if (colaTareas.isEmpty()){
            Integer tiempoParcial = getMaxTiempo(solucionParcial);
            if (solucionParcialEsMejorQueSolucion(tiempoParcial)) {
                reemplazarMejorSolucion(solucionParcial, tiempoParcial);
            }
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

    private void agregarTareaAProc(Procesador procesador, Tarea tarea, HashMap<Procesador, LinkedList<Tarea>> solucionParcial) {
        solucionParcial.get(procesador).add(tarea);
        procesador.incrementarTiempoEjecucion(tarea.getTiempo_ejecucion());
        if (tarea.esCritica()){
            procesador.incrementarTareasCriticas();
        }
    }

    private void sacarTareaAProc(Procesador procesador, Tarea tarea, HashMap<Procesador, LinkedList<Tarea>> solucionParcial) {
        solucionParcial.get(procesador).remove(tarea);
        procesador.decrementarTiempoEjecucion(tarea.getTiempo_ejecucion());
        if (tarea.esCritica()){
            procesador.decrementarTareasCriticas();
        }
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

    private boolean solucionParcialEsMejorQueSolucion(Integer maxTiempoParcial) {
        System.out.println(maxTiempoParcial +  " < " + this.maxTiempoActual);
        return maxTiempoParcial < this.maxTiempoActual || this.maxTiempoActual == 0;
    }

    private void reemplazarMejorSolucion(HashMap<Procesador, LinkedList<Tarea>> solucionParcial, Integer maxTiempoParcial) {
        for (Procesador procesador : procesadores) {
            LinkedList<Tarea> tareasSolucionParcial = solucionParcial.get(procesador);
            solucion.get(procesador).clear();
            solucion.get(procesador).addAll(tareasSolucionParcial);
        }
        this.maxTiempoActual = maxTiempoParcial;
    }

    public Integer getTiempoMejorSolucion() {
        if (solucionVacia())
            return -1;
        return maxTiempoActual;
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
}