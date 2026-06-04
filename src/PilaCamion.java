// Pila dinámica LIFO para carga de camiones
// apilar:    O(1) de insert, O(n) para validar peso
// desapilar: O(1)  ← permite "deshacer" última carga
// cima:      O(1)
// estaVacia: O(1)

public class PilaCamion<T extends Paquete<?>> {
    Nodo<T> cima;
    int contador;
    int capacidadMaxima;
    double pesoMaximoTotal;

    public PilaCamion(int capacidadMaxima, double pesoMaximoTotal) {
        this.cima = null;
        this.contador = 0;
        this.capacidadMaxima = capacidadMaxima;
        this.pesoMaximoTotal = pesoMaximoTotal;
    }

    // Calcula el peso acumulado en la pila - O(n)
    private double calcularPesoAcumulado() {
        double total = 0.0;
        Nodo<T> actual = cima;
        while (actual != null) {
            total += actual.dato.peso;
            actual = actual.siguiente;
        }
        return total;
    }

    // O(n) - valida capacidad en cantidad Y peso máximo
    public boolean apilar(T elemento) {
        if (contador >= capacidadMaxima) {
            System.out.println("El camión está lleno (límite de cantidad alcanzado)");
            return false;
        }

        double pesoActual = calcularPesoAcumulado();
        if (pesoActual + elemento.peso > pesoMaximoTotal) {
            System.out.println("No se puede apilar: peso total (" + (pesoActual + elemento.peso) + "kg) superaría el límite (" + pesoMaximoTotal + "kg)");
            return false;
        }

        Nodo<T> nuevo = new Nodo<>(elemento);
        nuevo.siguiente = cima;
        cima = nuevo;
        contador++;
        return true;
    }

    // O(1) - deshace la última carga y libera capacidad
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

    // Getter para peso actual
    public double getPesoActual() {
        return calcularPesoAcumulado();
    }
}
