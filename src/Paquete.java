public class Paquete<T> {
    int id;
    T contenido;
    double peso;
    String destino;
    boolean urgente;
    boolean procesado;

    public Paquete(int id, T contenido, double peso, String destino, boolean urgente, boolean procesado) {
        this.id = id;
        this.contenido = contenido;
        this.peso = peso;
        this.destino = destino;
        this.urgente = urgente;
        this.procesado = procesado;
    }

    // O(1)
    public boolean esPrioritario() {
        return urgente || peso > 50;
    }

    @Override
    public String toString() {
        return "ID:" + id + " | " + contenido + " | " + peso + "kg | " + destino + (urgente ? " | URGENTE" : "") + (procesado ? " | PROCESADO" : "") ;
    }
}