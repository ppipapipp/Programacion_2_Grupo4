import java.util.*;

// Grafo de depósitos modelado con lista de adyacencia
// agregarDeposito:  O(1)
// agregarRuta:      O(1)
// distanciaMinima:  O((V + E) log V) con Dijkstra
// mostrarRed:       O(V + E)

public class GrafoDepositos {

    // Nodo interno para lista de adyacencia
    private static class Arista {
        int destino;
        int distancia; // en km

        Arista(int destino, int distancia) {
            this.destino = destino;
            this.distancia = distancia;
        }
    }

    // id depósito → lista de aristas
    private Map<Integer, List<Arista>> adyacencia;
    // id depósito → nombre (para mostrar)
    private Map<Integer, String> nombres;

    public GrafoDepositos() {
        this.adyacencia = new HashMap<>();
        this.nombres = new HashMap<>();
    }

    // O(1)
    public void agregarDeposito(int id, String nombre) {
        if (!adyacencia.containsKey(id)) {
            adyacencia.put(id, new ArrayList<>());
            nombres.put(id, nombre);
        } else {
            System.out.println("El depósito " + id + " ya existe en el grafo.");
        }
    }

    // Agrega ruta bidireccional - O(1)
    public void agregarRuta(int origen, int destino, int distanciaKm) {
        if (!adyacencia.containsKey(origen) || !adyacencia.containsKey(destino)) {
            System.out.println("Error: uno o ambos depósitos no existen.");
            return;
        }
        adyacencia.get(origen).add(new Arista(destino, distanciaKm));
        adyacencia.get(destino).add(new Arista(origen, distanciaKm));
    }

    // Dijkstra para encontrar la ruta más corta entre dos depósitos
    // O((V + E) log V)
    public void distanciaMinima(int origen, int destino) {
        if (!adyacencia.containsKey(origen) || !adyacencia.containsKey(destino)) {
            System.out.println("Uno o ambos depósitos no existen en la red.");
            return;
        }

        Map<Integer, Integer> distancias = new HashMap<>();
        Map<Integer, Integer> anterior = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        // Inicializar distancias en infinito
        for (int id : adyacencia.keySet()) {
            distancias.put(id, Integer.MAX_VALUE);
        }
        distancias.put(origen, 0);
        pq.add(new int[]{origen, 0});

        while (!pq.isEmpty()) {
            int[] actual = pq.poll();
            int idActual = actual[0];
            int distActual = actual[1];

            // Si ya encontramos una ruta mejor, salteamos
            if (distActual > distancias.get(idActual)) continue;

            for (Arista arista : adyacencia.get(idActual)) {
                int nuevaDist = distancias.get(idActual) + arista.distancia;
                if (nuevaDist < distancias.get(arista.destino)) {
                    distancias.put(arista.destino, nuevaDist);
                    anterior.put(arista.destino, idActual);
                    pq.add(new int[]{arista.destino, nuevaDist});
                }
            }
        }

        // Mostrar resultado
        if (distancias.get(destino) == Integer.MAX_VALUE) {
            System.out.println("No hay ruta entre depósito #" + origen + " y depósito #" + destino);
            return;
        }

        System.out.println("\n=== RUTA MÍNIMA ===");
        System.out.println("Origen:  #" + origen + " [" + nombres.get(origen) + "]");
        System.out.println("Destino: #" + destino + " [" + nombres.get(destino) + "]");
        System.out.println("Distancia total: " + distancias.get(destino) + " km");

        // Reconstruir el camino
        List<Integer> camino = new ArrayList<>();
        int paso = destino;
        while (anterior.containsKey(paso)) {
            camino.add(0, paso);
            paso = anterior.get(paso);
        }
        camino.add(0, origen);

        System.out.print("Camino: ");
        for (int i = 0; i < camino.size(); i++) {
            int id = camino.get(i);
            System.out.print("#" + id + " [" + nombres.get(id) + "]");
            if (i < camino.size() - 1) System.out.print(" → ");
        }
        System.out.println();
    }

    // Muestra todos los depósitos y sus conexiones - O(V + E)
    public void mostrarRed() {
        if (adyacencia.isEmpty()) {
            System.out.println("La red de depósitos está vacía.");
            return;
        }

        System.out.println("\n=== RED DE DEPÓSITOS ===");
        for (Map.Entry<Integer, List<Arista>> entry : adyacencia.entrySet()) {
            int id = entry.getKey();
            System.out.print("  #" + id + " [" + nombres.get(id) + "] → ");
            if (entry.getValue().isEmpty()) {
                System.out.print("(sin conexiones)");
            } else {
                List<Arista> aristas = entry.getValue();
                for (int i = 0; i < aristas.size(); i++) {
                    Arista a = aristas.get(i);
                    System.out.print("#" + a.destino + " (" + a.distancia + " km)");
                    if (i < aristas.size() - 1) System.out.print(", ");
                }
            }
            System.out.println();
        }
    }

    public boolean existeDeposito(int id) {
        return adyacencia.containsKey(id);
    }

    public boolean estaVacio() {
        return adyacencia.isEmpty();
    }
}