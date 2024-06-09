import java.util.ArrayList;
import java.util.Comparator;

public class Procesador {
          int id_procesador;
          int codigo_procesador;
          Boolean esta_refrigerado;
          int año_funcionamiento;
          ArrayList<Tarea> tareas_asignadas;
          int cantCriticas = 0;
          int tiempo_ejecucion = 0;
      

        public Procesador(int id, int codigo, Boolean refrigerado, int año) {
              this.id_procesador = id;
              this.codigo_procesador = codigo;
              this.esta_refrigerado = refrigerado;
              this.año_funcionamiento = año;
              this.tareas_asignadas = new ArrayList<Tarea>();
          }
      
          @Override
          public String toString() {
              return "Procesador{" +
                      "id_procesador=" + id_procesador +
                      ", codigo_procesador=" + codigo_procesador +
                      ", esta_refrigerado=" + esta_refrigerado +
                      ", año_funcionamiento=" + año_funcionamiento +
                      '}';
          }
          
          public static Comparator<Procesador> RefrigeradoComparator = new Comparator<Procesador>() {
            @Override
            public int compare(Procesador p1, Procesador p2) {
                // Si p1 no está refrigerado y p2 sí, p1 debería ir antes (-1)
                // Si p1 está refrigerado y p2 no, p1 debería ir después (1)
                // Si ambos tienen el mismo valor de refrigerado, son iguales (0)
                return Boolean.compare(p1.esta_refrigerado, p2.esta_refrigerado);
            }
        };

        public int getId() {
            return id_procesador;
        }

        public int getCodigo() {
            return codigo_procesador;
        }

        public Boolean esta_refrigerado() {
            return esta_refrigerado;
        }

        public int getAño() {
            return año_funcionamiento;
        }

        public int getTiempo_ejecucion() {
            return tiempo_ejecucion;
        }
        public int getCantTareasCriticas(){
            return this.cantCriticas;
        }


        public boolean puedeInsertar(Tarea tarea, int tiempoMax){
            if (tarea.es_critica && this.cantCriticas >= 2) {
                return false;
            }
            if(!this.esta_refrigerado && ((tarea.getTiempo_ejecucion() + this.tiempo_ejecucion) > tiempoMax)){
                return false;
            }
            else return true;
        }

        public void insertar(Tarea t){
            if (t.es_critica) {
                this.cantCriticas ++;
            }
            this.tiempo_ejecucion += t.getTiempo_ejecucion();
            this.tareas_asignadas.add((t));
        }


    public void incrementarTiempoEjecucion(int tiempoEjecucion) {
        this.tiempo_ejecucion = this.getTiempo_ejecucion() + tiempoEjecucion;
    }

    public void decrementarTiempoEjecucion(int tiempoEjecucion) {
        this.tiempo_ejecucion = this.getTiempo_ejecucion() - tiempoEjecucion;
    }

    public void incrementarTareasCriticas() {
        this.cantCriticas++;
    }

    public void decrementarTareasCriticas() {
        this.cantCriticas--;
    }

    }
      