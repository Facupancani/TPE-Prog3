import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader{

          /*
           * Procesadores.csv
                    <id_procesador>;<codigo_procesador>;<esta_refrigerado?>;<año_funcionamiento>
          Tareas.csv
                    <id_tarea>;<nombre_tarea>;<tiempo_ejecucion>;<es_critica?>;<nivel_prioridad>
           */

          // Convierte un archivo CSV a String
          public String CSVtoString(String rutaArchivo) {
                    StringBuilder contenido = new StringBuilder();

                    try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
                        String linea;
                        while ((linea = br.readLine()) != null) {
                            // Concatenar cada línea del archivo CSV
                            contenido.append(linea).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return contenido.toString();
          }


          // Toma la informacion de las tareas y los convierte en objetos almacenados en un arreglo
        public LinkedList<Tarea> CargaTareas(String tareas){
            LinkedList<Tarea> ArrTareas = new LinkedList<Tarea>();

                    String[] lineas = tareas.split("\\n");
                    
                    for (int linea = 0; linea < lineas.length; linea++) {
                        // Aca separa cada linea en sus partes correspondientes <id_tarea>;<nombre_tarea>;<tiempo_ejecucion>;<es_critica?>;<nivel_prioridad> Sin importar si se separan por , o ;
                              String[] partes = lineas[linea].split("[,;]");

                        //Asigno la informacion a variables
                              String id_tarea = partes[0];
                              String nombre_tarea = partes[1];
                              int tiempo_ejecucion = Integer.parseInt(partes[2]);
                              Boolean es_critica = Boolean.parseBoolean(partes[3]);
                              int nivel_prioridad = Integer.parseInt(partes[4]);

                        //Creo el objeto Tarea y lo inserto en el arreglo
                              Tarea t = new Tarea(id_tarea, nombre_tarea, tiempo_ejecucion, es_critica, nivel_prioridad);
                              ArrTareas.add(t);
                    }
          return ArrTareas;
          }

          // Toma la informacion de los procesadores y los convierte en objetos almacenados en un arreglo
        public LinkedList<Procesador> CargaProcesadores(String procesadores){
                    LinkedList<Procesador> ArrProcesadores = new LinkedList<Procesador>();

                    String[] lineas = procesadores.split("\\n");
                    
                    for (int linea = 0; linea < lineas.length; linea++) {
                        // Aca separa cada linea en sus partes correspondientes <id_procesador>;<codigo_procesador>;<esta_refrigerado?>;<año_funcionamiento> Sin importar si se separan por , o ;
                        String[] partes = lineas[linea].split("[,;]");
                
                        String id_procesador =  partes[0];
                        String codigo_procesador = partes[1];
                        Boolean esta_refrigerado = Boolean.parseBoolean(partes[2]);
                        int año_funcionamiento = Integer.parseInt(partes[3]);

                        Procesador p = new Procesador(id_procesador, codigo_procesador, esta_refrigerado, año_funcionamiento);
                        ArrProcesadores.add(p);
                    }
          return ArrProcesadores;
          }



          
         /*  public class Main {
            public static void main(String[] args) {
                Reader reader = new Reader();
                
                // Carga y muestra tareas
                String contenidoTareas = reader.CSVtoString("tareas.csv");
                ArrayList<Tarea> tareas = reader.CargaTareas(contenidoTareas);
                System.out.println("Tareas:");
                mostrarTareas(tareas);
                
                
                // Carga y muestra procesadores
                String contenidoProcesadores = reader.CSVtoString("procesadores.csv");
                ArrayList<Procesador> procesadores = reader.CargaProcesadores(contenidoProcesadores);
                System.out.println("\nProcesadores:");
                mostrarProcesadores(procesadores);
            }
            
            public static void mostrarTareas(ArrayList<Tarea> tareas) {
                for (Tarea tarea : tareas) {
                    System.out.println(tarea.toString());
                }
            }
            
            public static void mostrarProcesadores(ArrayList<Procesador> procesadores) {
                for (Procesador procesador : procesadores) {
                    System.out.println(procesador.toString());
                }
            }
        }*/
        
}
