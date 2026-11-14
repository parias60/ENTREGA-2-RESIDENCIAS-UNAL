/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaz;

import gestion.GestorSistema;
import modelos.Estudiante;
import java.util.Scanner;

/**
 *
 * @author danie
 */
public class MenuPrincipal {
    private GestorSistema gestor;
    private Scanner scanner;

    public MenuPrincipal(GestorSistema gestor) {
        this.gestor = gestor;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        do {
            mostrarMenu();
            System.out.print("Seleccione una opcion: ");
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                System.out.println();

                switch (opcion) {
                    case 1:
                        registrarEstudiante();
                        break;
                    case 2:
                        consultarEstudiante();
                        break;
                    case 3:
                        modificarEstudiante();
                        break;
                    case 4:
                        eliminarEstudiante();
                        break;
                    case 5:
                        mostrarTodos();
                        break;
                    case 6:
                        mostrarListaEspera();
                        break;
                    case 7:
                        mostrarResidentes();
                        break;
                    case 8:
                        mostrarCuposDisponibles();
                        break;
                    case 9:
                        liberarResidencia();
                        break;
                    case 10:
                        System.out.println("Saliendo del sistema. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n[Error] Por favor, ingrese un número válido.\n");
                opcion = 0; // Para mantener el ciclo vivo
            }
            System.out.println("\n--------------------------------------------------\n");
        } while (opcion != 10);
    }

    private void mostrarMenu() {
        System.out.println("=== SISTEMA DE GESTIÓN DE RESIDENCIAS UNAL ===");
        System.out.println("1. Registrar estudiante");
        System.out.println("2. Consultar estudiante por ID");
        System.out.println("3. Modificar datos de estudiante");
        System.out.println("4. Eliminar estudiante");
        System.out.println("5. Mostrar todos los estudiantes");
        System.out.println("6. Mostrar lista de espera");
        System.out.println("7. Mostrar estudiantes residentes");
        System.out.println("8. Mostrar estado de cupos");
        System.out.println("9. Liberar residencia (por ID de cupo)");
        System.out.println("10. Salir");
    }

