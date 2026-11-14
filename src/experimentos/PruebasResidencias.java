package experimentos;

import gestion.GestorResidencias;
import modelos.Residencia;
import modelos.Estudiante;

public class PruebasResidencias {

    public static void ejecutar(
            Estudiante[] estudiantes,
            int repeticiones,
            Resultados resultados) {

        int n = estudiantes.length;

        for (int rep = 1; rep <= repeticiones; rep++) {

            // --------------------------------
            // PRUEBA 1: INICIALIZACIÓN
            // --------------------------------

            GestorResidencias gestor;

            long inicio = System.nanoTime();

            gestor = new GestorResidencias(n);

            long fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "Inicializacion",
                    n,
                    rep,
                    1,
                    fin - inicio
            );

            Resultados.conservar(gestor);

            // --------------------------------
            // PRUEBA 2: BÚSQUEDA POR ID
            // --------------------------------

            GestorResidencias gestorBusqueda =
                    new GestorResidencias(n);

            Residencia residencia = null;

            inicio = System.nanoTime();

            for (int i = 1; i <= n; i++) {

                residencia =
                        gestorBusqueda.buscarPorId(i);
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "BusquedaPorID",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(residencia);

            // --------------------------------
            // PRUEBA 3: BUSCAR CUPO DISPONIBLE
            // --------------------------------

            GestorResidencias gestorCupos =
                    new GestorResidencias(n);

            // Ocupamos todos menos el último.
            for (int i = 1; i < n; i++) {

                gestorCupos.asignar(
                        i,
                        estudiantes[i - 1].getId()
                );
            }

            Residencia disponible = null;

            inicio = System.nanoTime();

            for (int i = 0; i < n; i++) {

                disponible =
                        gestorCupos.buscarCupoDisponible();
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "BuscarCupoDisponible",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(disponible);

            // --------------------------------
            // PRUEBA 4: ASIGNACIÓN
            // --------------------------------

            GestorResidencias gestorAsignacion =
                    new GestorResidencias(n);

            boolean asignacionCorrecta = true;

            inicio = System.nanoTime();

            for (int i = 1; i <= n; i++) {

                asignacionCorrecta &=
                        gestorAsignacion.asignar(
                                i,
                                estudiantes[i - 1].getId()
                        );
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "Asignacion",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(asignacionCorrecta);

            // --------------------------------
            // PRUEBA 5: LIBERACIÓN
            // --------------------------------

            GestorResidencias gestorLiberacion =
                    new GestorResidencias(n);

            // Preparación fuera del tiempo medido.
            for (int i = 1; i <= n; i++) {

                gestorLiberacion.asignar(
                        i,
                        estudiantes[i - 1].getId()
                );
            }

            boolean liberacionCorrecta = true;

            inicio = System.nanoTime();

            for (int i = 1; i <= n; i++) {

                liberacionCorrecta &=
                        gestorLiberacion.liberar(i);
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "Liberacion",
                    n,
                    rep,
                    n,
                    fin - inicio
            );

            Resultados.conservar(liberacionCorrecta);
        }

        System.out.println(
                "Pruebas de residencias finalizadas para n = " + n
        );
    }
}