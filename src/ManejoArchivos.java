
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

// Carga O(n) donde n = cantidad de paquetes en el archivo
public class ManejoArchivos {

    public static void cargarEnCola(String ruta, ColaPrioridad<Paquete<String>> cola) {
        try {
            String contenido = new String(Files.readAllBytes(Paths.get(ruta)));
            JSONArray array = new JSONArray(contenido);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                int id           = obj.getInt("id");
                String tipo      = obj.getString("contenido");
                double peso      = obj.getDouble("peso");
                String destino   = obj.getString("destino");
                boolean urgente  = obj.getBoolean("urgente");
                Paquete<String> p = new Paquete<>(id, tipo, peso, destino, urgente);
                cola.encolar(p);
            }
            System.out.println("Inventario cargado correctamente.");
        } catch (Exception e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}