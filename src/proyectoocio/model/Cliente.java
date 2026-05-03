
package proyectoocio.model;

/**
 * Modelo que representa un cliente de Proyecto Ocio.
 * Encapsula todos los atributos con getters y setters.
 */
public class Cliente {

    private int    idCliente;
    private int    idTipo;
    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;

    // Constructor vacío
    public Cliente() {}

    // Constructor con todos los campos
    public Cliente(int idCliente, int idTipo, String nombre,
                   String apellidos, String email, String telefono) {
        this.idCliente = idCliente;
        this.idTipo    = idTipo;
        this.nombre    = nombre;
        this.apellidos = apellidos;
        this.email     = email;
        this.telefono  = telefono;
    }

    // Getters
    public int    getIdCliente()  { return idCliente; }
    public int    getIdTipo()     { return idTipo; }
    public String getNombre()     { return nombre; }
    public String getApellidos()  { return apellidos; }
    public String getEmail()      { return email; }
    public String getTelefono()   { return telefono; }

    // Setters
    public void setIdCliente(int idCliente)    { this.idCliente = idCliente; }
    public void setIdTipo(int idTipo)          { this.idTipo = idTipo; }
    public void setNombre(String nombre)       { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setEmail(String email)         { this.email = email; }
    public void setTelefono(String telefono)   { this.telefono = telefono; }

    @Override
    public String toString() {
        return String.format("[%d] %s %s | %s | %s",
                idCliente, nombre, apellidos, email, telefono);
    }
}
