
// Pila dinámica LIFO para carga de camiones
// apilar:    O(1)
// desapilar: O(1)  ← permite "deshacer" última carga
// cima:      O(1)
// estaVacia: O(1)

public class PilaCamion<T> {
    Nodo<T> cima;
    int contador;

    public PilaCamion() {
        this.cima = null;
        this.contador = 0;
    }

    // O(1)
    public void apilar(T elemento) {
        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.siguiente = cima;
        cima = nuevo;
        contador++;
    }

    // O(1) - deshace la última carga
    public T desapilar() {
        if (estaVacia()) {
            System.out.println("El camión está vacío");
            return null;
        }
        T dato = cima.dato;
        cima = cima.siguiente;
        contador--;
        return dato;
    }

    // O(1)
    public T cima() {
        if (estaVacia()) {
            return null;
        }
        return cima.dato;
    }

    // O(1)
    public boolean estaVacia() {
        return cima == null;
    }

    // O(n)
    public void mostrar() {
        if (estaVacia()) {
            System.out.println("el camion esta vacio");
        }
        Nodo<T> actual = cima;
        while (actual != null) {
            System.out.println(actual.dato);
            actual = actual.siguiente;
        }
    }
}