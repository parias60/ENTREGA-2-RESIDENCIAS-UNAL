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
        if (estudiante == null || estudiante.getId() == null) {
            return false;
        }
        boolean[] insertado = {false};
        raiz = insertarRecursivo(raiz, estudiante, insertado);
        if (insertado[0]) {
            tamanio++;
        }
        return insertado[0];
    }

    public boolean eliminar(
            int puntajeAnterior,
            String id
    ) {
        if (id == null || raiz == null) {
            return false;
        }
        boolean[] eliminado = {false};
        raiz = eliminarRecursivo(raiz, puntajeAnterior, id, eliminado);
        if (eliminado[0]) {
            tamanio--;
        }
        return eliminado[0];
    }

    public Estudiante obtenerMayorPrioridad() {
        if (raiz == null) {
            return null;
        }
        return obtenerMinimo(raiz).estudiante;
    }

    public Estudiante extraerMayorPrioridad() {
        Estudiante prioritario = obtenerMayorPrioridad();
        if (prioritario == null) {
            return null;
        }
        eliminar(prioritario.getPuntajeSocioeconomico(), prioritario.getId());
        return prioritario;
    }

    public Estudiante[] obtenerEnOrden() {
        Estudiante[] resultado = new Estudiante[tamanio];
        int[] indice = {0};
        recorrerEnOrden(raiz, resultado, indice);
        return resultado;
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

        if (fe > 1 && factorEquilibrio(nodo.izquierdo) >= 0) {
            return rotarDerecha(nodo);
        }
        if (fe > 1 && factorEquilibrio(nodo.izquierdo) < 0) {
            nodo.izquierdo = rotarIzquierda(nodo.izquierdo);
            return rotarDerecha(nodo);
        }
        if (fe < -1 && factorEquilibrio(nodo.derecho) <= 0) {
            return rotarIzquierda(nodo);
        }
        if (fe < -1 && factorEquilibrio(nodo.derecho) > 0) {
            nodo.derecho = rotarDerecha(nodo.derecho);
            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    private Nodo insertarRecursivo(Nodo actual, Estudiante estudiante, boolean[] insertado) {
        if (actual == null) {
            insertado[0] = true;
            return new Nodo(estudiante);
        }

        int cmp = comparar(
                estudiante.getPuntajeSocioeconomico(), estudiante.getId(),
                actual.estudiante.getPuntajeSocioeconomico(), actual.estudiante.getId()
        );

        if (cmp < 0) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, estudiante, insertado);
        } else if (cmp > 0) {
            actual.derecho = insertarRecursivo(actual.derecho, estudiante, insertado);
        } else {
            insertado[0] = false;
            return actual;
        }

        return balancear(actual);
    }

    private Nodo eliminarRecursivo(Nodo actual, int puntaje, String id, boolean[] eliminado) {
        if (actual == null) {
            return null;
        }

        int cmp = comparar(
                puntaje, id,
                actual.estudiante.getPuntajeSocioeconomico(), actual.estudiante.getId()
        );

        if (cmp < 0) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, puntaje, id, eliminado);
        } else if (cmp > 0) {
            actual.derecho = eliminarRecursivo(actual.derecho, puntaje, id, eliminado);
        } else {
            eliminado[0] = true;

            if (actual.izquierdo == null || actual.derecho == null) {
                return (actual.izquierdo != null) ? actual.izquierdo : actual.derecho;
            }

            Nodo sucesor = obtenerMinimo(actual.derecho);
            actual.estudiante = sucesor.estudiante;

            boolean[] dummy = {false};
            actual.derecho = eliminarRecursivo(
                    actual.derecho,
                    sucesor.estudiante.getPuntajeSocioeconomico(),
                    sucesor.estudiante.getId(),
                    dummy
            );
        }

        return balancear(actual);
    }

    private Nodo obtenerMinimo(Nodo nodo) {
        Nodo actual = nodo;
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    private void recorrerEnOrden(Nodo actual, Estudiante[] resultado, int[] indice) {
        if (actual != null) {
            recorrerEnOrden(actual.izquierdo, resultado, indice);
            resultado[indice[0]++] = actual.estudiante;
            recorrerEnOrden(actual.derecho, resultado, indice);
        }
    }
}