package interfaz;

import gestion.GestorSistema;
import java.io.PrintStream;
import java.util.Objects;
import java.util.Scanner;
import modelos.Estudiante;

/** Lee y presenta datos. Las reglas del sistema pertenecen a GestorSistema. */
public class MenuPrincipal {

    private final GestorSistema gestor;
    private final Scanner entrada;
    private final PrintStream salida;

    public MenuPrincipal(GestorSistema gestor) {
        this(gestor, new Scanner(System.in), System.out);
    }

    // Permite probar la consola sin reemplazar System.in ni System.out.
    MenuPrincipal(GestorSistema gestor, Scanner entrada, PrintStream salida) {
        this.gestor = Objects.requireNonNull(gestor);
        this.entrada = Objects.requireNonNull(entrada);
        this.salida = Objects.requireNonNull(salida);
    }

    public void iniciar() {
        while (true) {
            mostrarMenu();
            Integer opcion = leerEntero("Opción: ");
            if (opcion == null) {
                salida.println("\nFin de la entrada. Hasta luego.");
                return;
            }
            switch (opcion) {
                case 1: registrarEstudiante(); break;
                case 2: consultarEstudiante(); break;
                case 3: modificarEstudiante(); break;
                case 4: modificarPuntaje(); break;
                case 5: eliminarEstudiante(); break;
                case 6: mostrarTodos(); break;
                case 7: mostrarListaEspera(); break;
                case 8: mostrarResidentes(); break;
                case 9: mostrarCuposDisponibles(); break;
                case 10: liberarResidencia(); break;
                case 11: mostrarEstadisticas(); break;
                case 12:
                    salida.println("Hasta luego.");
                    return;
                default:
                    salida.println("Opción inválida. Elige un número del 1 al 12.");
            }
        }
    }

    private void mostrarMenu() {
        salida.println("\n=== RESIDENCIAS UNAL ===");
        salida.println(" 1. Registrar estudiante");
        salida.println(" 2. Buscar estudiante");
        salida.println(" 3. Modificar información del estudiante (nombre)");
        salida.println(" 4. Modificar puntaje socioeconómico");
        salida.println(" 5. Eliminar estudiante");
        salida.println(" 6. Mostrar todos los estudiantes");
        salida.println(" 7. Mostrar lista de espera");
        salida.println(" 8. Mostrar estudiantes residentes");
        salida.println(" 9. Mostrar cupos disponibles");
        salida.println("10. Liberar residencia");
        salida.println("11. Mostrar estadísticas del sistema");
        salida.println("12. Salir");
    }

    private void registrarEstudiante() {
        String id = leerTexto("ID del estudiante: ");
        if (id == null) {
            return;
        }
        String nombre = leerTexto("Nombre: ");
        if (nombre == null) {
            return;
        }
        Integer puntaje = leerEntero("Puntaje socioeconómico: ");
        if (puntaje == null) {
            return;
        }
        Estudiante estudiante = new Estudiante(id, nombre, puntaje);
        if (!gestor.registrarEstudiante(estudiante)) {
            salida.println("No se registró el estudiante. Verifica si el ID ya existe.");
            return;
        }
        if (estudiante.tieneResidencia()) {
            salida.println("Estudiante registrado. Cupo asignado: " + estudiante.getIdResidencia());
        } else {
            salida.println("Estudiante registrado en la lista de espera.");
        }
    }

    private void consultarEstudiante() {
        String id = leerTexto("ID del estudiante: ");
        if (id == null) {
            return;
        }
        Estudiante estudiante = gestor.buscarEstudiante(id);
        if (estudiante == null) {
            salida.println("No existe un estudiante con ese ID.");
        } else {
            imprimirEstudiante(estudiante);
        }
    }

    private void modificarEstudiante() {
        String id = leerTexto("ID del estudiante: ");
        if (id == null) {
            return;
        }
        String nombre = leerTexto("Nuevo nombre: ");
        if (nombre != null) {
            salida.println(gestor.modificarNombre(id, nombre)
                    ? "Nombre actualizado." : "No existe un estudiante con ese ID.");
        }
    }

    private void modificarPuntaje() {
        String id = leerTexto("ID del estudiante: ");
        if (id == null) {
            return;
        }
        Integer puntaje = leerEntero("Nuevo puntaje: ");
        if (puntaje != null) {
            salida.println(gestor.modificarPuntaje(id, puntaje)
                    ? "Puntaje actualizado." : "No existe un estudiante con ese ID.");
        }
    }

    private void eliminarEstudiante() {
        String id = leerTexto("ID del estudiante: ");
        if (id != null) {
            salida.println(gestor.eliminarEstudiante(id)
                    ? "Estudiante eliminado."
                    : "No se pudo eliminar el estudiante. Verifica el ID.");
        }
    }

    private void mostrarTodos() {
        mostrarEstudiantes("Estudiantes registrados", gestor.obtenerTodos());
    }

    private void mostrarListaEspera() {
        mostrarEstudiantes("Lista de espera por prioridad", gestor.obtenerListaEspera());
    }

    private void mostrarResidentes() {
        mostrarEstudiantes("Estudiantes residentes", gestor.obtenerResidentes());
    }

    private void mostrarCuposDisponibles() {
        salida.println("Cupos disponibles: " + gestor.obtenerCuposDisponibles());
    }

    private void liberarResidencia() {
        Integer id = leerEntero("ID de la residencia: ");
        if (id != null) {
            salida.println(gestor.liberarResidencia(id)
                    ? "Cupo liberado."
                    : "No se pudo liberar el cupo. Verifica que exista y esté ocupado.");
        }
    }

    private void mostrarEstadisticas() {
        int disponibles = gestor.obtenerCuposDisponibles();
        int ocupados = gestor.obtenerCuposOcupados();
        salida.println("Estudiantes registrados: " + gestor.obtenerTodos().length);
        salida.println("Estudiantes en espera: " + gestor.obtenerListaEspera().length);
        salida.println("Cupos totales: " + (disponibles + ocupados));
        salida.println("Cupos ocupados: " + ocupados);
        salida.println("Cupos disponibles: " + disponibles);
    }

    private void mostrarEstudiantes(String titulo, Estudiante[] estudiantes) {
        salida.println(titulo + " (" + estudiantes.length + ")");
        if (estudiantes.length == 0) {
            salida.println("No hay estudiantes para mostrar.");
            return;
        }
        for (Estudiante estudiante : estudiantes) {
            imprimirEstudiante(estudiante);
        }
    }

    private void imprimirEstudiante(Estudiante estudiante) {
        String cupo = estudiante.tieneResidencia()
                ? "Cupo " + estudiante.getIdResidencia() : "Sin cupo";
        salida.printf("%s | %s | Puntaje %d | %s%n", estudiante.getId(),
                estudiante.getNombre(), estudiante.getPuntajeSocioeconomico(), cupo);
    }

    private String leerTexto(String mensaje) {
        while (true) {
            salida.print(mensaje);
            salida.flush();
            if (!entrada.hasNextLine()) {
                return null;
            }
            String texto = entrada.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            salida.println("Este campo no puede estar vacío.");
        }
    }

    private Integer leerEntero(String mensaje) {
        while (true) {
            String texto = leerTexto(mensaje);
            if (texto == null) {
                return null;
            }
            try {
                return Integer.valueOf(texto);
            } catch (NumberFormatException ex) {
                salida.println("Escribe un número entero válido.");
            }
        }
    }
}
