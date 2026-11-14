
package experimentos;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

public class Resultados implements AutoCloseable {

    private BufferedWriter escritor;

    private static volatile Object ultimoResultado;

    // Constructor para las pruebas de calentamiento.
    // No genera ningún archivo CSV.
    public Resultados() {
        escritor = null;
    }

    // Constructor para guardar los resultados en CSV.
    public Resultados(String nombreArchivo) throws IOException {

        escritor = Files.newBufferedWriter(
                Paths.get(nombreArchivo),
                StandardCharsets.UTF_8
        );

        escritor.write(
                "estructura,operacion,cantidad,repeticion,"
                + "operaciones,tiempo_total_ns,tiempo_promedio_ns"
        );

        escritor.newLine();
    }

    public void agregar(
            String estructura,
            String operacion,
            int cantidad,
            int repeticion,
            int operaciones,
            long tiempoTotal) {

        // Durante el calentamiento no se guardan resultados.
        if (escritor == null) {
            return;
        }

        if (operaciones <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de operaciones debe ser mayor que cero."
            );
        }

        if (tiempoTotal < 0) {
            throw new IllegalArgumentException(
                    "El tiempo total no puede ser negativo."
            );
        }

        double promedio = (double) tiempoTotal / operaciones;

        try {

            escritor.write(String.format(
                    Locale.US,
                    "%s,%s,%d,%d,%d,%d,%.2f",
                    estructura,
                    operacion,
                    cantidad,
                    repeticion,
                    operaciones,
                    tiempoTotal,
                    promedio
            ));

            escritor.newLine();

            // No hacemos flush después de cada registro.
            // BufferedWriter acumula los datos en memoria.

        } catch (IOException e) {

            throw new UncheckedIOException(
                    "No se pudo guardar el resultado.",
                    e
            );
        }
    }

    public static void conservar(Object resultado) {

        ultimoResultado = resultado;
    }

    @Override
    public void close() throws IOException {

        if (escritor != null) {

            escritor.close();

            escritor = null;
        }
    }
}
