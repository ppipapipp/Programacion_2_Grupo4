import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class App {

    static Map<Integer, Paquete<String>> indice = new HashMap<>();

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

    public static Paquete<String> buscarPorId(int id) {
        return indice.get(id);
    }

    public static void modificarPaquete(int id, String destino, boolean urgente) {
        Paquete<String> p = indice.get(id);

        if (p != null) {
            p.destino = destino;
            p.urgente = urgente;
            System.out.println("Paquete actualizado: " + p);
        } else {
            System.out.println("No existe paquete con ese ID");
        }
    }

    private static int leerOpcion(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.println("Error: ingresá un número válido.");
            System.out.print("Opción: ");
            sc.next();
        }
        int op = sc.nextInt();
        sc.nextLine();
        return op;
    }

    // ── Submenú 1: Gestión de carga ──────────────────────────────────────────

    private static void menuCarga(Scanner sc, PilaCamion<Paquete<String>> camion,
                                  ColaPrioridad<Paquete<String>> centro, int[] idContador) {
        int opcion;
        do {
            System.out.println("\nGESTIÓN DE CARGA ------------------------------------");
            System.out.println("1.  Cargar paquete al camión");
            System.out.println("2.  Deshacer última carga (desapilar)");
            System.out.println("3.  Ver cima del camión");
            System.out.println("4.  Mostrar camión completo");
            System.out.println("5.  Transferir paquete al centro de distribución");
            System.out.println("6.  Procesar siguiente paquete del centro");
            System.out.println("7.  Mostrar centro de distribución");
            System.out.println("8.  Buscar paquete por ID");
            System.out.println("0.  Volver al menú principal");
            System.out.print("Opción: ");

            opcion = leerOpcion(sc);

            switch (opcion) {

                case 1:
                    if (indice.containsKey(idContador[0])) {
                        System.out.println("Error: el ID ya está en uso.");
                        break;
                    }

                    Paquete<String> pCamion = crearPaquete(sc, idContador[0]);
                    boolean agregado = camion.apilar(pCamion);

                    if (agregado) {
                        indice.put(pCamion.id, pCamion);
                        idContador[0]++;
                        System.out.println("Paquete cargado en camión: " + pCamion);
                    } else {
                        System.out.println("No se pudo cargar el paquete (camión lleno).");
                    }
                    break;

                case 2:
                    Paquete<String> removido = camion.desapilar();
                    if (removido != null)
                        System.out.println("Paquete descargado del camión: " + removido);
                    break;

                case 3:
                    Paquete<String> cima = camion.cima();
                    if (cima == null) {
                        System.out.println("El camión está vacío.");
                    } else {
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
                        System.out.println("Paquete transferido al centro: " + pCentro);
                        ManejoArchivos.guardarDesdeCola("src/inventario.json", centro);
                    } else {
                        System.out.println("No hay paquetes en el camión.");
                    }
                    break;

                case 6:
                    Paquete<String> procesado = centro.frente();
                    if (procesado != null) {
                        procesado.procesado = true;
                        System.out.println("Procesando paquete: " + procesado);
                        ManejoArchivos.guardarDesdeCola("src/inventario.json", centro);
                    } else {
                        System.out.println("No hay paquetes para procesar.");
                    }
                    break;

                case 7:
                    centro.mostrar();
                    break;

                case 8:
                    System.out.print("Ingrese ID a buscar: ");
                    int idBuscar = leerOpcion(sc);

                    Paquete<String> encontrado = buscarPorId(idBuscar);
                    if (encontrado != null) {
                        System.out.println("Paquete encontrado: " + encontrado);
                    } else {
                        System.out.println("No existe un paquete con ese ID.");
                    }
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ── Submenú 2: Gestión de depósitos ─────────────────────────────────────

    private static void menuDepositos(Scanner sc, ABBDepositos abb, GrafoDepositos grafo) {
        int opcion;
        do {
            System.out.println("\nGESTIÓN DE DEPÓSITOS --------------------------------");
            System.out.println("1.  Agregar depósito");
            System.out.println("2.  Buscar depósito por ID");
            System.out.println("3.  Listar todos los depósitos (in-orden)");
            System.out.println("4.  Auditar depósitos (post-orden)");
            System.out.println("5.  Reporte de depósitos por nivel");
            System.out.println("6.  Ver depósitos de un nivel específico");
            System.out.println("0.  Volver al menú principal");
            System.out.print("Opción: ");

            opcion = leerOpcion(sc);

            switch (opcion) {

                case 1:
                    System.out.print("ID del depósito: ");
                    int idNuevo = leerOpcion(sc);
                    System.out.print("Nombre del depósito: ");
                    String nombreNuevo = sc.nextLine();

                    abb.insertar(idNuevo, nombreNuevo);
                    grafo.agregarDeposito(idNuevo, nombreNuevo);
                    System.out.println("Depósito agregado.");
                    break;

                case 2:
                    System.out.print("ID del depósito a buscar: ");
                    int idABB = leerOpcion(sc);

                    NodoArbol dep = abb.buscar(idABB);
                    if (dep != null) {
                        System.out.println("Depósito encontrado: " + dep);
                    } else {
                        System.out.println("No existe un depósito con ese ID.");
                    }
                    break;

                case 3:
                    abb.listarTodos();
                    break;

                case 4:
                    if (abb.estaVacio()) {
                        System.out.println("No hay depósitos cargados.");
                    } else {
                        abb.auditarPostOrden();
                    }
                    break;

                case 5:
                    abb.reportePorNivel();
                    break;

                case 6:
                    System.out.print("Ingrese el número de nivel: ");
                    int nivelConsulta = leerOpcion(sc);
                    abb.depositosEnNivel(nivelConsulta);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ── Submenú 3: Red de rutas ──────────────────────────────────────────────

    private static void menuRutas(Scanner sc, GrafoDepositos grafo) {
        int opcion;
        do {
            System.out.println("\nRED DE RUTAS ----------------------------------------");
            System.out.println("1.  Mostrar red de depósitos");
            System.out.println("2.  Agregar ruta entre depósitos");
            System.out.println("3.  Calcular ruta mínima entre dos depósitos");
            System.out.println("0.  Volver al menú principal");
            System.out.print("Opción: ");

            opcion = leerOpcion(sc);

            switch (opcion) {

                case 1:
                    grafo.mostrarRed();
                    break;

                case 2:
                    System.out.print("ID depósito origen: ");
                    int idOrigen = leerOpcion(sc);
                    System.out.print("ID depósito destino: ");
                    int idDestino = leerOpcion(sc);
                    System.out.print("Distancia (km): ");
                    int km = leerOpcion(sc);

                    grafo.agregarRuta(idOrigen, idDestino, km);
                    System.out.println("Ruta agregada.");
                    break;

                case 3:
                    System.out.print("ID depósito origen: ");
                    int desde = leerOpcion(sc);
                    System.out.print("ID depósito destino: ");
                    int hasta = leerOpcion(sc);

                    grafo.distanciaMinima(desde, hasta);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);
    }

    // ── Menú principal ───────────────────────────────────────────────────────

    public static void main(String[] args) {

        PilaCamion<Paquete<String>> camion = new PilaCamion<>(5, 500.0);
        ColaPrioridad<Paquete<String>> centro = new ColaPrioridad<>();
        ABBDepositos abb = new ABBDepositos();
        GrafoDepositos grafo = new GrafoDepositos();
        Scanner sc = new Scanner(System.in);

        int maxId = ManejoArchivos.cargarEnCola("src/inventario.json", centro, indice);
        int[] idContador = { maxId + 1 };

        ManejoArchivos.cargarDepositos("src/depositos.json", abb, grafo);
        ManejoArchivos.cargarRutas("src/rutas.json", grafo);

        int opcion;

        do {
            System.out.println("\nSISTEMA DE LOGÍSTICA ----------------------------------------");
            System.out.println("1.  Gestión de carga");
            System.out.println("2.  Gestión de depósitos");
            System.out.println("3.  Red de rutas");
            System.out.println("0.  Salir");
            System.out.print("Opción: ");

            opcion = leerOpcion(sc);

            switch (opcion) {
                case 1:
                    menuCarga(sc, camion, centro, idContador);
                    break;
                case 2:
                    menuDepositos(sc, abb, grafo);
                    break;
                case 3:
                    menuRutas(sc, grafo);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}