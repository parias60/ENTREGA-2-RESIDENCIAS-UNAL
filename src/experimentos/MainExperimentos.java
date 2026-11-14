
package experimentos;

import modelos.Estudiante;

public class MainExperimentos {

    // Tamaños de entrada.
    private static final int[] TAMANIOS = {
        1000,
        5000,
        10000,
        50000,
        100000
    };

    private static final int REPETICIONES = 5;
    private static final int CALENTAMIENTO = 2;

    public static void main(String[] args) {

        long inicioTotal = System.nanoTime();

        System.out.println("======================================");
        System.out.println("   EXPERIMENTOS - RESIDENCIAS UNAL");
        System.out.println("======================================");

        System.out.println("Repeticiones: " + REPETICIONES);
        System.out.println("Calentamientos: " + CALENTAMIENTO);

        try (Resultados resultados =
                new Resultados("resultados.csv")) {

            for (int cantidad : TAMANIOS) {

                long inicioTamano = System.nanoTime();

                System.out.println("\n--------------------------------------");
                System.out.println("Tamaño de entrada: " + cantidad);
                System.out.println("--------------------------------------");

                // Generación de datos fuera de las mediciones.
                Estudiante[] estudiantes =
                        GeneradorDatos.generarEstudiantes(
                                cantidad,
                                12345L
                        );

                System.out.println("Datos generados: " + estudiantes.length);

                // =====================================
                // 1. CALENTAMIENTO DE LA JVM
                // =====================================

                System.out.println("\nCalentando JVM...");

                Resultados descarte = new Resultados();

                for (int i = 1; i <= CALENTAMIENTO; i++) {

                    System.out.println(
                            "Calentamiento " + i + " de " + CALENTAMIENTO
                    );

                    PruebasAVL.ejecutar(
                            estudiantes, 1, descarte
                    );

                    PruebasPrioridad.ejecutar(
                            estudiantes, 1, descarte
                    );

                    PruebasResidencias.ejecutar(
                            estudiantes, 1, descarte
                    );
                }

                // =====================================
                // 2. MEDICIONES REALES
                // =====================================

                System.out.println("\nIniciando mediciones...");

                PruebasAVL.ejecutar(
                        estudiantes,
                        REPETICIONES,
                        resultados
                );

                System.out.println("Pruebas AVL terminadas.");

                PruebasPrioridad.ejecutar(
                        estudiantes,
                        REPETICIONES,
                        resultados
                );

                System.out.println("Pruebas de prioridad terminadas.");

                PruebasResidencias.ejecutar(
                        estudiantes,
                        REPETICIONES,
                        resultados
                );

                System.out.println("Pruebas de residencias terminadas.");

                long finTamano = System.nanoTime();

                System.out.printf(
                        "\nTiempo total para n = %d: %.2f segundos%n",
                        cantidad,
                        (finTamano - inicioTamano) / 1_000_000_000.0
                );

                System.out.println(
                        "Finalizadas las pruebas para "
                        + cantidad + " elementos."
                );
            }

            long finTotal = System.nanoTime();

            System.out.println("\n======================================");
            System.out.println("TODOS LOS EXPERIMENTOS FINALIZARON");
            System.out.printf(
                    "Tiempo total: %.2f segundos%n",
                    (finTotal - inicioTotal) / 1_000_000_000.0
            );
            System.out.println("Resultados guardados en resultados.csv");
            System.out.println("======================================");

        } catch (Exception e) {

            System.err.println(
                    "\nERROR DURANTE LOS EXPERIMENTOS:"
            );

            e.printStackTrace();
        }
    }
}
