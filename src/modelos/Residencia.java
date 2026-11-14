/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author danie
 */
public class Residencia {
    
    private int id;
    private String idEstudiante;

    public Residencia(int id) {
        this.id = id;
        this.idEstudiante = null;
    }

    public int getId() {
        return id;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public boolean estaDisponible() {
        return idEstudiante == null;
    }

    public boolean asignarEstudiante(String idEstudiante) {
    if (idEstudiante == null || idEstudiante.trim().isEmpty()) {
        return false;
    }

    if (!estaDisponible()) {
        return false;
    }

    this.idEstudiante = idEstudiante;
    return true;
    }

    public boolean liberar() {
    if (estaDisponible()) {
        return false;
    }

    this.idEstudiante = null;
    return true;
    }
}
