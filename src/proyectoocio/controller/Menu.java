package proyectoocio.controller;

import proyectoocio.model.Actividad;
import proyectoocio.model.Cliente;
import proyectoocio.model.Reserva;
import proyectoocio.service.ActividadService;
import proyectoocio.service.ClienteService;
import proyectoocio.service.ReservaService;

import java.util.List;
import java.util.Scanner;

/**
 * Controlador principal de la aplicación.
 * Gestiona los menús por consola y coordina los servicios.
 */
public class Menu {

    private final Scanner          scanner          = new Scanner(System.in);
    private final ClienteService   clienteService   = new ClienteService();
    private final ActividadService actividadService = new ActividadService();
    private final ReservaService   reservaService   = new ReservaService();

    // ----------------------------------------------------------------
    // MENÚ PRINCIPAL
    // ----------------------------------------------------------------
    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n╔══════════════════════════════════════╗");
            System.out.println("║        PROYECTO OCIO – GESTIÓN       ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║  1. Gestión de clientes              ║");
            System.out.println("║  2. Gestión de actividades           ║");
            System.out.println("║  3. Gestión de reservas              ║");
            System.out.println("║  4. Estadísticas y consultas         ║");
            System.out.println("║  0. Salir                            ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> menuClientes();
                case 2 -> menuActividades();
                case 3 -> menuReservas();
                case 4 -> menuEstadisticas();
                case 0 -> System.out.println("\nHasta pronto. ¡Gracias por usar Proyecto Ocio!");
                default -> System.out.println("[!] Opción no válida. Inténtalo de nuevo.");
            }
        } while (opcion != 0);
    }

    // ----------------------------------------------------------------
    // MENÚ CLIENTES
    // ----------------------------------------------------------------
    private void menuClientes() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE CLIENTES ---");
            System.out.println("1. Listar todos los clientes");
            System.out.println("2. Buscar cliente por ID");
            System.out.println("3. Buscar cliente por apellido");
            System.out.println("4. Añadir nuevo cliente");
            System.out.println("5. Modificar cliente");
            System.out.println("6. Eliminar cliente");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarClientes();
                case 2 -> buscarClientePorId();
                case 3 -> buscarClientePorApellido();
                case 4 -> añadirCliente();
                case 5 -> modificarCliente();
                case 6 -> eliminarCliente();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("[!] Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void listarClientes() {
        List<Cliente> lista = clienteService.listarTodos();
        if (lista.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        System.out.println("\n--- Clientes registrados ---");
        for (Cliente c : lista) {
            System.out.println(c);
        }
    }

    private void buscarClientePorId() {
        System.out.print("Introduce el ID del cliente: ");
        int id = leerEntero();
        Cliente c = clienteService.buscarPorId(id);
        if (c != null) {
            System.out.println("Cliente encontrado: " + c);
        } else {
            System.out.println("No se encontró ningún cliente con ese ID.");
        }
    }

    private void buscarClientePorApellido() {
        System.out.print("Introduce el apellido a buscar: ");
        String apellido = scanner.nextLine();
        List<Cliente> lista = clienteService.buscarPorApellido(apellido);
        if (lista.isEmpty()) {
            System.out.println("No se encontraron clientes con ese apellido.");
        } else {
            for (Cliente c : lista) System.out.println(c);
        }
    }

    private void añadirCliente() {
        System.out.println("\n--- Nuevo cliente ---");
        System.out.println("Tipos: 1=Particular adulto, 2=Familia, 3=Grupo jóvenes, 4=Colegio, 5=Empresa");
        System.out.print("Tipo de cliente (1-5): ");
        int tipo = leerEntero();

        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Teléfono: ");
        String telefono = scanner.nextLine();

        Cliente c = new Cliente(0, tipo, nombre, apellidos, email, telefono);
        if (clienteService.insertar(c)) {
            System.out.println("Cliente añadido correctamente.");
        } else {
            System.out.println("No se pudo añadir el cliente.");
        }
    }

    private void modificarCliente() {
        System.out.print("ID del cliente a modificar: ");
        int id = leerEntero();
        Cliente c = clienteService.buscarPorId(id);
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }
        System.out.println("Cliente actual: " + c);
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine();
        System.out.print("Nuevos apellidos (Enter para mantener): ");
        String apellidos = scanner.nextLine();
        System.out.print("Nuevo email (Enter para mantener): ");
        String email = scanner.nextLine();
        System.out.print("Nuevo teléfono (Enter para mantener): ");
        String telefono = scanner.nextLine();

        if (!nombre.isBlank())    c.setNombre(nombre);
        if (!apellidos.isBlank()) c.setApellidos(apellidos);
        if (!email.isBlank())     c.setEmail(email);
        if (!telefono.isBlank())  c.setTelefono(telefono);

        if (clienteService.modificar(c)) {
            System.out.println("Cliente modificado correctamente.");
        } else {
            System.out.println("No se pudo modificar el cliente.");
        }
    }

    private void eliminarCliente() {
        System.out.print("ID del cliente a eliminar: ");
        int id = leerEntero();
        System.out.print("¿Seguro que quieres eliminar el cliente " + id + "? (s/n): ");
        String confirmacion = scanner.nextLine();
        if (confirmacion.equalsIgnoreCase("s")) {
            if (clienteService.eliminar(id)) {
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("No se pudo eliminar. Puede tener reservas asociadas.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // ----------------------------------------------------------------
    // MENÚ ACTIVIDADES
    // ----------------------------------------------------------------
    private void menuActividades() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE ACTIVIDADES ---");
            System.out.println("1. Listar todas las actividades");
            System.out.println("2. Buscar actividad por ID");
            System.out.println("3. Añadir nueva actividad");
            System.out.println("4. Modificar actividad");
            System.out.println("5. Eliminar actividad");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> listarActividades();
                case 2 -> buscarActividadPorId();
                case 3 -> añadirActividad();
                case 4 -> modificarActividad();
                case 5 -> eliminarActividad();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("[!] Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void listarActividades() {
        List<Actividad> lista = actividadService.listarTodas();
        if (lista.isEmpty()) {
            System.out.println("No hay actividades registradas.");
            return;
        }
        System.out.println("\n--- Actividades disponibles ---");
        for (Actividad a : lista) System.out.println(a);
    }

    private void buscarActividadPorId() {
        System.out.print("Introduce el ID de la actividad: ");
        int id = leerEntero();
        Actividad a = actividadService.buscarPorId(id);
        if (a != null) {
            System.out.println("Actividad encontrada: " + a);
        } else {
            System.out.println("No se encontró ninguna actividad con ese ID.");
        }
    }

    private void añadirActividad() {
        System.out.println("\n--- Nueva actividad ---");
        System.out.println("Categorías: 1=Naturaleza, 2=Talleres, 3=Deportes, 4=Team building, 5=Educativo");
        System.out.print("ID Categoría (1-5): ");
        int idCat = leerEntero();
        System.out.println("Instalaciones: 1=Sede Central, 2=Parque Retiro, 3=C.Deportivo Norte, 4=Aula Naturaleza, 5=Sala Talleres");
        System.out.print("ID Instalación (1-5): ");
        int idInst = leerEntero();
        System.out.print("Nombre de la actividad: ");
        String nombre = scanner.nextLine();
        System.out.print("Descripción: ");
        String desc = scanner.nextLine();
        System.out.print("Precio base (€): ");
        double precio = leerDouble();
        System.out.print("Plazas máximas: ");
        int plazas = leerEntero();
        System.out.print("Duración (horas): ");
        int horas = leerEntero();

        Actividad a = new Actividad(0, idCat, idInst, nombre, desc, precio, plazas, horas);
        if (actividadService.insertar(a)) {
            System.out.println("Actividad añadida correctamente.");
        } else {
            System.out.println("No se pudo añadir la actividad.");
        }
    }

    private void modificarActividad() {
        System.out.print("ID de la actividad a modificar: ");
        int id = leerEntero();
        Actividad a = actividadService.buscarPorId(id);
        if (a == null) {
            System.out.println("Actividad no encontrada.");
            return;
        }
        System.out.println("Actividad actual: " + a);
        System.out.print("Nuevo nombre (Enter para mantener): ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva descripción (Enter para mantener): ");
        String desc = scanner.nextLine();
        System.out.print("Nuevo precio (0 para mantener): ");
        double precio = leerDouble();
        System.out.print("Nuevas plazas máx (0 para mantener): ");
        int plazas = leerEntero();
        System.out.print("Nueva duración horas (0 para mantener): ");
        int horas = leerEntero();

        if (!nombre.isBlank()) a.setNombre(nombre);
        if (!desc.isBlank())   a.setDescripcion(desc);
        if (precio > 0)        a.setPrecioBase(precio);
        if (plazas > 0)        a.setPlazasMax(plazas);
        if (horas > 0)         a.setDuracionHoras(horas);

        if (actividadService.modificar(a)) {
            System.out.println("Actividad modificada correctamente.");
        } else {
            System.out.println("No se pudo modificar la actividad.");
        }
    }

    private void eliminarActividad() {
        System.out.print("ID de la actividad a eliminar: ");
        int id = leerEntero();
        System.out.print("¿Seguro? (s/n): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("s")) {
            if (actividadService.eliminar(id)) {
                System.out.println("Actividad eliminada.");
            } else {
                System.out.println("No se pudo eliminar. Puede tener reservas asociadas.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // ----------------------------------------------------------------
    // MENÚ RESERVAS
    // ----------------------------------------------------------------
    private void menuReservas() {
        int opcion;
        do {
            System.out.println("\n--- GESTIÓN DE RESERVAS ---");
            System.out.println("1. Listar todas las reservas");
            System.out.println("2. Filtrar reservas por estado");
            System.out.println("3. Nueva reserva");
            System.out.println("4. Cambiar estado de una reserva");
            System.out.println("5. Eliminar reserva");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> reservaService.listarConDetalle();
                case 2 -> filtrarReservasPorEstado();
                case 3 -> nuevaReserva();
                case 4 -> cambiarEstadoReserva();
                case 5 -> eliminarReserva();
                case 0 -> System.out.println("Volviendo al menú principal...");
                default -> System.out.println("[!] Opción no válida.");
            }
        } while (opcion != 0);
    }

    private void filtrarReservasPorEstado() {
        System.out.println("Estados disponibles: pendiente / confirmada / cancelada / completada");
        System.out.print("Estado a filtrar: ");
        String estado = scanner.nextLine().trim();
        reservaService.listarPorEstado(estado);
    }

    private void nuevaReserva() {
        System.out.println("\n--- Nueva reserva ---");
        listarClientes();
        System.out.print("ID del cliente: ");
        int idCliente = leerEntero();
        listarActividades();
        System.out.print("ID de la actividad: ");
        int idActividad = leerEntero();
        System.out.print("Fecha de la actividad (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("Número de participantes: ");
        int participantes = leerEntero();

        Reserva r = new Reserva(0, idCliente, idActividad, "", fecha, participantes, "pendiente");
        if (reservaService.insertar(r)) {
            System.out.println("Reserva creada correctamente con estado 'pendiente'.");
        } else {
            System.out.println("No se pudo crear la reserva.");
        }
    }

    private void cambiarEstadoReserva() {
        reservaService.listarConDetalle();
        System.out.print("ID de la reserva a modificar: ");
        int id = leerEntero();
        System.out.println("Nuevo estado: pendiente / confirmada / cancelada / completada");
        System.out.print("Estado: ");
        String estado = scanner.nextLine().trim();
        if (reservaService.cambiarEstado(id, estado)) {
            System.out.println("Estado actualizado correctamente.");
        } else {
            System.out.println("No se pudo actualizar el estado.");
        }
    }

    private void eliminarReserva() {
        System.out.print("ID de la reserva a eliminar: ");
        int id = leerEntero();
        System.out.print("¿Seguro? (s/n): ");
        String conf = scanner.nextLine();
        if (conf.equalsIgnoreCase("s")) {
            if (reservaService.eliminar(id)) {
                System.out.println("Reserva eliminada correctamente.");
            } else {
                System.out.println("No se pudo eliminar la reserva.");
            }
        } else {
            System.out.println("Operación cancelada.");
        }
    }

    // ----------------------------------------------------------------
    // MENÚ ESTADÍSTICAS
    // ----------------------------------------------------------------
    private void menuEstadisticas() {
        int opcion;
        do {
            System.out.println("\n--- ESTADÍSTICAS Y CONSULTAS ---");
            System.out.println("1. Valoraciones por actividad");
            System.out.println("2. Reservas pendientes de confirmación");
            System.out.println("0. Volver al menú principal");
            System.out.print("Elige una opción: ");
            opcion = leerEntero();

            switch (opcion) {
                case 1 -> actividadService.mostrarValoraciones();
                case 2 -> reservaService.listarPorEstado("pendiente");
                case 0 -> System.out.println("Volviendo...");
                default -> System.out.println("[!] Opción no válida.");
            }
        } while (opcion != 0);
    }

    // ----------------------------------------------------------------
    // UTILIDADES DE LECTURA
    // ----------------------------------------------------------------
    private int leerEntero() {
        try {
            int valor = Integer.parseInt(scanner.nextLine().trim());
            return valor;
        } catch (NumberFormatException e) {
            System.out.println("[!] Valor no válido. Se usará 0.");
            return 0;
        }
    }

    private double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            System.out.println("[!] Valor no válido. Se usará 0.");
            return 0;
        }
    }
}
