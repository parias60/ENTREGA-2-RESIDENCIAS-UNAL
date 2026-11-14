package experimentos;

import estructuras.AVLestudiantes;
import modelos.Estudiante;

public class PruebasAVL {

    public static void ejecutar(
            Estudiante[] estudiantes,
            int repeticiones,
            Resultados resultados) {

        int n = estudiantes.length;

        for (int rep = 1; rep <= repeticiones; rep++) {

            // --------------------------------
            // PRUEBA 1: INSERCIÓN
            // --------------------------------

            AVLestudiantes arbolInsercion =
                    new AVLestudiantes();

            long inicio = System.nanoTime();

            boolean insercionCorrecta = true;

            for (Estudiante estudiante : estudiantes) {
                insercionCorrecta &=
                        arbolInsercion.insertar(estudiante);
            }

            long fin = System.nanoTime();

            resultados.agregar(
                    "AVL",
                    "Insercion",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(insercionCorrecta);

            // --------------------------------
            // PRUEBA 2: BÚSQUEDA
            // --------------------------------

            AVLestudiantes arbolBusqueda =
                    construirArbol(estudiantes);

            Estudiante encontrado = null;

            inicio = System.nanoTime();

            for (Estudiante estudiante : estudiantes) {

                encontrado = arbolBusqueda.buscar(
                        estudiante.getId()
                );
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "AVL",
                    "Busqueda",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(encontrado);

            // --------------------------------
            // PRUEBA 3: ELIMINACIÓN
            // --------------------------------

            AVLestudiantes arbolEliminacion =
                    construirArbol(estudiantes);

            boolean eliminacionCorrecta = true;

            inicio = System.nanoTime();

            for (Estudiante estudiante : estudiantes) {

                eliminacionCorrecta &=
                        arbolEliminacion.eliminar(
                                estudiante.getId()
                        );
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "AVL",
                    "Eliminacion",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(eliminacionCorrecta);

            // --------------------------------
            // PRUEBA 4: RECORRIDO
            // --------------------------------

            AVLestudiantes arbolRecorrido =
                    construirArbol(estudiantes);

            Estudiante[] recorrido;

            inicio = System.nanoTime();

            recorrido = arbolRecorrido.obtenerEnOrden();

            fin = System.nanoTime();

            resultados.agregar(
                    "AVL",
                    "Recorrido",
                    n,
                    rep,
                    1,
                    fin - inicio
            );

            Resultados.conservar(recorrido);
        }

        System.out.println(
                "Pruebas AVL finalizadas para n = " + n
        );
    }

    private static AVLestudiantes construirArbol(
            Estudiante[] estudiantes) {

        AVLestudiantes arbol = new AVLestudiantes();

        for (Estudiante estudiante : estudiantes) {
            arbol.insertar(estudiante);
        }

        return arbol;
    }
}