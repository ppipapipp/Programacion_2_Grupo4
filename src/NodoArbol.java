import java.time.LocalDateTime;

public class NodoArbol {
    int idDeposito;
    String nombre;
    boolean visitado;
    LocalDateTime fechaUltimaAuditoria;

    NodoArbol izquierdo;
    NodoArbol derecho;

    public NodoArbol(int idDeposito, String nombre) {
        this.idDeposito = idDeposito;
        this.nombre = nombre;
        this.visitado = false;
        this.fechaUltimaAuditoria = null;
        this.izquierdo = null;
        this.derecho = null;
    }

    @Override
    public String toString() {
        String audit = fechaUltimaAuditoria != null
                ? fechaUltimaAuditoria.toString()
                : "Sin auditar";
        return "Depósito #" + idDeposito + " [" + nombre + "]"
                + " | Visitado: " + (visitado ? "Sí" : "No")
                + " | Última auditoría: " + audit;
    }
}