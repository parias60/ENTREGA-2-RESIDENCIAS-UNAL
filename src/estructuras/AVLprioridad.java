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

    private int comparar(int puntaje1, String id1, int puntaje2, String id2) {
        if (puntaje1 < puntaje2) {
            return -1;
        }
        if (puntaje1 > puntaje2) {
            return 1;
        }
        return id1.compareTo(id2);
    }

    private int altura(Nodo nodo) {
        return (nodo == null) ? 0 : nodo.altura;
    }

    private void actualizarAltura(Nodo nodo) {
        if (nodo != null) {
            int altIzq = altura(nodo.izquierdo);
            int altDer = altura(nodo.derecho);
            nodo.altura = 1 + (altIzq > altDer ? altIzq : altDer);
        }
    }

    private int factorEquilibrio(Nodo nodo) {
        return (nodo == null) ? 0 : altura(nodo.izquierdo) - altura(nodo.derecho);
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierdo;
        Nodo T2 = x.derecho;

        x.derecho = y;
        y.izquierdo = T2;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecho;
        Nodo T2 = y.izquierdo;

        y.izquierdo = x;
        x.derecho = T2;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }

    private Nodo balancear(Nodo nodo) {
        if (nodo == null) {
            return null;
        }
        actualizarAltura(nodo);
        int fe = factorEquilibrio(nodo);

        // Rotacion simple derecha (LL)
        if (fe > 1 && factorEquilibrio(nodo.izquierdo) >= 0) {
            return rotarDerecha(nodo);
        }
        // Rotacion doble izquierda-derecha (LR)
        if (fe > 1 && factorEquilibrio(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }
        // Rotacion simple izquierda (RR)
        if (fe < -1 && factorEquilibrio(nodo.derecho) <= 0) {
            return rotarIzquierda(nodo);
        }
        // Rotacion doble derecha-izquierda (RL)
        if (fe < -1 && factorEquilibrio(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }
}