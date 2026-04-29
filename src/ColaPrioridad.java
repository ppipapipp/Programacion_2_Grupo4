// Cola con dos carriles internos: prioritarios y estándar
// encolar:   O(1)
// desencolar: O(1)
// La cola prioritaria siempre se vacía antes que la estándar

public class ColaPrioridad<T extends Paquete<?>> {
    Nodo<T> frentePrio;
    Nodo<T> fondoPrio;
    Nodo<T> frenteStd;
    Nodo<T> fondoStd;

    public ColaPrioridad() {
        this.frentePrio = null;
        this.fondoPrio  = null;
        this.frenteStd  = null;
        this.fondoStd   = null;
    }

    // O(1)
    public void encolar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        if (elemento.esPrioritario()) {
            if (frentePrio == null) {
                frentePrio = nuevo;
                fondoPrio  = nuevo;
            } else {
                fondoPrio.siguiente = nuevo;
                fondoPrio = nuevo;
            }
        } else {
            if (frenteStd == null) {
                frenteStd = nuevo;
                fondoStd  = nuevo;
            } else {
                fondoStd.siguiente = nuevo;
                fondoStd = nuevo;
            }
        }
    }

    // O(1) - los prioritarios salen primero
    public T desencolar() {
        if (frentePrio != null) {
            T dato = frentePrio.dato;
            frentePrio = frentePrio.siguiente;
            if (frentePrio == null) fondoPrio = null;
            return dato;
        }
        if (frenteStd != null) {
            T dato = frenteStd.dato;
            frenteStd = frenteStd.siguiente;
            if (frenteStd == null) fondoStd = null;
            return dato;
        }
        System.out.println("Centro de distribución vacío");
        return null;
    }

    // O(1)
    public boolean estaVacia() {
        return frentePrio == null && frenteStd == null;
    }

    // O(1) - pero ahora O(n) para saltar procesados
    public T frente() {
        Nodo<T> actual = frentePrio;
        while (actual != null) {
            if (!actual.dato.procesado) return actual.dato;
            actual = actual.siguiente;
        }
        actual = frenteStd;
        while (actual != null) {
            if (!actual.dato.procesado) return actual.dato;
            actual = actual.siguiente;
        }
        return null;
    }

    // O(n)
    public void mostrar() {
        System.out.println("-- Prioritarios --");
        Nodo<T> actual = frentePrio;
        while (actual != null) {
            if (!actual.dato.procesado) System.out.println(actual.dato);
            actual = actual.siguiente;
        }
        System.out.println("-- Estándar --");
        actual = frenteStd;
        while (actual != null) {
            if (!actual.dato.procesado) System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }
}