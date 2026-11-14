
package experimentos;

import estructuras.AVLestudiantes;
import modelos.Estudiante;

public class PruebasAVL {

    public static void ejecutar(
            Estudiante[] estudiantes,
            int repeticiones,
            Resultados resultados) {

        if (estudiantes == null || estudiantes.length == 0) {
            throw new IllegalArgumentException(
                    "Debe existir al menos un estudiante."
            );
        }

        int n = estudiantes.length;

        for (int rep = 1; rep <= repeticiones; rep++) {

            // =====================================
            // PRUEBA 1: INSERCION
            // =====================================

            AVLestudiantes arbolInsercion =
                    new AVLestudiantes();

            boolean insercionCorrecta = true;

            long inicio = System.nanoTime();

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
            Resultados.conservar(
                    arbolInsercion.obtenerTamanio() == n
            );


            // =====================================
            // PRUEBA 2: BUSQUEDA
            // =====================================

            // La construcción no forma parte del tiempo medido.
            AVLestudiantes arbolBusqueda =
                    construirArbol(estudiantes);

            boolean busquedaCorrecta = true;
            long checksumBusqueda = 0;

            inicio = System.nanoTime();

            for (Estudiante estudiante : estudiantes) {

                Estudiante encontrado =
                        arbolBusqueda.buscar(estudiante.getId());

                if (encontrado == null) {
                    busquedaCorrecta = false;
                } else {
                    checksumBusqueda +=
                            encontrado.getId().hashCode();
                }
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

            Resultados.conservar(busquedaCorrecta);
            Resultados.conservar(checksumBusqueda);


            // =====================================
            // PRUEBA 3: ELIMINACION
            // =====================================

            // La construcción no forma parte del tiempo medido.
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
            Resultados.conservar(
                    arbolEliminacion.obtenerTamanio() == 0
            );


            // =====================================
            // PRUEBA 4: RECORRIDO EN ORDEN
            // =====================================

            // La construcción no forma parte del tiempo medido.
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

            // Validaciones fuera del tiempo medido.
            boolean recorridoCorrecto =
                    recorrido != null && recorrido.length == n;

            long checksumRecorrido = 0;

            if (recorridoCorrecto) {

                for (int i = 0; i < recorrido.length; i++) {

                    if (recorrido[i] == null) {
                        recorridoCorrecto = false;
                        break;
                    }

                    checksumRecorrido +=
                            recorrido[i].getId().hashCode();

                    if (i > 0 &&
                            recorrido[i - 1].getId().compareTo(
                                    recorrido[i].getId()
                            ) >= 0) {

                        recorridoCorrecto = false;
                        break;
                    }
                }
            }

            Resultados.conservar(recorridoCorrecto);
            Resultados.conservar(checksumRecorrido);
        }

        System.out.println(
                "Pruebas AVL finalizadas para n = " + n
        );
    }


    // Construye un árbol sin incluir ese tiempo en las mediciones.
    private static AVLestudiantes construirArbol(
            Estudiante[] estudiantes) {

        AVLestudiantes arbol = new AVLestudiantes();

        for (Estudiante estudiante : estudiantes) {
            arbol.insertar(estudiante);
        }

        return arbol;
    }
}
