import java.time.LocalDateTime;

public class ABB {
    private NodoDeposito raiz;
    
    public ABB() {
        this.raiz = null;
    }
    
    // Insertar (O(log n) promedio)
    public void insertar(int id, String nombre) {
        NodoDeposito nuevo = new NodoDeposito(id, nombre);
        if (raiz == null) {
            raiz = nuevo;
        } else {
            insertarRec(raiz, nuevo);
        }
    }
    
    private void insertarRec(NodoDeposito actual, NodoDeposito nuevo) {
        if (nuevo.getId() < actual.getId()) {
            if (actual.getIzquierdo() == null) {
                actual.setIzquierdo(nuevo);
            } else {
                insertarRec(actual.getIzquierdo(), nuevo);
            }
        } else if (nuevo.getId() > actual.getId()) {
            if (actual.getDerecho() == null) {
                actual.setDerecho(nuevo);
            } else {
                insertarRec(actual.getDerecho(), nuevo);
            }
        } else {
            System.out.println("Ya existe un depósito con ID " + nuevo.getId() + ". No se insertó.");
        }
    }
    
    // Buscar (O(log n) promedio)
    public NodoDeposito buscar(int id) {
        return buscarRec(raiz, id);
    }
    
    private NodoDeposito buscarRec(NodoDeposito actual, int id) {
        if (actual == null) return null;
        if (id == actual.getId()) return actual;
        if (id < actual.getId()) return buscarRec(actual.getIzquierdo(), id);
        else return buscarRec(actual.getDerecho(), id);
    }
    
    public boolean estaVacio() {
        return raiz == null;
    }
    
    public void mostrarInorden() {
        System.out.println("=== Depósitos ordenados por ID ===");
        mostrarInordenRec(raiz);
        System.out.println();
    }
    
    private void mostrarInordenRec(NodoDeposito nodo) {
        if (nodo != null) {
            mostrarInordenRec(nodo.getIzquierdo());
            System.out.println(nodo);
            mostrarInordenRec(nodo.getDerecho());
        }
    }
    
    public NodoDeposito getRaiz() {
        return raiz;
    }
}