/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestion;
import modelos.Residencia;
/**
 *
 * @author danie
 */
public class GestorResidencias {
    
    private Residencia[] residencias;

    public GestorResidencias(int cantidadCupos) {
        // Inicializar arreglo de residencias
    }

    public Residencia buscarCupoDisponible() {
        // Implementacion pendiente
        return null;
    }

    public Residencia buscarPorId(int id) {
        // Implementacion pendiente
        return null;
    }

    public boolean asignar(
        int idResidencia,
        String idEstudiante
    ) {
        // Implementacion pendiente
        return false;
    }

    public boolean liberar(int idResidencia) {
        // Implementacion pendiente
        return false;
    }

    public int contarDisponibles() {
        // Implementacion pendiente
        return 0;
    }

    public int contarOcupados() {
        // Implementacion pendiente
        return 0;
    }

    public Residencia[] obtenerResidencias() {
        return residencias;
    }
}
