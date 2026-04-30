import java.util.Scanner;

public class App {

    private static Paquete<String> crearPaquete(Scanner sc, int id) {

        String input;
        boolean urgente;
        double peso;

        System.out.print("Contenido: ");
        String contenido = sc.nextLine();

        do {
            System.out.print("Peso: ");
            while (!sc.hasNextDouble()) {
                System.out.println("Error: ingresá un número válido.");
                sc.next();
                System.out.print("Peso: ");
            }
            peso = sc.nextDouble();
            sc.nextLine();
            if (peso <= 0) {
                System.out.println("El peso debe ser mayor a 0.");
            }
        } while (peso <= 0);

        System.out.print("Destino: ");
        String destino = sc.nextLine();

        do {
            System.out.print("¿Urgente? (s/n): ");
            input = sc.nextLine();
            if (!input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("n")) {
                System.out.println("Ingresá solo 's' o 'n'.");
            }
        } while (!input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("n"));
        urgente = input.equalsIgnoreCase("s");

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
            System.out.println("\nSISTEMA DE LOGÍSTICA ----------------------------------------");
            System.out.println("1. Cargar paquete al camion");
            System.out.println("2. Deshacer última carga (desapilar)");
            System.out.println("3. Ver cima del camion");
            System.out.println("4. Mostrar camion completo");
            System.out.println("5. Transferir paquete del camion al centro de distribución");
            System.out.println("6. Procesar siguiente paquete del centro");
            System.out.println("7. Mostrar centro de distribución");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            while (!sc.hasNextInt()) {
                System.out.println("Error: ingresá un número válido.");
                System.out.print("Opción: ");
                sc.next();
            }
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

                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 0);
            sc.close();
            System.out.println("Saliendo del sistema");
    }
}