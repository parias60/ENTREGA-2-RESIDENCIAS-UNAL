package experimentos;

import modelos.Estudiante;
import java.util.Random;
import java.util.Locale;

public class GeneradorDatos {

    public static Estudiante[] generarEstudiantes(int cantidad, long semilla) {

        Estudiante[] estudiantes = new Estudiante[cantidad];

        Random aleatorio = new Random(semilla);

        for (int i = 0; i < cantidad; i++) {

            String id = String.format(
                    Locale.ROOT, "%08d", i + 1
            );

            String nombre = "Estudiante " + (i + 1);

            int puntaje = aleatorio.nextInt(1000) + 1;

            estudiantes[i] = new Estudiante(
                    id, nombre, puntaje
            );
        }

        return estudiantes;
    }
}