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
        return new Paquete<>(id, contenido, peso, destino, urgente, false);
    }

    public static void main(String[] args) {
        PilaCamion<Paquete<String>> camion = new PilaCamion<>();
        ColaPrioridad<Paquete<String>> centro = new ColaPrioridad<>();
        Scanner sc = new Scanner(System.in);



        //NUEVO:
        int maxId = ManejoArchivos.cargarEnCola("src/inventario.json", centro);
        int idContador = maxId + 1;


        int opcion;
        do {
            System.out.println("\n=== Sistema de Logística ===");
            System.out.println("1. Cargar paquete al camion");
            System.out.println("2. Deshacer última carga (desapilar)");
            System.out.println("3. Ver cima del camion");
            System.out.println("4. Mostrar camion completo");
            System.out.println("5. Transferir paquete del camion al centro de distribución");
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
                    Paquete<String> cima = camion.cima();
                    if (cima == null) {
                        System.out.println("El camión está vacio");
                    }else {
                        System.out.println("Cima del camión: " + cima);
                    }
                    break;

                case 4:
                    camion.mostrar();
                    break;

                case 5:
                    if (!camion.estaVacia()) {
                        Paquete<String> pCentro = camion.desapilar();
                        centro.encolar(pCentro);
                        System.out.println("Paquete transferido del camión al centro de distribución: " + pCentro);
                        ManejoArchivos.guardarDesdeCola("src/inventario.json", centro); // ← NUEVO
                    } else {
                        System.out.println("No hay paquetes en el camión para transferir.");
                    }
                    break;

                case 6:
                    Paquete<String> procesado = centro.frente();
                    if (procesado != null) {
                        procesado.procesado = true;
                        System.out.println("Procesando paquete: " + procesado);
                        ManejoArchivos.guardarDesdeCola("src/inventario.json", centro);
                    } else {
                        System.out.println("No hay paquetes para procesar en el centro de distribución.");
                    }
                    break;

                case 7:
                    centro.mostrar();
                    break;
            }
        } while (opcion != 0);
            sc.close();
            System.out.println("Saliendo del sistema");
    }
}