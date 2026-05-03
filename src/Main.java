package proyectoocio;

import proyectoocio.controller.Menu;
import proyectoocio.db.Conexion;

/**
 * Clase principal de la aplicación Proyecto Ocio.
 * Punto de entrada del programa.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║       PROYECTO OCIO – v1.0           ║");
        System.out.println("║  Gestión de actividades de ocio      ║");
        System.out.println("╚══════════════════════════════════════╝");

        // Verificar conexión con la base de datos
        if (Conexion.getConexion() == null) {
            System.out.println("[ERROR] No se puede iniciar la aplicación sin conexión a la base de datos.");
            System.out.println("Asegúrate de que XAMPP (MySQL) está en ejecución.");
            return;
        }

        // Lanzar el menú principal
        Menu menu = new Menu();
        menu.mostrarMenuPrincipal();

        // Cerrar conexión al salir
        Conexion.cerrar();
    }
}
