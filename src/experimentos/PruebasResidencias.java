
package experimentos;

import gestion.GestorResidencias;
import modelos.Residencia;
import modelos.Estudiante;

public class PruebasResidencias {

    // Máximo de operaciones medidas por prueba y repetición.
    private static final int MAX_OPERACIONES = 1000;

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

        // Se limita la cantidad de operaciones para evitar
        // experimentos excesivamente largos.
        int operaciones = Math.min(n, MAX_OPERACIONES);

        for (int rep = 1; rep <= repeticiones; rep++) {

            // --------------------------------
            // PRUEBA 1: INICIALIZACIÓN
            // --------------------------------

            long inicio = System.nanoTime();

            GestorResidencias gestor =
                    new GestorResidencias(n);

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

            boolean busquedaCorrecta = true;

            long checksumBusqueda = 0;

            inicio = System.nanoTime();

            for (int i = 0; i < operaciones; i++) {

                int indice = indiceMuestreado(
                        i, n, operaciones
                );

                Residencia encontrada =
                        gestorBusqueda.buscarPorId(indice + 1);

                if (encontrada == null) {
                    busquedaCorrecta = false;
                } else {
                    checksumBusqueda += encontrada.getId();
                }
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "BusquedaPorID",
                    n,
                    rep,
                    operaciones,
                    fin - inicio
            );

            Resultados.conservar(busquedaCorrecta);
            Resultados.conservar(checksumBusqueda);

            // --------------------------------
            // PRUEBA 3: BUSCAR CUPO DISPONIBLE
            // --------------------------------

            GestorResidencias gestorCupos =
                    new GestorResidencias(n);

            Residencia[] cupos =
                    gestorCupos.obtenerResidencias();

            /*
             * Ocupamos todos los cupos excepto el último.
             *
             * La preparación queda fuera del cronómetro.
             * Así, cada búsqueda debe recorrer prácticamente
             * todo el arreglo para encontrar el último cupo.
             */

            for (int i = 0; i < n - 1; i++) {

                cupos[i].asignarEstudiante(
                        estudiantes[i].getId()
                );
            }

            boolean busquedaCupoCorrecta = true;

            long checksumCupos = 0;

            inicio = System.nanoTime();

            for (int i = 0; i < operaciones; i++) {

                Residencia disponible =
                        gestorCupos.buscarCupoDisponible();

                if (disponible == null) {
                    busquedaCupoCorrecta = false;
                } else {
                    checksumCupos += disponible.getId();
                }
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "BuscarCupoDisponible",
                    n,
                    rep,
                    operaciones,
                    fin - inicio
            );

            Resultados.conservar(busquedaCupoCorrecta);
            Resultados.conservar(checksumCupos);

            // --------------------------------
            // PRUEBA 4: ASIGNACIÓN
            // --------------------------------

            GestorResidencias gestorAsignacion =
                    new GestorResidencias(n);

            boolean asignacionCorrecta = true;

            inicio = System.nanoTime();

            for (int i = 0; i < operaciones; i++) {

                int indice = indiceMuestreado(
                        i, n, operaciones
                );

                asignacionCorrecta &=
                        gestorAsignacion.asignar(
                                indice + 1,
                                estudiantes[indice].getId()
                        );
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "Asignacion",
                    n,
                    rep,
                    operaciones,
                    fin - inicio
            );

            Resultados.conservar(asignacionCorrecta);

            // --------------------------------
            // PRUEBA 5: LIBERACIÓN
            // --------------------------------

            GestorResidencias gestorLiberacion =
                    new GestorResidencias(n);

            Residencia[] cuposLiberacion =
                    gestorLiberacion.obtenerResidencias();

            /*
             * Ocupamos previamente los cupos que se van
             * a liberar. Esta preparación no se mide.
             */

            for (int i = 0; i < operaciones; i++) {

                int indice = indiceMuestreado(
                        i, n, operaciones
                );

                cuposLiberacion[indice].asignarEstudiante(
                        estudiantes[indice].getId()
                );
            }

            boolean liberacionCorrecta = true;

            inicio = System.nanoTime();

            for (int i = 0; i < operaciones; i++) {

                int indice = indiceMuestreado(
                        i, n, operaciones
                );

                liberacionCorrecta &=
                        gestorLiberacion.liberar(indice + 1);
            }

            fin = System.nanoTime();

            resultados.agregar(
                    "Residencias",
                    "Liberacion",
                    n,
                    rep,
                    operaciones,
                    fin - inicio
            );

            Resultados.conservar(liberacionCorrecta);
        }

        System.out.println(
                "Pruebas de residencias finalizadas para n = " + n
        );
    }

    /*
     * Selecciona posiciones distribuidas uniformemente
     * a lo largo del arreglo.
     *
     * Cuando se utiliza una sola operación, selecciona
     * la primera posición.
     */
    private static int indiceMuestreado(
            int i,
            int n,
            int operaciones) {

        if (operaciones == 1) {
            return 0;
        }

        return (int) (
                (long) i * (n - 1) / (operaciones - 1)
        );
    }
}
