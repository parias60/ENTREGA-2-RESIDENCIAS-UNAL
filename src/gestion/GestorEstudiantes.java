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
        // Inicializar ambos arboles
    }

    public boolean registrar(Estudiante estudiante) {
        // Implementacion pendiente
        return false;
    }

    public Estudiante buscar(String id) {
        // Implementacion pendiente
        return null;
    }

    public boolean eliminarRegistro(String id) {
        // Implementacion pendiente
        return false;
    }

    public boolean agregarAEspera(Estudiante estudiante) {
        // Implementacion pendiente
        return false;
    }

    public boolean quitarDeEspera(
        int puntajeAnterior,
        String id
    ) {
        // Implementacion pendiente
        return false;
    }

    public Estudiante obtenerSiguienteEnEspera() {
        // Implementacion pendiente
        return null;
    }

    public Estudiante extraerSiguienteEnEspera() {
        // Implementacion pendiente
        return null;
    }

    public boolean actualizarPuntajeEnEspera(
        String id,
        int nuevoPuntaje
    ) {
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
}
