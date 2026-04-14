
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        PilaCamion<Paquete<String>> camion = new PilaCamion<>();
        ColaPrioridad<Paquete<String>> centro = new ColaPrioridad<>();
        Scanner sc = new Scanner(System.in);
        int idContador = 100;

        ManejoArchivos.cargarEnCola("src/main/resources/inventario.json", centro);


        int opcion;
        do {
            System.out.println("\n=== Sistema de Logística ===");
            System.out.println("1. Cargar paquete al camion");
            System.out.println("2. Deshacer última carga (desapilar)");
            System.out.println("3. Ver cima del camion");
            System.out.println("4. Mostrar camion completo");
            System.out.println("5. Agregar paquete al centro de distribución");
            System.out.println("6. Procesar siguiente paquete del centro");
            System.out.println("7. Mostrar centro de distribución");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                System.out.print("Contenido: ");
                String contenido = sc.nextLine();
                System.out.print("Peso: ");
                double peso = sc.nextDouble();
                sc.nextLine();
                System.out.print("Destino: ");
                String destino = sc.nextLine();
                System.out.print("¿Urgente? (s/n): ");
                boolean urgente = sc.nextLine().equalsIgnoreCase("s");
                Paquete<String> p = new Paquete<>(idContador++, contenido, peso, destino, urgente);
                camion.apilar(p);
                System.out.println("Paquete cargado: " + p);

            } else if (opcion == 2) {
                Paquete<String> removido = camion.desapilar();
                if (removido != null) System.out.println("Deshecho: " + removido);

            } else if (opcion == 3) {
                System.out.println("Cima: " + camion.cima());

            } else if (opcion == 4) {
                camion.mostrar();

            } else if (opcion == 5) {
                System.out.print("Contenido: ");
                String contenido = sc.nextLine();
                System.out.print("Peso: ");
                double peso = sc.nextDouble();
                sc.nextLine();
                System.out.print("Destino: ");
                String destino = sc.nextLine();
                System.out.print("¿Urgente? (s/n): ");
                boolean urgente = sc.nextLine().equalsIgnoreCase("s");
                Paquete<String> p = new Paquete<>(idContador++, contenido, peso, destino, urgente);
                centro.encolar(p);
                System.out.println("Paquete encolado: " + p);

            } else if (opcion == 6) {
                Paquete<String> procesado = centro.desencolar();
                if (procesado != null) System.out.println("Procesando: " + procesado);

            } else if (opcion == 7) {
                centro.mostrar();
            }

        } while (opcion != 0);

        sc.close();
    }
}