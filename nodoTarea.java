import java.util.ArrayList;

public class nodoTarea {
    Tarea tarea;
    nodoTarea anterior;
    nodoTarea siguiente;

    public nodoTarea(Tarea t) {
        this.tarea = t;
        this.anterior = null;
        this.siguiente = null;
    }

    public Tarea getTarea() {
        return tarea;
    }

    public nodoTarea getAnterior() {
        return anterior;
    }

    public nodoTarea getSiguiente() {
        return siguiente;
    }

    public void setAnterior(nodoTarea anterior) {
        this.anterior = anterior;
    }

    public void setSiguiente(nodoTarea siguiente) {
        this.siguiente = siguiente;
    }

    public void insertar(nodoTarea nodo, Tarea tarea) {
        if (nodo.getTarea().getNivel_prioridad() >= tarea.getNivel_prioridad()) {
            if (nodo.getAnterior() == null) {
                nodoTarea temp = new nodoTarea(tarea);
                nodo.setAnterior(temp);
            } else insertar(nodo.getAnterior(), tarea);
        } else if (nodo.getTarea().getNivel_prioridad() < tarea.getNivel_prioridad()) {
            if (nodo.getSiguiente() == null) {
                nodoTarea temp = new nodoTarea(tarea);
                nodo.setSiguiente(temp);
            } else insertar(nodo.getSiguiente(), tarea);
        }
    }

    public ArrayList<Tarea> listarTareasPrio(nodoTarea nodo, ArrayList<Tarea> listado, int prioridadInferior, int prioridadSuperior) {
        if (nodo != null) {
            // Verificar si la tarea actual está dentro del rango de prioridad
            int prioridadActual = nodo.getTarea().getNivel_prioridad();
            if (prioridadActual >= prioridadInferior && prioridadActual <= prioridadSuperior) {
                listado.add(nodo.getTarea());
            }

            // Llamar recursivamente para los nodos siguiente y anterior
            listarTareasPrio(nodo.getSiguiente(), listado, prioridadInferior, prioridadSuperior);
            listarTareasPrio(nodo.getAnterior(), listado, prioridadInferior, prioridadSuperior);
        }
        return listado;
    }
}
