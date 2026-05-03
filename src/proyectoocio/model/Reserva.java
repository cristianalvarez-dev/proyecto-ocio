package proyectoocio.model;
/**
 * Modelo que representa una reserva de Proyecto Ocio.
 */
public class Reserva {

    private int    idReserva;
    private int    idCliente;
    private int    idActividad;
    private String fechaReserva;
    private String fechaActividad;
    private int    numParticipantes;
    private String estado;

    public Reserva() {}

    public Reserva(int idReserva, int idCliente, int idActividad,
                   String fechaReserva, String fechaActividad,
                   int numParticipantes, String estado) {
        this.idReserva        = idReserva;
        this.idCliente        = idCliente;
        this.idActividad      = idActividad;
        this.fechaReserva     = fechaReserva;
        this.fechaActividad   = fechaActividad;
        this.numParticipantes = numParticipantes;
        this.estado           = estado;
    }

    // Getters
    public int    getIdReserva()        { return idReserva; }
    public int    getIdCliente()        { return idCliente; }
    public int    getIdActividad()      { return idActividad; }
    public String getFechaReserva()     { return fechaReserva; }
    public String getFechaActividad()   { return fechaActividad; }
    public int    getNumParticipantes() { return numParticipantes; }
    public String getEstado()           { return estado; }

    // Setters
    public void setIdReserva(int idReserva)               { this.idReserva = idReserva; }
    public void setIdCliente(int idCliente)               { this.idCliente = idCliente; }
    public void setIdActividad(int idActividad)           { this.idActividad = idActividad; }
    public void setFechaReserva(String fechaReserva)      { this.fechaReserva = fechaReserva; }
    public void setFechaActividad(String fechaActividad)  { this.fechaActividad = fechaActividad; }
    public void setNumParticipantes(int numParticipantes) { this.numParticipantes = numParticipantes; }
    public void setEstado(String estado)                  { this.estado = estado; }

    @Override
    public String toString() {
        return String.format("[%d] Cliente:%d | Actividad:%d | Fecha:%s | %d personas | %s",
                idReserva, idCliente, idActividad,
                fechaActividad, numParticipantes, estado);
    }
}