    private void registrarEstudiante() {
        System.out.println("--- REGISTRO DE NUEVO ESTUDIANTE ---");
        System.out.print("Ingrese ID del estudiante: ");
        String id = scanner.nextLine().trim();
        
        if (id.isEmpty()) {
            System.out.println("[Error] El ID no puede estar vacío.");
            return;
        }

        System.out.print("Ingrese Nombre del estudiante: ");
        String nombre = scanner.nextLine().trim();

        int puntaje = -1;
        while (puntaje < 0) {
            System.out.print("Ingrese Puntaje Socioeconómico (entero mayor o igual a 0): ");
            try {
                puntaje = Integer.parseInt(scanner.nextLine());
                if (puntaje < 0) {
                    System.out.println("El puntaje no puede ser negativo.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }

        Estudiante nuevo = new Estudiante(id, nombre, puntaje);
        boolean exito = gestor.registrarEstudiante(nuevo);

        if (exito) {
            System.out.println("\n[Éxito] Estudiante registrado correctamente.");
            if (nuevo.tieneResidencia()) {
                System.out.println("-> Se le ha asignado el cupo de residencia #" + nuevo.getIdResidencia());
            } else {
                System.out.println("-> No hay cupos disponibles. Ha sido incorporado a la lista de espera.");
            }
        } else {
            System.out.println("\n[Error] No se pudo registrar. Ya existe un estudiante con el ID '" + id + "'.");
        }
    }

    private void consultarEstudiante() {
        System.out.println("--- CONSULTAR ESTUDIANTE ---");
        System.out.print("Ingrese el ID del estudiante a buscar: ");
        String id = scanner.nextLine().trim();

        Estudiante e = gestor.buscarEstudiante(id);
        if (e != null) {
            System.out.println("\n[Estudiante Encontrado]");
            System.out.println("ID: " + e.getId());
            System.out.println("Nombre: " + e.getNombre());
            System.out.println("Puntaje Socioeconómico: " + e.getPuntajeSocioeconomico());
            if (e.tieneResidencia()) {
                System.out.println("Estado: Residente (Cupo #" + e.getIdResidencia() + ")");
            } else {
                System.out.println("Estado: En lista de espera");
            }
        } else {
            System.out.println("\n[Aviso] No se encontró ningún estudiante con el ID '" + id + "'.");
        }
    }

    private void modificarEstudiante() {
        System.out.println("--- MODIFICAR ESTUDIANTE ---");
        System.out.print("Ingrese el ID del estudiante a modificar: ");
        String id = scanner.nextLine().trim();

        Estudiante e = gestor.buscarEstudiante(id);
        if (e == null) {
            System.out.println("\n[Error] El estudiante con ID '" + id + "' no existe.");
            return;
        }

        System.out.println("Estudiante seleccionado: " + e.getNombre());
        System.out.println("1. Modificar Nombre");
        System.out.println("2. Modificar Puntaje Socioeconómico");
        System.out.print("Elija qué desea modificar: ");
        
        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion == 1) {
                System.out.print("Ingrese el nuevo nombre: ");
                String nuevoNombre = scanner.nextLine().trim();
                if (gestor.modificarNombre(id, nuevoNombre)) {
                    System.out.println("\n[Éxito] Nombre modificado correctamente.");
                } else {
                    System.out.println("\n[Error] No se pudo actualizar el nombre.");
                }
            } else if (opcion == 2) {
                System.out.print("Ingrese el nuevo puntaje socioeconómico: ");
                int nuevoPuntaje = Integer.parseInt(scanner.nextLine());
                if (gestor.modificarPuntaje(id, nuevoPuntaje)) {
                    System.out.println("\n[Éxito] Puntaje modificado correctamente y reordenado en el sistema.");
                } else {
                    System.out.println("\n[Error] No se pudo actualizar el puntaje.");
                }
            } else {
                System.out.println("Opción no válida.");
            }
        } catch (NumberFormatException ex) {
            System.out.println("\n[Error] Entrada numérica inválida.");
        }
    }

    private void eliminarEstudiante() {
        System.out.println("--- ELIMINAR ESTUDIANTE ---");
        System.out.print("Ingrese el ID del estudiante a eliminar: ");
        String id = scanner.nextLine().trim();

        boolean eliminado = gestor.eliminarEstudiante(id);
        if (eliminado) {
            System.out.println("\n[Éxito] Estudiante eliminado del sistema. Si ocupaba residencia, esta ha sido liberada y reasignada.");
        } else {
            System.out.println("\n[Error] No se pudo eliminar. El estudiante con ID '" + id + "' no existe.");
        }
    }

    private void mostrarTodos() {
        System.out.println("--- LISTA GENERAL DE ESTUDIANTES (Ordenados por ID) ---");
        Estudiante[] todos = gestor.obtenerTodos();
        if (todos.length == 0) {
            System.out.println("No hay estudiantes registrados en el sistema.");
            return;
        }

        for (Estudiante e : todos) {
            String estado = e.tieneResidencia() ? "Residente (Cupo #" + e.getIdResidencia() + ")" : "En espera";
            System.out.println("- ID: " + e.getId() + " | Nombre: " + e.getNombre() + " | Puntaje: " + e.getPuntajeSocioeconomico() + " | Estado: " + estado);
        }
    }

    private void mostrarListaEspera() {
        System.out.println("--- LISTA DE ESPERA (Ordenada por Prioridad) ---");
        Estudiante[] espera = gestor.obtenerListaEspera();
        if (espera.length == 0) {
            System.out.println("La lista de espera está vacía.");
            return;
        }

        int posicion = 1;
        for (Estudiante e : espera) {
            System.out.println(posicion + ". ID: " + e.getId() + " | Nombre: " + e.getNombre() + " | Puntaje: " + e.getPuntajeSocioeconomico());
            posicion++;
        }
    }

    private void mostrarResidentes() {
        System.out.println("--- ESTUDIANTES RESIDENTES ---");
        Estudiante[] residentes = gestor.obtenerResidentes();
        if (residentes.length == 0) {
            System.out.println("Actualmente no hay estudiantes con residencia asignada.");
            return;
        }

        for (Estudiante e : residentes) {
            System.out.println("- Cupo #" + e.getIdResidencia() + " -> Estudiante: " + e.getNombre() + " (ID: " + e.getId() + ", Puntaje: " + e.getPuntajeSocioeconomico() + ")");
        }
    }

    private void mostrarCuposDisponibles() {
        System.out.println("--- ESTADO DE CUPOS DE RESIDENCIA ---");
        int disponibles = gestor.obtenerCuposDisponibles();
        int ocupados = gestor.obtenerCuposOcupados();
        System.out.println("Cupos Ocupados   : " + ocupados);
        System.out.println("Cupos Disponibles: " + disponibles);
        System.out.println("Total de Cupos   : " + (disponibles + ocupados));
    }

    private void liberarResidencia() {
        System.out.println("--- LIBERAR RESIDENCIA ---");
        System.out.print("Ingrese el número (ID) del cupo de residencia a liberar: ");
        try {
            int idResidencia = Integer.parseInt(scanner.nextLine());
            boolean liberada = gestor.liberarResidencia(idResidencia);
            if (liberada) {
                System.out.println("\n[Éxito] Residencia #" + idResidencia + " liberada correctamente.");
                System.out.println("-> El sistema ha asignado automáticamente el cupo al siguiente estudiante con mayor prioridad en espera (si lo había).");
            } else {
                System.out.println("\n[Error] No se pudo liberar. Verifique que el número de cupo sea válido y que se encuentre ocupado.");
            }
        } catch (NumberFormatException e) {
            System.out.println("\n[Error] Debe ingresar un número entero válido.");
        }
    }
}