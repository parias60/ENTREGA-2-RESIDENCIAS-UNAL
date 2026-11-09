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

public class AVLestudiantes {

    // Nodo interno del arbol
    private class Nodo {
        Estudiante estudiante;
        Nodo izquierdo;
        Nodo derecho;

        public Nodo(Estudiante estudiante) {
            this.estudiante = estudiante;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private Nodo raiz;

    public AVLestudiantes() {
        raiz = null;
    }

    // INSERTAR
    public boolean insertar(Estudiante estudiante) {

        if (estudiante == null || estudiante.getId() == null) {
            return false;
        }

        boolean[] insertado = {false};

        raiz = insertarRecursivo(raiz, estudiante, insertado);

        return insertado[0];
    }

    private Nodo insertarRecursivo(
            Nodo actual,
            Estudiante estudiante,
            boolean[] insertado) {

        if (actual == null) {
            insertado[0] = true;
            return new Nodo(estudiante);
        }

        int comparacion = estudiante.getId().compareTo(
                actual.estudiante.getId());

        if (comparacion < 0) {
            actual.izquierdo = insertarRecursivo(
                    actual.izquierdo, estudiante, insertado);

        } else if (comparacion > 0) {
            actual.derecho = insertarRecursivo(
                    actual.derecho, estudiante, insertado);

        } else {
            return actual; // ID duplicado
        }

        return actual;
    }

    // BUSCAR
    public Estudiante buscar(String id) {

        if (id == null) {
            return null;
        }

        return buscarRecursivo(raiz, id);
    }

    private Estudiante buscarRecursivo(Nodo actual, String id) {

        if (actual == null) {
            return null;
        }

        int comparacion = id.compareTo(actual.estudiante.getId());

        if (comparacion == 0) {
            return actual.estudiante;
        }

        if (comparacion < 0) {
            return buscarRecursivo(actual.izquierdo, id);
        }

        return buscarRecursivo(actual.derecho, id);
    }

    public boolean eliminar(String id) {
        // Implementacion pendiente
        return false;
    }

    public boolean contiene(String id) {
        // Implementacion pendiente
        return false;
    }

    public Estudiante[] obtenerEnOrden() {
        // Implementacion pendiente
        return new Estudiante[0];
    }

    public int obtenerTamanio() {
        return obtenerTamanio();
    }

    // Metodos auxiliares privados:
    // altura()
    // actualizarAltura()
    // factorEquilibrio()
    // rotarIzquierda()
    // rotarDerecha()
    // insertarRecursivo()
    // buscarRecursivo()
    // eliminarRecursivo()
    // recorrerEnOrden()

 }
