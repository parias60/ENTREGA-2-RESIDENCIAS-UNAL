package experimentos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;

public class Resultados {

    private static class Registro {

        String estructura;
        String operacion;
        int cantidad;
        int repeticion;
        int operaciones;
        long tiempoTotal;

        Registro(String estructura, String operacion,
                int cantidad, int repeticion,
                int operaciones, long tiempoTotal) {

            this.estructura = estructura;
            this.operacion = operacion;
            this.cantidad = cantidad;
            this.repeticion = repeticion;
            this.operaciones = operaciones;
            this.tiempoTotal = tiempoTotal;
        }
    }

    private final ArrayList<Registro> registros;

    // Evita que el resultado de una prueba quede sin utilizar.
    private static volatile Object ultimoResultado;

    public Resultados() {
        registros = new ArrayList<>();
    }

    public void agregar(String estructura, String operacion,
            int cantidad, int repeticion,
            int operaciones, long tiempoTotal) {

        registros.add(new Registro(
                estructura,
                operacion,
                cantidad,
                repeticion,
                operaciones,
                tiempoTotal
        ));
    }

    public static void conservar(Object resultado) {
        ultimoResultado = resultado;
    }

    public void guardarCSV(String nombreArchivo)
            throws IOException {

        try (BufferedWriter escritor = Files.newBufferedWriter(
                Paths.get(nombreArchivo),
                StandardCharsets.UTF_8)) {

            escritor.write(
                    "estructura,operacion,cantidad,repeticion,"
                    + "operaciones,tiempo_total_ns,"
                    + "tiempo_promedio_ns"
            );

            escritor.newLine();

            for (Registro r : registros) {

                double promedio = (double) r.tiempoTotal
                        / r.operaciones;

                escritor.write(String.format(
                        Locale.US,
                        "%s,%s,%d,%d,%d,%d,%.2f",
                        r.estructura,
                        r.operacion,
                        r.cantidad,
                        r.repeticion,
                        r.operaciones,
                        r.tiempoTotal,
                        promedio
                ));

                escritor.newLine();
            }
        }

        System.out.println(
                "\nResultados guardados en: " + nombreArchivo
        );
    }
}