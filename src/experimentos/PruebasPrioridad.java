
package experimentos;

import estructuras.AVLprioridad;
import modelos.Estudiante;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class PruebasPrioridad {

    private static final int CANTIDAD_ARBOLES_CONSULTA = 4;

    public static void ejecutar(
            Estudiante[] estudiantes,
            int repeticiones,
            Resultados resultados) {

        int n = estudiantes.length;

        for (int rep = 1; rep <= repeticiones; rep++) {

            // --------------------------------
            // PRUEBA 1: INSERCIÓN
            // --------------------------------

            AVLprioridad arbolInsercion = new AVLprioridad();

            boolean correcto = true;

            long inicio = System.nanoTime();

            for (Estudiante estudiante : estudiantes) {

                correcto &= arbolInsercion.insertar(estudiante);
            }

            long fin = System.nanoTime();

            resultados.agregar(
                    "AVLPrioridad",
                    "Insercion",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(correcto);

            // --------------------------------
            // PRUEBA 2: CONSULTAR PRIORIDAD
            // --------------------------------

            /*
             * Se construyen cuatro árboles con los mismos estudiantes,
             * pero con diferentes órdenes de inserción.
             *
             * La construcción se realiza fuera del tiempo medido.
             */

            AVLprioridad[] arbolesConsulta =
                    new AVLprioridad[CANTIDAD_ARBOLES_CONSULTA];

            for (int i = 0; i < CANTIDAD_ARBOLES_CONSULTA; i++) {

                Estudiante[] orden = estudiantes.clone();

                Collections.shuffle(
                        Arrays.asList(orden),
                        new Random(12345L + rep * 31L + i)
                );

                arbolesConsulta[i] = construirArbol(orden);
            }

            long checksumConsulta = 0;

            int indiceArbol = 0;

            inicio = System.nanoTime();

            for (int i = 0; i < n; i++) {

                Estudiante prioritario =
                        arbolesConsulta[indiceArbol]
                                .obtenerMayorPrioridad();

                checksumConsulta += prioritario.getId().hashCode();

                indiceArbol++;

                if (indiceArbol == CANTIDAD_ARBOLES_CONSULTA) {
                    indiceArbol = 0;
                }
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "AVLPrioridad",
                    "ConsultarPrioridad",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(checksumConsulta);

            // --------------------------------
            // PRUEBA 3: EXTRAER PRIORIDAD
            // --------------------------------

            AVLprioridad arbolExtraccion =
                    construirArbol(estudiantes);

            long checksumExtraccion = 0;

            inicio = System.nanoTime();

            for (int i = 0; i < n; i++) {

                Estudiante prioritario =
                        arbolExtraccion.extraerMayorPrioridad();

                checksumExtraccion += prioritario.getId().hashCode();
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "AVLPrioridad",
                    "ExtraerPrioridad",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(checksumExtraccion);

            // Comprobación posterior a la medición.
            Resultados.conservar(
                    arbolExtraccion.obtenerTamanio() == 0
            );

            // --------------------------------
            // PRUEBA 4: ELIMINACIÓN
            // --------------------------------

            AVLprioridad arbolEliminacion =
                    construirArbol(estudiantes);

            boolean eliminacionCorrecta = true;

            inicio = System.nanoTime();

            for (Estudiante estudiante : estudiantes) {

                eliminacionCorrecta &=
                        arbolEliminacion.eliminar(
                                estudiante.getPuntajeSocioeconomico(),
                                estudiante.getId()
                        );
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "AVLPrioridad",
                    "Eliminacion",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(eliminacionCorrecta);

            Resultados.conservar(
                    arbolEliminacion.obtenerTamanio() == 0
            );

            // --------------------------------
            // PRUEBA 5: RECORRIDO
            // --------------------------------

            AVLprioridad arbolRecorrido =
                    construirArbol(estudiantes);

            Estudiante[] recorrido;

            inicio = System.nanoTime();

            recorrido = arbolRecorrido.obtenerEnOrden();

            fin = System.nanoTime();

            resultados.agregar(
                    "AVLPrioridad",
                    "Recorrido",
                    n,
                    rep,
                    1,
                    fin - inicio
            );

            Resultados.conservar(recorrido);
        }

        System.out.println(
                "Pruebas de prioridad finalizadas para n = " + n
        );
    }

    private static AVLprioridad construirArbol(
            Estudiante[] estudiantes) {

        AVLprioridad arbol = new AVLprioridad();

        for (Estudiante estudiante : estudiantes) {
            arbol.insertar(estudiante);
        }

        return arbol;
    }
}
