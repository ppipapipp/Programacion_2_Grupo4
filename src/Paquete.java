public class Paquete<T> {
    int id;
    T contenido;
    double peso;
    String destino;
    boolean urgente;
    boolean procesado;

    // 🔥 contador global
    private static int totalPaquetesCreados = 0;

    public Paquete(int id, T contenido, double peso, String destino, boolean urgente, boolean procesado) {
        this.id = id;
        this.contenido = contenido;
        this.peso = peso;
        this.destino = validarDestino(destino);
        this.urgente = urgente;
        this.procesado = procesado;

        totalPaquetesCreados++; // 🔥 aumenta cada vez que se crea uno
    }

    // 🔥 getter estático
    public static int getTotalPaquetesCreados() {
        return totalPaquetesCreados;
    }

    private static String validarDestino(String destino) {
        if (destino == null || destino.trim().isEmpty()) {
            throw new IllegalArgumentException("El destino no puede ser null, vacío ni solo espacios.");
        }
        return destino;
    }

    // O(1)
    public boolean esPrioritario() {
        return urgente || peso > 50 || destino.toUpperCase().contains("CAPITAL");
    }

    @Override
    public String toString() {
        return "ID:" + id + " | " + contenido + " | " + peso + "kg | " + destino + (urgente ? " | URGENTE" : "") + (procesado ? " | PROCESADO" : "") ;
    }

}