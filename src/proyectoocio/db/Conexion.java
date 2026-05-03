package proyectoocio.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase encargada de gestionar la conexión con la base de datos MySQL.
 * Utiliza el patrón Singleton para reutilizar la misma conexión.
 */
public class Conexion {

    private static final String URL      = "jdbc:mysql://localhost:3306/proyecto_ocio";
    private static final String USUARIO  = "root";
    private static final String CLAVE    = "";

    private static Connection conexion = null;

    // Constructor privado: nadie puede instanciar esta clase desde fuera
    private Conexion() {}

    /**
     * Devuelve la conexión activa. Si no existe, la crea.
     */
    public static Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
                System.out.println("[DB] Conexión establecida correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] No se pudo conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }

    /**
     * Cierra la conexión con la base de datos.
     */
    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("[DB] Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("[ERROR] Al cerrar la conexión: " + e.getMessage());
        }
    }
}
