
package proyectoocio.service;

import proyectoocio.db.Conexion;
import proyectoocio.model.Actividad;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones CRUD de la entidad Actividad.
 */
public class ActividadService {

    // ----------------------------------------------------------------
    // LISTAR TODAS LAS ACTIVIDADES
    // ----------------------------------------------------------------
    public List<Actividad> listarTodas() {
        List<Actividad> lista = new ArrayList<>();
        String sql = "SELECT * FROM actividad ORDER BY nombre";

        try (Statement st = Conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new Actividad(
                        rs.getInt("id_actividad"),
                        rs.getInt("id_categoria"),
                        rs.getInt("id_instalacion"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_base"),
                        rs.getInt("plazas_max"),
                        rs.getInt("duracion_horas")
                ));
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al listar actividades: " + e.getMessage());
        }
        return lista;
    }

    // ----------------------------------------------------------------
    // BUSCAR ACTIVIDAD POR ID
    // ----------------------------------------------------------------
    public Actividad buscarPorId(int id) {
        String sql = "SELECT * FROM actividad WHERE id_actividad = ?";
        Actividad a = null;

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Actividad(
                        rs.getInt("id_actividad"),
                        rs.getInt("id_categoria"),
                        rs.getInt("id_instalacion"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_base"),
                        rs.getInt("plazas_max"),
                        rs.getInt("duracion_horas")
                );
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al buscar actividad: " + e.getMessage());
        }
        return a;
    }

    // ----------------------------------------------------------------
    // INSERTAR NUEVA ACTIVIDAD
    // ----------------------------------------------------------------
    public boolean insertar(Actividad a) {
        String sql = "INSERT INTO actividad " +
                "(id_categoria, id_instalacion, nombre, descripcion, precio_base, plazas_max, duracion_horas) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, a.getIdCategoria());
            ps.setInt(2, a.getIdInstalacion());
            ps.setString(3, a.getNombre());
            ps.setString(4, a.getDescripcion());
            ps.setDouble(5, a.getPrecioBase());
            ps.setInt(6, a.getPlazasMax());
            ps.setInt(7, a.getDuracionHoras());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al insertar actividad: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // MODIFICAR ACTIVIDAD
    // ----------------------------------------------------------------
    public boolean modificar(Actividad a) {
        String sql = "UPDATE actividad SET nombre=?, descripcion=?, precio_base=?, " +
                "plazas_max=?, duracion_horas=? WHERE id_actividad=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getDescripcion());
            ps.setDouble(3, a.getPrecioBase());
            ps.setInt(4, a.getPlazasMax());
            ps.setInt(5, a.getDuracionHoras());
            ps.setInt(6, a.getIdActividad());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al modificar actividad: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // ELIMINAR ACTIVIDAD
    // ----------------------------------------------------------------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM actividad WHERE id_actividad = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al eliminar actividad: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // LISTAR ACTIVIDADES CON VALORACIÓN MEDIA (JOIN)
    // ----------------------------------------------------------------
    public void mostrarValoraciones() {
        String sql = "SELECT a.nombre, COUNT(v.id_valoracion) AS num_val, " +
                "ROUND(AVG(v.puntuacion), 2) AS media " +
                "FROM actividad a " +
                "LEFT JOIN valoracion v ON a.id_actividad = v.id_actividad " +
                "GROUP BY a.id_actividad, a.nombre " +
                "ORDER BY media DESC";

        try (Statement st = Conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- Valoraciones por actividad ---");
            System.out.printf("%-40s %10s %10s%n", "Actividad", "Num. val.", "Media");
            System.out.println("-".repeat(62));

            while (rs.next()) {
                System.out.printf("%-40s %10d %10s%n",
                        rs.getString("nombre"),
                        rs.getInt("num_val"),
                        rs.getString("media") != null ? rs.getString("media") : "Sin datos");
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al mostrar valoraciones: " + e.getMessage());
        }
    }
}
