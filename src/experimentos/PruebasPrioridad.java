package experimentos;

import estructuras.AVLprioridad;
import modelos.Estudiante;

public class PruebasPrioridad {

    public static void ejecutar(
            Estudiante[] estudiantes,
            int repeticiones,
            Resultados resultados) {

        int n = estudiantes.length;

        for (int rep = 1; rep <= repeticiones; rep++) {

            // --------------------------------
            // PRUEBA 1: INSERCIÓN
            // --------------------------------

            AVLprioridad arbolInsercion =
                    new AVLprioridad();

            boolean correcto = true;

            long inicio = System.nanoTime();

            for (Estudiante estudiante : estudiantes) {

                correcto &= arbolInsercion.insertar(
                        estudiante
                );
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

            AVLprioridad arbolConsulta =
                    construirArbol(estudiantes);

            Estudiante prioritario = null;

            inicio = System.nanoTime();

            for (int i = 0; i < n; i++) {

                prioritario =
                        arbolConsulta.obtenerMayorPrioridad();
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

            Resultados.conservar(prioritario);

            // --------------------------------
            // PRUEBA 3: EXTRAER PRIORIDAD
            // --------------------------------

            AVLprioridad arbolExtraccion =
                    construirArbol(estudiantes);

            prioritario = null;

            inicio = System.nanoTime();

            for (int i = 0; i < n; i++) {

                prioritario =
                        arbolExtraccion.extraerMayorPrioridad();
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

            Resultados.conservar(prioritario);

            // --------------------------------
            // PRUEBA 4: ELIMINACIÓN
            // --------------------------------

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