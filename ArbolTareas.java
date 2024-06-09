public class ArbolTareas {
          private nodoTarea raiz;

          public ArbolTareas() {
                    this.raiz = null;
          }

          public void insertar(Tarea T) {
                    if (this.raiz == null)
                              this.raiz = new nodoTarea(T);
                    else
                              this.raiz.insertar(this.raiz, T);
          }

          public nodoTarea getRaiz() {
                    return raiz;
          }

}
