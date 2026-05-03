
package proyectoocio.service;

import proyectoocio.db.Conexion;
import proyectoocio.model.Cliente;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

/**
 * Servicio que gestiona las operaciones CRUD de la entidad Cliente.
 * Se comunica directamente con la base de datos mediante JDBC.
 */
public class ClienteService {

    // ----------------------------------------------------------------
    // LISTAR TODOS LOS CLIENTES
    // ----------------------------------------------------------------
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY apellidos, nombre";

        try (Statement st = Conexion.getConexion().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getInt("id_tipo"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al listar clientes: " + e.getMessage());
        }
        return lista;
    }

    // ----------------------------------------------------------------
    // BUSCAR CLIENTE POR ID
    // ----------------------------------------------------------------
    public Cliente buscarPorId(int id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        Cliente c = null;

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                c = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getInt("id_tipo"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("telefono")
                );
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al buscar cliente: " + e.getMessage());
        }
        return c;
    }

    // ----------------------------------------------------------------
    // INSERTAR NUEVO CLIENTE
    // ----------------------------------------------------------------
    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO cliente (id_tipo, nombre, apellidos, email, telefono, fecha_registro) " +
                "VALUES (?, ?, ?, ?, ?, CURRENT_DATE)";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, c.getIdTipo());
            ps.setString(2, c.getNombre());
            ps.setString(3, c.getApellidos());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getTelefono());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // MODIFICAR CLIENTE EXISTENTE
    // ----------------------------------------------------------------
    public boolean modificar(Cliente c) {
        String sql = "UPDATE cliente SET nombre=?, apellidos=?, email=?, telefono=? " +
                "WHERE id_cliente=?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getApellidos());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelefono());
            ps.setInt(5, c.getIdCliente());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al modificar cliente: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // ELIMINAR CLIENTE POR ID
    // ----------------------------------------------------------------
    public boolean eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("[ERROR] Al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // ----------------------------------------------------------------
    // BUSCAR CLIENTES POR APELLIDO
    // ----------------------------------------------------------------
    public List<Cliente> buscarPorApellido(String apellido) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE apellidos LIKE ? ORDER BY apellidos";

        try (PreparedStatement ps = Conexion.getConexion().prepareStatement(sql)) {
            ps.setString(1, "%" + apellido + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getInt("id_tipo"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("email"),
                        rs.getString("telefono")
                ));
            }

        } catch (SQLException e) {
            System.out.println("[ERROR] Al buscar por apellido: " + e.getMessage());
        }
        return lista;
    }
}
