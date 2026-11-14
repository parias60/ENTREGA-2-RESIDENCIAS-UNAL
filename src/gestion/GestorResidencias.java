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
    if (cantidadCupos < 0) {
        throw new IllegalArgumentException(
            "La cantidad de cupos no puede ser negativa."
        );
    }

    residencias = new Residencia[cantidadCupos];

    for (int i = 0; i < cantidadCupos; i++) {
        residencias[i] = new Residencia(i + 1);
        }
    }

    public Residencia buscarCupoDisponible() {
    for (Residencia residencia : residencias) {
        if (residencia.estaDisponible()) {
            return residencia;
        }
    }

    return null;
    }

    public Residencia buscarPorId(int id) {
    for (Residencia residencia : residencias) {
        if (residencia.getId() == id) {
            return residencia;
        }
    }

    return null;
    }

    public boolean asignar(int idResidencia, String idEstudiante) {
    Residencia residencia = buscarPorId(idResidencia);

    if (residencia == null) {
        return false;
    }

    return residencia.asignarEstudiante(idEstudiante);
    }

    public boolean liberar(int idResidencia) {
    Residencia residencia = buscarPorId(idResidencia);

    if (residencia == null) {
        return false;
    }

    return residencia.liberar();
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
