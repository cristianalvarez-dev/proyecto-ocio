package proyectoocio.service;

import proyectoocio.db.Conexion;
import proyectoocio.model.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones CRUD de la entidad Reserva.
 */
public class ReservaService {

    // ----------------------------------------------------------------
    // LISTAR TODAS LAS RESERVAS CON DATOS DE CLIENTE Y ACTIVIDAD
    // ----------------------------------------------------------------
    public void listarConDetalle() {
        String sql = "SELECT r.id_reserva, " +
                "CONCAT(c.nombre, ' ', c.apellidos) AS cliente, " +
                "a.nombre AS actividad, " +
                "r.fecha_actividad, r.num_participantes, r.estado " +
                "FROM reserva r " +
                "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                "JOIN actividad a ON r.id_actividad = a.id_actividad " +
                "ORDER BY r.fecha_actividad";

        try (Statement st = Conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Listado de reservas ---");
            System.out.printf("%-5s %-25s %-35s %-12s %5s %-12s%n",
                    "ID", "Cliente", "Actividad", "Fecha", "Pers.", "Estado");
            System.out.println("-".repeat(96));

            while (rs.next()) {
                System.out.printf("%-5d %-25s %-35s %-12s %5d %-12s%n",
                        rs.getInt("id_reserva"),
                        rs.getString("cliente"),
                        rs.getString("actividad"),
                        rs.getString("fecha_actividad"),
                        rs.getInt("num_participantes"),
                        rs.getString("estado"));
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al listar reservas: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // LISTAR RESERVAS POR ESTADO
    // ----------------------------------------------------------------
    public void listarPorEstado(String estado) {
        String sql = "SELECT r.id_reserva, " +
                "CONCAT(c.nombre,' ',c.apellidos) AS cliente, " +
                "a.nombre AS actividad, r.fecha_actividad, r.num_participantes " +
                "FROM reserva r " +
                "JOIN cliente c ON r.id_cliente = c.id_cliente " +
                "JOIN actividad a ON r.id_actividad = a.id_actividad " +
                "WHERE r.estado = ? ORDER BY r.fecha_actividad";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n--- Reservas con estado: " + estado + " ---");
            System.out.printf("%-5s %-25s %-35s %-12s %5s%n",
                    "ID", "Cliente", "Actividad", "Fecha", "Pers.");
            System.out.println("-".repeat(82));

            boolean hayResultados = false;
            while (rs.next()) {
                hayResultados = true;
                System.out.printf("%-5d %-25s %-35s %-12s %5d%n",
                        rs.getInt("id_reserva"),
                        rs.getString("cliente"),
                        rs.getString("actividad"),
                        rs.getString("fecha_actividad"),
                        rs.getInt("num_participantes"));
            }
            if (!hayResultados) {
                System.out.println("No hay reservas con ese estado.");
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al filtrar reservas: " + e.getMessage());
        }
    }

    // ----------------------------------------------------------------
    // INSERTAR NUEVA RESERVA
    // ----------------------------------------------------------------
    public boolean insertar(Reserva r) {
        String sql = "INSERT INTO reserva " +
                "(id_cliente, id_actividad, fecha_reserva, fecha_actividad, num_participantes, estado) " +
                "VALUES (?, ?, CURRENT_DATE, ?, ?, 'pendiente')";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, r.getIdCliente());
            ps.setInt(2, r.getIdActividad());
            ps.setString(3, r.getFechaActividad());
            ps.setInt(4, r.getNumParticipantes());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al insertar reserva: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // CAMBIAR ESTADO DE UNA RESERVA
    // ----------------------------------------------------------------
    public boolean cambiarEstado(int idReserva, String nuevoEstado) {
        String sql = "UPDATE reserva SET estado = ? WHERE id_reserva = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idReserva);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // ELIMINAR RESERVA
    // ----------------------------------------------------------------
    public boolean eliminar(int id) {
        // Primero eliminamos el pago asociado si existe
        String sqlPago = "DELETE FROM pago WHERE id_reserva = ?";
        String sqlReserva = "DELETE FROM reserva WHERE id_reserva = ?";

        try (PreparedStatement ps1 = Conexion.getConexion().prepareStatement(sqlPago);
             PreparedStatement ps2 = Conexion.getConexion().prepareStatement(sqlReserva)) {

            ps1.setInt(1, id);
            ps1.executeUpdate();

            ps2.setInt(1, id);
            return ps2.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al eliminar reserva: " + e.getMessage());
            return false;
        }
    }
}
