package gestion;

import estructuras.AVLEstudiantes;
import estructuras.AVLPrioridad;
import modelos.Estudiante;

/** Administra los indices por identificador y por prioridad de espera. */
public class GestorEstudiantes {

    private final AVLEstudiantes arbolEstudiantes = new AVLEstudiantes();
    private final AVLPrioridad arbolPrioridad = new AVLPrioridad();

    public boolean registrar(Estudiante estudiante) {
        return estudiante != null && estudiante.getId() != null
                && arbolEstudiantes.insertar(estudiante);
    }

    public Estudiante buscar(String id) {
        return id == null ? null : arbolEstudiantes.buscar(id);
    }

    /** Elimina del indice por ID. GestorSistema coordina la espera y el cupo. */
    public boolean eliminarRegistro(String id) {
        return id != null && arbolEstudiantes.eliminar(id);
    }

    public boolean agregarAEspera(Estudiante estudiante) {
        return estudiante != null && !estudiante.tieneResidencia()
                && buscar(estudiante.getId()) == estudiante
                && arbolPrioridad.insertar(estudiante);
    }

    public boolean quitarDeEspera(int puntajeAnterior, String id) {
        return id != null && arbolPrioridad.eliminar(puntajeAnterior, id);
    }

    public Estudiante obtenerSiguienteEnEspera() {
        return arbolPrioridad.obtenerMayorPrioridad();
    }

    public Estudiante extraerSiguienteEnEspera() {
        return arbolPrioridad.extraerMayorPrioridad();
    }

    public boolean actualizarPuntajeEnEspera(String id, int nuevoPuntaje) {
        Estudiante estudiante = buscar(id);
        if (estudiante == null || estudiante.tieneResidencia()) {
            return false;
        }
        int anterior = estudiante.getPuntajeSocioeconomico();

        // No cambiar el objeto mientras la clave anterior siga en el AVL.
        if (!quitarDeEspera(anterior, id)) {
            return false;
        }
        estudiante.setPuntajeSocioeconomico(nuevoPuntaje);
        if (agregarAEspera(estudiante)) {
            return true;
        }

        estudiante.setPuntajeSocioeconomico(anterior);
        if (!agregarAEspera(estudiante)) {
            throw new IllegalStateException("No se pudo restaurar la lista de espera.");
        }
        throw new IllegalStateException("El AVL rechazo la nueva clave de " + id + ".");
    }

    public Estudiante[] obtenerTodos() {
        return arbolEstudiantes.obtenerEnOrden();
    }

    public Estudiante[] obtenerListaEspera() {
        return arbolPrioridad.obtenerEnOrden();
    }
}