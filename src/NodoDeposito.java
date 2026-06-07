import java.time.LocalDateTime;

public class NodoDeposito {
    // Datos del depósito
    private int id;                 // Clave para el ABB
    private String nombre;
    
    // Atributos requeridos por la consigna
    private boolean visitado;
    private LocalDateTime fechaUltimaAuditoria;
    
    // Enlaces del árbol binario
    private NodoDeposito izquierdo;
    private NodoDeposito derecho;
    
    // Constructor
    public NodoDeposito(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.visitado = false;
        this.fechaUltimaAuditoria = null;   // Inicialmente no auditado
        this.izquierdo = null;
        this.derecho = null;
    }
    
    // Getters y Setters (necesarios para el ABB y futuras operaciones)
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public boolean isVisitado() { return visitado; }
    public void setVisitado(boolean visitado) { this.visitado = visitado; }
    
    public LocalDateTime getFechaUltimaAuditoria() { return fechaUltimaAuditoria; }
    public void setFechaUltimaAuditoria(LocalDateTime fecha) { this.fechaUltimaAuditoria = fecha; }
    
    public NodoDeposito getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoDeposito izquierdo) { this.izquierdo = izquierdo; }
    
    public NodoDeposito getDerecho() { return derecho; }
    public void setDerecho(NodoDeposito derecho) { this.derecho = derecho; }
    
    @Override
    public String toString() {
        return "Depósito [ID=" + id + ", nombre=" + nombre + 
               ", visitado=" + visitado + 
               ", última auditoría=" + (fechaUltimaAuditoria != null ? fechaUltimaAuditoria : "Nunca") + "]";
    }
}