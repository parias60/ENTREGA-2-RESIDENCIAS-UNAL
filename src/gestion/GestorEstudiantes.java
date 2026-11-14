/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;
import modelos.Estudiante;
import estructuras.AVLestudiantes;
import estructuras.AVLprioridad;
/**
 *
 * @author danie
 */
public class GestorEstudiantes {
    private AVLestudiantes arbolEstudiantes;
    private AVLprioridad arbolPrioridad;

    public GestorEstudiantes() {
        this.arbolEstudiantes = new AVLestudiantes();
        this.arbolPrioridad = new AVLprioridad();
    }

    public boolean registrar(Estudiante estudiante) {
        if (estudiante == null || estudiante.getId() == null) {
            return false;
        }
        
        return arbolEstudiantes.insertar(estudiante);
    }

    public Estudiante buscar(String id) {
        if (id == null) {
            return null;
        }
        return arbolEstudiantes.buscar(id);
    }

    public boolean eliminarRegistro(String id) {
        if (id == null) {
            return false;
        }
        
        // si el estudiante existe para saber si estaba en espera también
        Estudiante estudiante = arbolEstudiantes.buscar(id);
        if (estudiante == null) {
            return false;
        }

        // Si el estudiante estaba en espera (no tiene residencia asignada), 
        // quiarlo del de prioridad
        if (!estudiante.tieneResidencia()) {
            arbolPrioridad.eliminar(estudiante.getPuntajeSocioeconomico(), id);
        }

        return arbolEstudiantes.eliminar(id);
    }

    public boolean agregarAEspera(Estudiante estudiante) {
        if (estudiante == null) {
            return false;
        }
        return arbolPrioridad.insertar(estudiante);
    }

    public boolean quitarDeEspera(int puntajeAnterior,String id){
        if (id == null) {
            return false;
        }
        return arbolPrioridad.eliminar(puntajeAnterior, id);
    }

    public Estudiante obtenerSiguienteEnEspera() {
        return arbolPrioridad.obtenerMayorPrioridad();
    }

    public Estudiante extraerSiguienteEnEspera() {
        return arbolPrioridad.extraerMayorPrioridad();
    }

    public boolean actualizarPuntajeEnEspera(String id,int nuevoPuntaje) {
        if (id == null) {
            return false;
        }

        Estudiante estudiante = arbolEstudiantes.buscar(id);
        if (estudiante == null || estudiante.tieneResidencia()) {
            return false; // No existe o ya tiene residencia (por lo tanto no está en espera)
        }

        int puntajeAnterior = estudiante.getPuntajeSocioeconomico();

        // 1. Eliminar del árbol de prioridad usando el puntaje anterior
        boolean eliminadoDeEspera = arbolPrioridad.eliminar(puntajeAnterior, id);
        if (!eliminadoDeEspera) {
            return false;
        }

        // 2. Modificar el puntaje en el objeto estudiante
        estudiante.setPuntajeSocioeconomico(nuevoPuntaje);

        // 3. Volver a insertarlo en el árbol de prioridad con su nueva clave
        return arbolPrioridad.insertar(estudiante);
    }

    public Estudiante[] obtenerTodos() {
        return arbolEstudiantes.obtenerEnOrden();
    }

    public Estudiante[] obtenerListaEspera() {
        // Implementacion pendiente
        return arbolPrioridad.obtenerEnOrden();
    }
}
