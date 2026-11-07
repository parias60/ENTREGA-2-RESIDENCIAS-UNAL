/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelos.Estudiante;
/**
 *
 * @author danie
 */
public class AVLprioridad {
      private class Nodo {
        Estudiante estudiante;
        Nodo izquierdo;
        Nodo derecho;
        int altura;

        public Nodo(Estudiante estudiante) {
            this.estudiante = estudiante;
            this.izquierdo = null;
            this.derecho = null;
            this.altura = 1;
        }
    }

    private Nodo raiz;
    private int tamanio;

    public AVLprioridad() {
        raiz = null;
        tamanio = 0;
    }

    public boolean insertar(Estudiante estudiante) {
        // Implementacion pendiente
        return false;
    }

    public boolean eliminar(
        int puntajeAnterior,
        String id
    ) {
        // Implementacion pendiente
        return false;
    }

    public Estudiante obtenerMayorPrioridad() {
        // Implementacion pendiente
        return null;
    }

    public Estudiante extraerMayorPrioridad() {
        // Implementacion pendiente
        return null;
    }

    public Estudiante[] obtenerEnOrden() {
        // Implementacion pendiente
        return new Estudiante[0];
    }

    public int obtenerTamanio() {
        return tamanio;
    }

    // Metodos auxiliares privados:
    // comparar()
    // altura()
    // actualizarAltura()
    // factorEquilibrio()
    // rotarIzquierda()
    // rotarDerecha()
    // insertarRecursivo()
    // eliminarRecursivo()
    // obtenerMinimo()
    // recorrerEnOrden()
}
