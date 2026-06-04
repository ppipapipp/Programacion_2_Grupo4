import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Queue;

// Árbol Binario de Búsqueda para gestión de depósitos regionales
// insertar:         O(log n) promedio, O(n) peor caso
// buscar:           O(log n) promedio, O(n) peor caso
// auditarPostOrden: O(n)
// reportePorNivel:  O(n)

public class ABBDepositos {

    NodoArbol raiz;

    public ABBDepositos() {
        this.raiz = null;
    }

    // O(log n) promedio
    public void insertar(int id, String nombre) {
        raiz = insertarRec(raiz, id, nombre);
    }

    private NodoArbol insertarRec(NodoArbol nodo, int id, String nombre) {
        if (nodo == null) {
            return new NodoArbol(id, nombre);
        }
        if (id < nodo.idDeposito) {
            nodo.izquierdo = insertarRec(nodo.izquierdo, id, nombre);
        } else if (id > nodo.idDeposito) {
            nodo.derecho = insertarRec(nodo.derecho, id, nombre);
        } else {
            System.out.println("Ya existe un depósito con ID " + id);
        }
        return nodo;
    }

    // O(log n) promedio
    public NodoArbol buscar(int id) {
        return buscarRec(raiz, id);
    }

    private NodoArbol buscarRec(NodoArbol nodo, int id) {
        if (nodo == null) return null;
        if (id == nodo.idDeposito) return nodo;
        if (id < nodo.idDeposito) return buscarRec(nodo.izquierdo, id);
        return buscarRec(nodo.derecho, id);
    }

    // Auditoría post-orden: visita izquierdo → derecho → raíz
    // Marca como visitados solo los que no fueron auditados en los últimos 30 días
    // O(n)
    public void auditarPostOrden() {
        System.out.println("\n=== AUDITORÍA POST-ORDEN DE DEPÓSITOS ===");
        auditarRec(raiz);
        System.out.println("Auditoría completada.");
    }

    private void auditarRec(NodoArbol nodo) {
        if (nodo == null) return;

        auditarRec(nodo.izquierdo);
        auditarRec(nodo.derecho);

        LocalDateTime ahora = LocalDateTime.now();
        boolean necesitaAuditoria = nodo.fechaUltimaAuditoria == null
                || nodo.fechaUltimaAuditoria.isBefore(ahora.minusDays(30));

        if (necesitaAuditoria) {
            nodo.visitado = true;
            nodo.fechaUltimaAuditoria = ahora;
            System.out.println("  [AUDITADO] " + nodo);
        } else {
            System.out.println("  [OK] Depósito #" + nodo.idDeposito
                    + " auditado recientemente: " + nodo.fechaUltimaAuditoria);
        }
    }

    // Reporte por niveles usando BFS (recorrido en anchura)
    // O(n)
    public void reportePorNivel() {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        System.out.println("\n=== REPORTE DE DEPÓSITOS POR NIVEL ===");

        Queue<NodoArbol> cola = new LinkedList<>();
        cola.add(raiz);
        int nivel = 0;

        while (!cola.isEmpty()) {
            int cantEnNivel = cola.size();
            System.out.println("Nivel " + nivel + ":");

            for (int i = 0; i < cantEnNivel; i++) {
                NodoArbol actual = cola.poll();
                System.out.println("  " + actual);

                if (actual.izquierdo != null) cola.add(actual.izquierdo);
                if (actual.derecho != null) cola.add(actual.derecho);
            }

            nivel++;
        }
    }

    // Muestra un depósito específico por nivel (para coordinar inspecciones)
    // O(n)
    public void depositosEnNivel(int nivelBuscado) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return;
        }

        System.out.println("\n=== DEPÓSITOS EN NIVEL " + nivelBuscado + " ===");

        Queue<NodoArbol> cola = new LinkedList<>();
        cola.add(raiz);
        int nivel = 0;
        boolean encontrado = false;

        while (!cola.isEmpty() && nivel <= nivelBuscado) {
            int cantEnNivel = cola.size();

            if (nivel == nivelBuscado) {
                while (!cola.isEmpty()) {
                    NodoArbol actual = cola.poll();
                    System.out.println("  " + actual);
                    encontrado = true;
                }
                break;
            }

            for (int i = 0; i < cantEnNivel; i++) {
                NodoArbol actual = cola.poll();
                if (actual.izquierdo != null) cola.add(actual.izquierdo);
                if (actual.derecho != null) cola.add(actual.derecho);
            }

            nivel++;
        }

        if (!encontrado) {
            System.out.println("No hay depósitos en el nivel " + nivelBuscado);
        }
    }

    // Recorrido in-orden para listar todos los depósitos ordenados por ID
    // O(n)
    public void listarTodos() {
        System.out.println("\n=== DEPÓSITOS (ordenados por ID) ===");
        listarInOrden(raiz);
    }

    private void listarInOrden(NodoArbol nodo) {
        if (nodo == null) return;
        listarInOrden(nodo.izquierdo);
        System.out.println("  " + nodo);
        listarInOrden(nodo.derecho);
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}