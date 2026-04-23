
import java.util.Scanner;

public class App {

    private static Paquete<String> crearPaquete(Scanner sc, int id) {
        System.out.print("Contenido: ");
        String contenido = sc.nextLine();
        System.out.print("Peso: ");
        double peso = sc.nextDouble();
        sc.nextLine();
        System.out.print("Destino: ");
        String destino = sc.nextLine();
        System.out.print("¿Urgente? (s/n): ");
        boolean urgente = sc.nextLine().equalsIgnoreCase("s");
        return new Paquete<>(id, contenido, peso, destino, urgente);
    }

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

            switch (opcion) {

                case 1:
                    Paquete<String> pCamion = crearPaquete(sc, idContador++);
                    camion.apilar(pCamion);
                    System.out.println("Paquete cargado en camión: " + pCamion);
                    break;

                case 2:
                    Paquete<String> removido = camion.desapilar();
                    if (removido != null)
                        System.out.println("Paquete descargado del camión: " + removido);
                    break;

                case 3:
                    System.out.println("Cima del camión: " + camion.cima());
                    break;

                case 4:
                    camion.mostrar();
                    break;

                case 5:
                    Paquete<String> pCentro = crearPaquete(sc, idContador++);
                    centro.encolar(pCentro);
                    System.out.println("Paquete encolado en centro logístico: " + pCentro);
                    break;

                case 6:
                    Paquete<String> procesado = centro.desencolar();
                    if (procesado != null)
                        System.out.println("Procesando paquete: " + procesado);
                    break;

                case 7:
                    centro.mostrar();
                    break;
            }
        } while (opcion != 0);
            sc.close();
    }
}