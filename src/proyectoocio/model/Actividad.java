package proyectoocio.model;

/**
 * Modelo que representa una actividad de Proyecto Ocio.
 */
public class Actividad {

    private int    idActividad;
    private int    idCategoria;
    private int    idInstalacion;
    private String nombre;
    private String descripcion;
    private double precioBase;
    private int    plazasMax;
    private int    duracionHoras;

    public Actividad() {}

    public Actividad(int idActividad, int idCategoria, int idInstalacion,
                     String nombre, String descripcion,
                     double precioBase, int plazasMax, int duracionHoras) {
        this.idActividad   = idActividad;
        this.idCategoria   = idCategoria;
        this.idInstalacion = idInstalacion;
        this.nombre        = nombre;
        this.descripcion   = descripcion;
        this.precioBase    = precioBase;
        this.plazasMax     = plazasMax;
        this.duracionHoras = duracionHoras;
    }

    // Getters
    public int    getIdActividad()   { return idActividad; }
    public int    getIdCategoria()   { return idCategoria; }
    public int    getIdInstalacion() { return idInstalacion; }
    public String getNombre()        { return nombre; }
    public String getDescripcion()   { return descripcion; }
    public double getPrecioBase()    { return precioBase; }
    public int    getPlazasMax()     { return plazasMax; }
    public int    getDuracionHoras() { return duracionHoras; }

    // Setters
    public void setIdActividad(int idActividad)     { this.idActividad = idActividad; }
    public void setIdCategoria(int idCategoria)     { this.idCategoria = idCategoria; }
    public void setIdInstalacion(int idInstalacion) { this.idInstalacion = idInstalacion; }
    public void setNombre(String nombre)            { this.nombre = nombre; }
    public void setDescripcion(String descripcion)  { this.descripcion = descripcion; }
    public void setPrecioBase(double precioBase)    { this.precioBase = precioBase; }
    public void setPlazasMax(int plazasMax)         { this.plazasMax = plazasMax; }
    public void setDuracionHoras(int duracionHoras) { this.duracionHoras = duracionHoras; }

    @Override
    public String toString() {
        return String.format("[%d] %s | %.2f€/persona | %d h | Max. %d plazas",
                idActividad, nombre, precioBase, duracionHoras, plazasMax);
    }
}
