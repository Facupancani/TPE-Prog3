public class Procesador {
          int id_procesador;
          int codigo_procesador;
          Boolean esta_refrigerado;
          int año_funcionamiento;
      
          public Procesador(int id, int codigo, Boolean refrigerado, int año) {
              this.id_procesador = id;
              this.codigo_procesador = codigo;
              this.esta_refrigerado = refrigerado;
              this.año_funcionamiento = año;
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
      }
      