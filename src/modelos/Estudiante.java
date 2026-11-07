/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author danie
 */
public class Estudiante {
    private String id;
    private String nombre;
    private int puntajeSocioeconomico;
    private String idResidencia;

    public Estudiante(String id, String nombre, int puntajeSocioeconomico) {
        this.id = id;
        this.nombre = nombre;
        this.puntajeSocioeconomico = puntajeSocioeconomico;
        this.idResidencia = null;
    }
    
      public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntajeSocioeconomico() {
        return puntajeSocioeconomico;
    }

    public String getIdResidencia() {
        return idResidencia;
    }

    public boolean tieneResidencia() {
        return idResidencia != null;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPuntajeSocioeconomico(int puntajeSocioeconomico) {
        this.puntajeSocioeconomico = puntajeSocioeconomico;
    }

    public void setIdResidencia(String idResidencia) {
        this.idResidencia = idResidencia;
    }

}
