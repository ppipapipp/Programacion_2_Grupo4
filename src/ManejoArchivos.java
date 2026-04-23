import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ManejoArchivos {

    public static void cargarEnCola(String ruta, ColaPrioridad<Paquete<String>> cola) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(ruta)));
            JSONArray array = new JSONArray(contenido);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id          = obj.getInt("id");
                String tipo     = obj.getString("contenido");
                double peso     = obj.getDouble("peso");
                String destino  = obj.getString("destino");
                boolean urgente = obj.getBoolean("urgente");
                cola.encolar(new Paquete<>(id, tipo, peso, destino, urgente));
            }
            System.out.println("Inventario cargado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    // ← NUEVO: recorre ambas colas y serializa todo al archivo
    public static void guardarDesdeCola(String ruta, ColaPrioridad<Paquete<String>> cola) {
        try {
            JSONArray array = new JSONArray();

            // Recorremos sin desencolar — directamente por los nodos internos
            Nodo<Paquete<String>> actual = cola.frentePrio;
            while (actual != null) {
                array.put(paqueteAJson(actual.dato));
                actual = actual.siguiente;
            }
            actual = cola.frenteStd;
            while (actual != null) {
                array.put(paqueteAJson(actual.dato));
                actual = actual.siguiente;
            }

            Files.write(Paths.get(ruta), array.toString(2).getBytes());
            System.out.println("Inventario guardado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }

    private static JSONObject paqueteAJson(Paquete<String> p) {
        JSONObject obj = new JSONObject();
        obj.put("id",        p.id);
        obj.put("contenido", p.contenido);
        obj.put("peso",      p.peso);
        obj.put("destino",   p.destino);
        obj.put("urgente",   p.urgente);
        return obj;
    }
}