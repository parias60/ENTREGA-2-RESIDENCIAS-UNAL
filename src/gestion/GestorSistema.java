package gestion;

import modelos.Estudiante;
import modelos.Residencia;

/** Centraliza las reglas de asignacion y coordina estudiantes y cupos. */
public class GestorSistema {

    private static final int CUPOS_PREDETERMINADOS = 10;

    private final GestorEstudiantes gestorEstudiantes;
    private final GestorResidencias gestorResidencias;
    private final int cantidadCupos;

    public GestorSistema() {
        this(CUPOS_PREDETERMINADOS);
    }

    public GestorSistema(int cantidadCupos) {
        if (cantidadCupos < 0) {
            throw new IllegalArgumentException("La cantidad de cupos no puede ser negativa.");
        }
        this.cantidadCupos = cantidadCupos;
        gestorEstudiantes = new GestorEstudiantes();
        gestorResidencias = new GestorResidencias(cantidadCupos);
    }

    public boolean registrarEstudiante(Estudiante estudiante) {
        if (estudiante == null || !tieneTexto(estudiante.getId())
                || !tieneTexto(estudiante.getNombre()) || estudiante.tieneResidencia()
                || buscarEstudiante(estudiante.getId()) != null
                || !gestorEstudiantes.registrar(estudiante)) {
            return false;
        }

        Residencia disponible = gestorResidencias.buscarCupoDisponible();
        if (gestorEstudiantes.obtenerSiguienteEnEspera() == null) {
            if (disponible != null && asignarCupo(disponible, estudiante)) {
                return true;
            }
            return registrarEnEspera(estudiante);
        }

        // Si hay espera y un cupo libre, el primero anterior tiene precedencia
        // sobre el recien llegado, aun si el nuevo trae un puntaje menor.
        if (disponible != null) {
            asignarSiguienteEn(disponible);
        }
        return registrarEnEspera(estudiante);
    }

    public Estudiante buscarEstudiante(String id) {
        return gestorEstudiantes.buscar(id);
    }

    public boolean modificarNombre(String id, String nuevoNombre) {
        Estudiante estudiante = buscarEstudiante(id);
        if (estudiante == null || !tieneTexto(nuevoNombre)) {
            return false;
        }
        estudiante.setNombre(nuevoNombre);
        return true;
    }

    public boolean modificarPuntaje(String id, int nuevoPuntaje) {
        Estudiante estudiante = buscarEstudiante(id);
        if (estudiante == null) {
            return false;
        }
        // Un residente o un antiguo residente fuera de espera no cambia de cupo.
        if (!estudiante.tieneResidencia()
                && gestorEstudiantes.actualizarPuntajeEnEspera(id, nuevoPuntaje)) {
            return true;
        }
        estudiante.setPuntajeSocioeconomico(nuevoPuntaje);
        return true;
    }

    public boolean eliminarEstudiante(String id) {
        Estudiante estudiante = buscarEstudiante(id);
        if (estudiante == null) {
            return false;
        }
        if (!estudiante.tieneResidencia()) {
            boolean estabaEnEspera = gestorEstudiantes.quitarDeEspera(
                    estudiante.getPuntajeSocioeconomico(), id);
            if (gestorEstudiantes.eliminarRegistro(id)) {
                return true;
            }
            if (estabaEnEspera) {
                restaurarEspera(estudiante);
            }
            return false;
        }

        Residencia residencia = buscarCupo(estudiante.getIdResidencia());
        if (residencia == null || !id.equals(residencia.getIdEstudiante())
                || !gestorResidencias.liberar(residencia.getId())) {
            return false;
        }
        estudiante.setIdResidencia(null);
        if (!gestorEstudiantes.eliminarRegistro(id)) {
            if (!asignarCupo(residencia, estudiante)) {
                throw new IllegalStateException("No se pudo restaurar el cupo de " + id + ".");
            }
            return false;
        }
        asignarSiguienteEn(residencia);
        return true;
    }

    public boolean liberarResidencia(int idResidencia) {
        Residencia residencia = buscarCupo(idResidencia);
        if (residencia == null || residencia.estaDisponible()) {
            return false;
        }
        Estudiante titular = buscarEstudiante(residencia.getIdEstudiante());
        if (titular == null || !Integer.valueOf(idResidencia).equals(titular.getIdResidencia())
                || !gestorResidencias.liberar(idResidencia)) {
            return false;
        }

        // Sale del cupo, no del registro general. No se reinscribe a la espera.
        titular.setIdResidencia(null);
        asignarSiguienteEn(residencia);
        return true;
    }

    public boolean asignarSiguiente() {
        if (gestorEstudiantes.obtenerSiguienteEnEspera() == null) {
            return false;
        }
        return asignarSiguienteEn(gestorResidencias.buscarCupoDisponible());
    }

    public Estudiante[] obtenerTodos() {
        return gestorEstudiantes.obtenerTodos();
    }

    public Estudiante[] obtenerListaEspera() {
        return gestorEstudiantes.obtenerListaEspera();
    }

    public Estudiante[] obtenerResidentes() {
        Estudiante[] todos = obtenerTodos();
        int cantidad = 0;
        for (Estudiante estudiante : todos) {
            if (estudiante.tieneResidencia()) {
                cantidad++;
            }
        }
        Estudiante[] residentes = new Estudiante[cantidad];
        int indice = 0;
        for (Estudiante estudiante : todos) {
            if (estudiante.tieneResidencia()) {
                residentes[indice++] = estudiante;
            }
        }
        return residentes;
    }

    public int obtenerCuposDisponibles() {
        return gestorResidencias.contarDisponibles();
    }

    public int obtenerCuposOcupados() {
        return gestorResidencias.contarOcupados();
    }

    private Residencia buscarCupo(int idResidencia) {
        if (idResidencia < 1 || idResidencia > cantidadCupos) {
            return null;
        }
        return gestorResidencias.buscarPorId(idResidencia);
    }

    private boolean registrarEnEspera(Estudiante estudiante) {
        if (gestorEstudiantes.agregarAEspera(estudiante)) {
            return true;
        }
        if (!gestorEstudiantes.eliminarRegistro(estudiante.getId())) {
            throw new IllegalStateException("No se pudo deshacer el registro.");
        }
        return false;
    }

    private boolean asignarSiguienteEn(Residencia residencia) {
        if (residencia == null || !residencia.estaDisponible()) {
            return false;
        }
        Estudiante siguiente = gestorEstudiantes.extraerSiguienteEnEspera();
        if (siguiente == null) {
            return false;
        }
        if (asignarCupo(residencia, siguiente)) {
            return true;
        }
        restaurarEspera(siguiente);
        return false;
    }

    private boolean asignarCupo(Residencia residencia, Estudiante estudiante) {
        if (estudiante.tieneResidencia()
                || !gestorResidencias.asignar(residencia.getId(), estudiante.getId())) {
            return false;
        }
        estudiante.setIdResidencia(residencia.getId());
        return true;
    }

    private void restaurarEspera(Estudiante estudiante) {
        if (!gestorEstudiantes.agregarAEspera(estudiante)) {
            throw new IllegalStateException("No se pudo restaurar la espera de "
                    + estudiante.getId() + ".");
        }
    }

    private static boolean tieneTexto(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }
}
