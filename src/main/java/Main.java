import dao.FabricanteDAO;
import dao.ProductoDAO;
import entity.Fabricante;
import entity.Producto;

import java.util.List;
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
            sc.nextLine();

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
                                            buscarFabricantePorProducto();
                                            break;
                                        case 4:
                                            listarProductosDeFabricante();
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
                                listarProductos();
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
        System.out.println("SE HAN ENCONTRADO " + fabricantes.size() + " REGISTROS:");
        for (var f : fabricantes) {
            System.out.println("-> ID: " + f.getCod() + " | Marca: " + f.getNombre());
        }
    }

    private static void buscarFabricanteNombre() {
        System.out.print("Introduzca el nombre del fabricante: ");
        String nombre = sc.nextLine();
        Fabricante fabricante = fabricanteDAO.buscarPorNombre(nombre);
        if (fabricante == null) {
            System.out.println("No existe ningún fabricante con ese nombre.");
        } else {
            System.out.println("SE HA ENCONTRADO 1 REGISTRO:");
            System.out.println("-> ID: " + fabricante.getCod() + " Marca: " + fabricante.getNombre());
        }
    }

    private static void crearFabricante() {
        System.out.print("Introduce el nombre del fabricante: ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }
        if (fabricanteDAO.buscarPorNombre(nombre) != null) {
            System.out.println("Ya existe un fabricante con ese nombre.");
            return;
        }
        try {
            Fabricante fabricante = new Fabricante(nombre);
            fabricanteDAO.guardarFabricante(fabricante);
            System.out.println("ÉXITO: Fabricante creado con ID " + fabricante.getCod());
        } catch (Exception ex) {
            System.out.println("Error al intentar crear el fabricante");
        }
    }

    private static void modificarFabricante() {
        System.out.print("Introduce el ID del fabricante: ");
        int id = sc.nextInt(); sc.nextLine();
        Fabricante fabricante = fabricanteDAO.buscarPorId(id);
        if (fabricante == null) {
            System.out.println("Error: No existe ningún fabricante con ID " + id);
            return;
        }
        System.out.println("Nombre actual : " + fabricante.getNombre());
        System.out.print("Introduce el nuevo nombre: ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) return;
        fabricante.setNombre(nombre);
        fabricanteDAO.actualizarFabricante(fabricante);
        System.out.println("ÉXITO: Fabricante actualizado.");
    }

    private static void borrarFabricante() {
        System.out.print("Introduce el ID del fabricante a borrar: ");
        int id = sc.nextInt(); sc.nextLine();
        Fabricante fabricante = fabricanteDAO.buscarPorId(id);
        if (fabricante == null) {
            System.out.println("Error: No existe el ID " + id);
            return;
        }
        System.out.print("¿Estás seguro de borrar a " + fabricante.getNombre() + "? (s/n): ");
        if (sc.nextLine().equalsIgnoreCase("s")) {
            fabricanteDAO.borrarFabricante(fabricante);
            System.out.println("ÉXITO: Fabricante eliminado.");
        }
    }

    private static void crearProducto() {
        System.out.print("Nombre del producto: ");
        String nombre = sc.nextLine();
        System.out.print("Precio: ");
        double precio = sc.nextDouble(); sc.nextLine();

        System.out.println("Fabricantes disponibles:");
        List<Fabricante> fabricantes = fabricanteDAO.listarTodos();
        for (Fabricante f : fabricantes) {
            System.out.println("- " + f.getNombre());
        }
        System.out.print("Nombre del fabricante (si no existe, se creará): ");
        String nombreFab = sc.nextLine();
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        productoDAO.guardarProductoJuntoFabricante(producto, nombreFab);
        System.out.println("¡Operación finalizada con éxito!");
    }

    private static void listarProductos() {
        var productos = productoDAO.listarTodos();
        for (Producto p : productos) {
            System.out.println(p.getCod() + ": " + p.getNombre() + " " + p.getPrecio() + " [" + p.getFabricante().getNombre() + "]");
        }
    }

    private static void modificarProducto() {
        System.out.print("Introduce el ID del producto a modificar: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Introduce el nuevo precio: ");
        double precio = sc.nextDouble(); sc.nextLine();
        productoDAO.actualizarPrecio(id, precio);
        System.out.println("ÉXITO: Producto actualizado.");
    }

    private static void borrarProducto() {
        System.out.print("Introduce el ID del producto a borrar: ");
        int id = sc.nextInt(); sc.nextLine();
        Producto producto = productoDAO.buscarPorIdProducto(id);
        if (producto != null) {
            productoDAO.borrarProducto(producto);
            System.out.println("ÉXITO: Producto borrado.");
        }
    }

    private static void buscarFabricantePorProducto() {
        System.out.print("Introduce el nombre del producto: ");
        Fabricante f = fabricanteDAO.buscarPorNombreProducto(sc.nextLine());
        if (f != null) {
            System.out.println("-> ID: " + f.getCod() + " Marca: " + f.getNombre());
        } else {
            System.out.println("No se encontró el producto.");
        }
    }

    private static void listarProductosDeFabricante() {
        System.out.print("Introduce el nombre del fabricante: ");
        String nombre = sc.nextLine();
        List<Producto> prods = productoDAO.buscarPorFabricante(nombre);
        System.out.println("SE HAN ENCONTRADO " + prods.size() + " REGISTROS ASOCIADOS:");
        for (Producto p : prods) {
            System.out.println("-> ID: " + p.getCod() + " Producto: " + p.getNombre());
        }
    }
}