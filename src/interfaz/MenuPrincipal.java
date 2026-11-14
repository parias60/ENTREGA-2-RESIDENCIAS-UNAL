/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
            Integer opcion = leerEntero("Opcion: ");
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
                    salida.println("Opcion invalida, debe elegir una opcion valida");
            }
            salida.println("\n--------------------------------------------------\n");
        }
    }

    private void mostrarMenu() {
        salida.println("=== RESIDENCIAS UNAL ===");
        salida.println(" 1. Registrar estudiante");
        salida.println(" 2. Consultar estudiante por ID");
        salida.println(" 3. Modificar nombre de estudiante");
        salida.println(" 4. Modificar puntaje socioeconómico");
        salida.println(" 5. Eliminar estudiante");
        salida.println(" 6. Mostrar todos los estudiantes");
        salida.println(" 7. Mostrar lista de espera");
        salida.println(" 8. Mostrar estudiantes residentes");
        salida.println(" 9. Mostrar estado de cupos");
        salida.println("10. Liberar residencia");
        salida.println("11. Mostrar estadisticas del sistema");
        salida.println("12. Salir");
    }

    private void registrarEstudiante() {
        salida.println("--- REGISTRO DE NUEVO ESTUDIANTE ---");
        String id = leerTexto("ID del estudiante: ");
        if (id == null) {
            return;
        }
        String nombre = leerTexto("Nombre: ");
        if (nombre == null) {
            return;
        }
        Integer puntaje = leerEntero("Puntaje socioeconómico (mayor o igual a 0): ");
        if (puntaje == null || puntaje < 0) {
            salida.println("ERROR PUNTAJE NO VALIDO.");
            return;
        }

        Estudiante estudiante = new Estudiante(id, nombre, puntaje);
        boolean exito = gestor.registrarEstudiante(estudiante);

        if (exito) {
            salida.println("\n[Éxito] Estudiante registrado correctamente.");
            if (estudiante.tieneResidencia()) {
                salida.println("-> Se le ha asignado el cupo de residencia #" + estudiante.getIdResidencia());
            } else {
                salida.println("-> No hay cupos disponibles se agregara a la lista de espera.");
            }
        } else {
            salida.println("\n No se pudo registrar. Ya existe un estudiante con el ID '" + id + "'.");
        }
    }

    private void consultarEstudiante() {
        salida.println("--- CONSULTAR ESTUDIANTE ---");
        String id = leerTexto("ID del estudiante a buscar: ");
        if (id == null) {
            return;
        }
        Estudiante estudiante = gestor.buscarEstudiante(id);
        if (estudiante == null) {
            salida.println("\n[Aviso] No se encontró ningún estudiante con el ID '" + id + "'.");
        } else {
            salida.println("\n[Estudiante Encontrado]");
            salida.println("ID: " + estudiante.getId());
            salida.println("Nombre: " + estudiante.getNombre());
            salida.println("Puntaje Socioeconómico: " + estudiante.getPuntajeSocioeconomico());
            if (estudiante.tieneResidencia()) {
                salida.println("Estado: Residente (Cupo #" + estudiante.getIdResidencia() + ")");
            } else {
                salida.println("Estado: En lista de espera");
            }
        }
    }

    private void modificarEstudiante() {
        salida.println("--- MODIFICAR NOMBRE DE ESTUDIANTE ---");
        String id = leerTexto("ID del estudiante: ");
        if (id == null) {
            return;
        }
        Estudiante e = gestor.buscarEstudiante(id);
        if (e == null) {
            salida.println("\nEl estudiante con ID '" + id + "' no existe.");
            return;
        }
        String nombre = leerTexto("Nuevo nombre: ");
        if (nombre != null) {
            salida.println(gestor.modificarNombre(id, nombre)
                    ? "\nNombre actualizado." : "\nNo se pudo actualizar el nombre.");
        }
    }

    private void modificarPuntaje() {
        salida.println("--- MODIFICAR PUNTAJE SOCIOECONÓMICO ---");
        String id = leerTexto("ID del estudiante: ");
        if (id == null) {
            return;
        }
        Estudiante e = gestor.buscarEstudiante(id);
        if (e == null) {
            salida.println("\nEl estudiante con ID '" + id + "' no existe.");
            return;
        }
        Integer puntaje = leerEntero("Nuevo puntaje socioeconómico: ");
        if (puntaje != null && puntaje >= 0) {
            salida.println(gestor.modificarPuntaje(id, puntaje)
                    ? "\nPuntaje actualizado correctamente y reordenado en el sistema." : "\nNo se pudo actualizar el puntaje.");
        } else {
            salida.println("El puntaje ingresado no es válido.");
        }
    }

    private void eliminarEstudiante() {
        salida.println("--- ELIMINAR ESTUDIANTE ---");
        String id = leerTexto("ID del estudiante a eliminar: ");
        if (id != null) {
            boolean eliminado = gestor.eliminarEstudiante(id);
            if (eliminado) {
                salida.println("\nEstudiante eliminado del sistema. Si ocupaba residencia, esta ha sido liberada y reasignada.");
            } else {
                salida.println("\n No se pudo eliminar. el ID '" + id + "' no existe.");
            }
        }
    }

    private void mostrarTodos() {
        salida.println("--- LISTA GENERAL DE ESTUDIANTES (Ordenados por ID) ---");
        Estudiante[] estudiantes = gestor.obtenerTodos();
        if (estudiantes.length == 0) {
            salida.println("No hay estudiantes registrados en el sistema.");
            return;
        }
        for (Estudiante estudiante : estudiantes) {
            String estado = estudiante.tieneResidencia() 
                    ? "Residente (Cupo #" + estudiante.getIdResidencia() + ")" : "En espera";
            salida.println("- ID: " + estudiante.getId() + " | Nombre: " + estudiante.getNombre() 
                    + " | Puntaje: " + estudiante.getPuntajeSocioeconomico() + " | Estado: " + estado);
        }
    }

    private void mostrarListaEspera() {
        salida.println("--- LISTA DE ESPERA (Ordenada por Prioridad) ---");
        Estudiante[] espera = gestor.obtenerListaEspera();
        if (espera.length == 0) {
            salida.println("La lista de espera esta vacia.");
            return;
        }
        int posicion = 1;
        for (Estudiante e : espera) {
            salida.println(posicion + ". ID: " + e.getId() + " | Nombre: " + e.getNombre() 
                    + " | Puntaje: " + e.getPuntajeSocioeconomico());
            posicion++;
        }
    }

    private void mostrarResidentes() {
        salida.println("--- ESTUDIANTES RESIDENTES ---");
        Estudiante[] residentes = gestor.obtenerResidentes();
        if (residentes.length == 0) {
            salida.println("Actualmente no hay estudiantes con residencia asignada.");
            return;
        }
        for (Estudiante e : residentes) {
            salida.println("- Cupo #" + e.getIdResidencia() + " -> Estudiante: " + e.getNombre() 
                    + " (ID: " + e.getId() + ", Puntaje: " + e.getPuntajeSocioeconomico() + ")");
        }
    }

    private void mostrarCuposDisponibles() {
        salida.println("--- ESTADO DE CUPOS DE RESIDENCIA ---");
        int disponibles = gestor.obtenerCuposDisponibles();
        int ocupados = gestor.obtenerCuposOcupados();
        salida.println("Cupos Ocupados   : " + ocupados);
        salida.println("Cupos Disponibles: " + disponibles);
        salida.println("Total de Cupos   : " + (disponibles + ocupados));
    }

    private void liberarResidencia() {
        salida.println("--- LIBERAR RESIDENCIA ---");
        Integer idResidencia = leerEntero("Ingrese el número (ID) del cupo de residencia a liberar: ");
        if (idResidencia != null) {
            boolean liberada = gestor.liberarResidencia(idResidencia);
            if (liberada) {
                salida.println("\nResidencia #" + idResidencia + " liberada correctamente.");
                salida.println("-> El sistema ha asignado automáticamente el cupo al siguiente estudiante con mayor prioridad en espera (si hay uno).");
            } else {
                salida.println("\n No se pudo liberar. Verifique que el número de cupo sea válido y que se encuentre ocupado.");
            }
        }
    }

    private void mostrarEstadisticas() {
        salida.println("--- ESTADÍSTICAS ACTUALES DEL SISTEMA ---");
        int disponibles = gestor.obtenerCuposDisponibles();
        int ocupados = gestor.obtenerCuposOcupados();
        salida.println("Estudiantes registrados: " + gestor.obtenerTodos().length);
        salida.println("Estudiantes en espera  : " + gestor.obtenerListaEspera().length);
        salida.println("Total de Cupos         : " + (disponibles + ocupados));
        salida.println("Cupos ocupados         : " + ocupados);
        salida.println("Cupos disponibles      : " + disponibles);
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
            salida.println("[Error] Este campo no puede estar vacío.");
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
                salida.println("[Error] Escribe un número entero válido.");
            }
        }
    }
}