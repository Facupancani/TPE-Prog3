import java.util.ArrayList;

public class greedy {

    
    public Procesador BuscarProcesadorMenor(ArrayList<Procesador> procesadores, Tarea t, int TiempoMaxNoRefrigerados){
        Procesador procesadorMenor = procesadores.get(0);
        int tiempoMenor = procesadores.get(0).tiempo_ejecucion;
        
        //O(n) n= largo ArrProcesadores
        for (Procesador p : procesadores){
            if( p.tiempo_ejecucion < tiempoMenor){
                if (p.puedeInsertar(t,TiempoMaxNoRefrigerados)){
                    procesadorMenor = p;
                }
            }
        }
        return procesadorMenor;
    }
}
