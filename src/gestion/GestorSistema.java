/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;
import modelos.Estudiante;
/**
 *
 * @author danie
 */
public class GestorSistema {
     private GestorEstudiantes gestorEstudiantes;
    private GestorResidencias gestorResidencias;

    public GestorSistema() {
        // Inicializar los gestores
    }

    public boolean registrarEstudiante(Estudiante estudiante) {
        // Implementacion pendiente
        return false;
    }

    public Estudiante buscarEstudiante(String id) {
        // Implementacion pendiente
        return null;
    }

    public boolean modificarNombre(
        String id,
        String nuevoNombre
    ) {
        // Implementacion pendiente
        return false;
    }

    public boolean modificarPuntaje(
        String id,
        int nuevoPuntaje
    ) {
        // Implementacion pendiente
        return false;
    }

    public boolean eliminarEstudiante(String id) {
        // Implementacion pendiente
        return false;
    }

    public boolean liberarResidencia(int idResidencia) {
        // Implementacion pendiente
        return false;
    }

    public boolean asignarSiguiente() {
        // Implementacion pendiente
        return false;
    }

    public Estudiante[] obtenerTodos() {
        // Implementacion pendiente
        return new Estudiante[0];
    }

    public Estudiante[] obtenerListaEspera() {
        // Implementacion pendiente
        return new Estudiante[0];
    }

    public Estudiante[] obtenerResidentes() {
        // Implementacion pendiente
        return new Estudiante[0];
    }

    public int obtenerCuposDisponibles() {
        // Implementacion pendiente
        return 0;
    }

    public int obtenerCuposOcupados() {
        // Implementacion pendiente
        return 0;
    }
}
