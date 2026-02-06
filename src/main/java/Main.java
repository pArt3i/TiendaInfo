import dao.FabricanteDAO;
import dao.ProductoDAO;
import entity.Fabricante;
import entity.Producto;

import java.util.Scanner;

public class Main {
    static FabricanteDAO fabricanteDAO = new FabricanteDAO();
    static ProductoDAO productoDAO = new ProductoDAO();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 1, opcion2 = 1, opcion3 = 1;
        do {
            menuPrincipal();
            System.out.print("Opcion: ");
            opcion = sc.nextInt();
            sc.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 0:
                    System.out.println("Saliendo del programa");
                    break;
                case 1:
                    do {
                        menuFabricante();
                        System.out.print("Opcion: ");
                        opcion2 = sc.nextInt();
                        sc.nextLine();
                        switch (opcion2) {
                            case 1:
                                crearFabricante();
                                break;
                            case 2:
                                do {
                                    menuBuscarFabricante();
                                    System.out.print("Opcion (Pulse 0 para volver): ");
                                    opcion3 = sc.nextInt();
                                    sc.nextLine();
                                    switch (opcion3) {
                                        case 1:
                                            listarFabricantes();
                                            break;
                                        case 2:
                                            buscarFabricanteNombre();
                                            break;
                                        case 3:
                                            //buscar por nombre de producto
                                            break;
                                        case 4:
                                            //buscar por nombre de fabticante y obtener producto asociados
                                            break;
                                        case 0:
                                            System.out.println("Saliendo de busqueda...");
                                            break;
                                        default:
                                            System.out.println("Opcion no valida");
                                    }
                                } while (opcion3 != 0);
                                break;
                            case 3:
                                modificarFabricante();
                                break;
                            case 4:
                                borrarFabricante();
                                break;
                            case 5:
                                System.out.println("Volviendo al menu principal");
                                break;
                            default:
                                System.out.println("Opcion no valida");
                                break;
                        }
                    } while (opcion2 != 5);
                    break;
                case 2:
                    do {
                        menuProducto();
                        System.out.print("Opcion: ");
                        opcion2 = sc.nextInt();
                        sc.nextLine();
                        switch (opcion2) {
                            case 1:
                                crearProducto();
                                break;
                            case 2:
                                //menuBuscarProducto()
                                do {
                                    System.out.println("\n1. Ver todos\n0. Volver");
                                    System.out.print("Opcion: ");
                                    opcion3 = sc.nextInt();
                                    sc.nextLine();
                                    if (opcion3 == 1) {
                                        // listar productos
                                    }
                                } while (opcion3 != 0);
                                break;
                            case 3:
                                modificarProducto();
                                break;
                            case 4:
                                borrarProducto();
                                break;
                            case 5:
                                System.out.println("Volviendo al menu principal");
                                break;
                            default:
                                System.out.println("Opcion no valida");
                                break;
                        }
                    } while (opcion2 != 5);
                    break;
                default:
                    if (opcion != 0) System.out.println("Opcion no valida");
            }
        } while (opcion != 0);
    }

    public static void menuBuscarFabricante() {
        System.out.println("\n--- BUSCAR FABRICANTES ---");
        System.out.println("1. Ver TODOS");
        System.out.println("2. Buscar el Id por NOMBRE de Fabricante");
        System.out.println("3. Buscar por NOMBRE de PRODUCTO");
        System.out.println("4. Buscar por NOMBRE de Fabricante y obtener productos asociados");
        System.out.println("0. Volver");
    }

    public static void menuFabricante() {
        System.out.println("\n--- GESTION DE FABRICANTE ---");
        System.out.println("1. Crear");
        System.out.println("2. Buscar / Leer");
        System.out.println("3. Actualizar");
        System.out.println("4. Borrar");
        System.out.println("5. Volver");
    }

    public static void menuProducto() {
        System.out.println("\n--- GESTION DE PRODUCTO ---");
        System.out.println("1. Crear");
        System.out.println("2. Buscar / Leer");
        System.out.println("3. Actualizar");
        System.out.println("4. Borrar");
        System.out.println("5. Volver");
    }

    public static void menuPrincipal() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Fabricante");
        System.out.println("2. Producto");
        System.out.println("0. Salir");
    }

    private static void listarFabricantes() {
        var fabricantes = fabricanteDAO.listarTodos();
        System.out.println("\n--- LISTADO DE FABRICANTES ---");
        for (var f : fabricantes) {
            System.out.println("ID: " + f.getCod() + " | Nombre: " + f.getNombre());
        }
    }

    private static void buscarFabricanteNombre() {
        System.out.println("\n--- BUSCAR FABRICANTE POR NOMBRE ---");
        System.out.print("Introduzca el nombre del fabricante para saber su codigo: ");
        String nombre = sc.nextLine();

        Fabricante fabricante = fabricanteDAO.buscarPorNombre(nombre);

        if (fabricante == null) {
            System.out.println("No existe ningún fabricante con ese nombre.");
        } else {
            System.out.println("Código del fabricante: " + fabricante.getCod());
        }
    }


    private static void crearFabricante() {
        System.out.println("Introduce el nombre del fabricante: ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) {
            System.out.println("El nombre esta vacio");
            return;
        }
        try {
            Fabricante fabricante = new Fabricante(nombre);
            fabricanteDAO.guardarFabricante(fabricante);
            System.out.println("Fabricante guardado.");
        } catch (Exception ex) {
            System.out.println("Error al intentar crear el fabricante");
        }
    }

    private static void modificarFabricante() {
        System.out.println("--ACTUALIZAR FABRICANTE--");
        listarFabricantes();
        System.out.print("Introduce el ID del fabricante: ");
        int id = sc.nextInt();
        sc.nextLine();
        Fabricante fabricante = fabricanteDAO.buscarPorId(id);
        if (fabricante == null) {
            System.out.println("Error: No existe ningún fabricante con ID " + id);
            return;
        }
        System.out.println("Nombre actual : " + fabricante.getNombre());
        System.out.print("Introduce el nuevo nombre: ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) {
            System.out.println("El nombre esta vacio");
            return;
        }
        fabricante.setNombre(nombre);
        try {
            fabricanteDAO.actualizarFabricante(fabricante);
            System.out.println("EXITO: Fabricante actualizado");
        } catch (Exception ex) {
            System.out.println("Error al intentar modificar el fabricante");
        }
    }

    private static void borrarFabricante() {
        listarFabricantes();
        System.out.print("Introduce el ID del fabricante a borrar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Fabricante fabricante = fabricanteDAO.buscarPorId(id);
        if (fabricante == null) {
            System.out.println("Error: No existe ningún fabricante con ID " + id);
            return;
        }
        System.out.println("Fabricante encontrado: " + fabricante.getNombre());
        try {
            fabricanteDAO.borrarFabricante(fabricante);
            System.out.println("ÉXITO: Fabricante borrado correctamente.");
        } catch (Exception ex) {
            System.out.println("Error: No se pudo borrar el fabricante.");
        }
    }

    private static void crearProducto() {
        System.out.println("Introduce el nombre del producto: ");
        String nombre = sc.nextLine();
        System.out.println("Introduce el precio de producto: ");
        double precio = sc.nextDouble();
        sc.nextLine();
        if (nombre.isEmpty()) {
            System.out.println("El nombre esta vacio");
            return;
        }
        if (precio < 0) {
            System.out.println("Precio negativo");
            return;
        }
        try {
            Producto producto = new Producto();
            // Falta setear nombre y precio en tu entidad Producto si los tienes
            productoDAO.guardarProducto(producto);
            System.out.println("Producto guardado.");
        } catch (Exception ex) {
            System.out.println("Error al intentar crear un producto");
        }
    }

    private static void modificarProducto() {
        System.out.print("Introduce el ID del producto: ");
        int id = sc.nextInt();
        sc.nextLine();
        Producto producto = productoDAO.buscarPorIdProducto(id);
        if (producto == null) {
            System.out.println("Error: No existe ningún producto con ID " + id);
            return;
        }
        System.out.print("Introduce el nuevo nombre: ");
        String nombre = sc.nextLine();
        // producto.setNombre(nombre); ... etc
        try {
            productoDAO.actualizarProducto(producto);
            System.out.println("EXITO: Producto actualizado");
        } catch (Exception ex) {
            System.out.println("Error al intentar modificar el producto");
        }
    }

    private static void borrarProducto() {
        System.out.print("Introduce el ID del producto a borrar: ");
        int id = sc.nextInt();
        sc.nextLine();
        Producto producto = productoDAO.buscarPorIdProducto(id);
        if (producto == null) {
            System.out.println("Error: No existe ningún producto con ID " + id);
            return;
        }
        try {
            productoDAO.borrarProducto(producto);
            System.out.println("ÉXITO: Producto borrado correctamente.");
        } catch (Exception ex) {
            System.out.println("Error: No se pudo borrar el producto.");
        }
    }
}