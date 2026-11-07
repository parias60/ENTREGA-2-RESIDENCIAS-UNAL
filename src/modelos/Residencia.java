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

    public void asignarEstudiante(String idEstudiante) {
        //FALTA IMPLEMENTAR
        this.idEstudiante = idEstudiante;
    }

    public void liberar() {
        //FALTA IMPLEMENTAR
        this.idEstudiante = null;
    }
}
