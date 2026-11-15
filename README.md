# Sistema de Gestión de Residencias Universitarias - RESIDENCIAS UNAL

## Integrantes

* Pablo Daniel Felipe Arias Yucuma
* Simón Peñaranda Fajardo
* Jose Santiago Dominguez Ortiz
* Sergio Enrique Herrera Galvis
* Samuel Alejandro Jaime Rodriguez

---

## Descripción del Problema

La Universidad Nacional de Colombia cuenta con residencias universitarias para apoyar a estudiantes que requieren alojamiento durante sus estudios. Debido a la alta demanda, el proceso de asignación debe realizarse de forma eficiente, justa y priorizada. Cada estudiante que solicita residencia tiene asociado un puntaje socioeconómico que representa su prioridad: **a menor puntaje, mayor necesidad de alojamiento**.

Este sistema permite registrar estudiantes, administrar cupos de residencia, gestionar dinámicamente la lista de espera y asignar o reasignar cupos de manera prioritaria, manteniendo la consistencia entre todas las estructuras de datos implementadas desde cero.

---

## Funcionalidades del Sistema

El sistema permite realizar las siguientes operaciones desde un menú interactivo por consola:

1. **Registrar estudiante:** Registra un estudiante con su `ID`, `Nombre completo` y `Puntaje socioeconómico`. Si existen cupos disponibles y la lista de espera está vacía, se le asigna residencia inmediatamente; de lo contrario, ingresa a la lista de espera priorizada.
2. **Consultar estudiante por ID:** Acceso eficiente a los datos y estado de residencia mediante su identificador único.
3. **Modificar nombre de estudiante:** Actualiza el nombre de un estudiante registrado.
4. **Modificar puntaje socioeconómico:** Actualiza el puntaje de un estudiante y reajusta automáticamente su posición en el árbol de prioridad si se encuentra en lista de espera.
5. **Eliminar estudiante:** Retira al estudiante del sistema. Si ocupaba una residencia, libera el cupo y lo reasigna automáticamente al primero en la lista de espera.
6. **Mostrar todos los estudiantes:** Lista todos los estudiantes registrados en orden creciente por su `ID`.
7. **Mostrar lista de espera:** Lista los aspirantes sin cupo en orden creciente de puntaje socioeconómico (y desempate alfabético por `ID`).
8. **Mostrar estudiantes residentes:** Lista los estudiantes que cuentan actualmente con un cupo asignado.
9. **Mostrar estado de cupos:** Consulta la cantidad de cupos disponibles y ocupados.
10. **Liberar residencia:** Desocupa una residencia específica por su identificador numérico y asigna automáticamente el cupo al estudiante con mayor prioridad en la lista de espera.
11. **Mostrar estadísticas del sistema:** Resumen general de estudiantes registrados, residentes, en espera y disponibilidad de cupos.
12. **Salir:** Finaliza la ejecución del programa.

---

## Diseño y Estructura de Paquetes

El proyecto se divide en los siguientes paquetes:

* **`modelos/`**
  * `Estudiante.java`: Representa a un estudiante en el sistema con los atributos `ID`, `Nombre`, `Puntaje Socioeconomico` e `idResidencia` (`null` si no tiene residencia asignada).
  * `Residencia.java`: Representa el cupo físico de una residencia numerado secuencialmente a partir de `1`, con los atributos `id` e `idEstudiante` (`null` si se encuentra desocupado).
* **`estructuras/`**
  * `AVLEstudiantes.java`: Árbol AVL que almacena al conjunto total de estudiantes registrados, ordenándolos según su `id`.
  * `AVLPrioridad.java`: Árbol AVL que almacena a los estudiantes en lista de espera, ordenándolos por puntaje socioeconómico y, en caso de empate, por `id`.
* **`gestion/`**
  * `GestorEstudiantes.java`: Maneja y mantiene la sincronización entre `AVLEstudiantes` y `AVLPrioridad`.
  * `GestorResidencias.java`: Administra el arreglo estático de residencias (`Residencia[]`), busca cupos disponibles, asigna y libera cupos.
  * `GestorSistema.java`: Centraliza las reglas de negocio y coordina las operaciones entre estudiantes y residencias.
* **`interfaz/`**
  * `MenuPrincipal.java`: Construcción de la interfaz interactiva por consola para el usuario.
* **`residenciasunal/`**
  * `Main.java`: Punto de entrada principal del sistema.
* **Pruebas de Rendimiento y Experimentos:**
  * `MainExperimentos.java`, `GeneradorDatos.java`, `PruebasAVL.java`, `PruebasPrioridad.java`, `PruebasResidencias.java` y `Resultados.java`: Módulos encargados de medir tiempos de ejecución con `System.nanoTime()` para entradas de `1.000`, `5.000`, `10.000`, `50.000` y `100.000` elementos, generando el archivo `resultados.csv`.

---

## Complejidades Teóricas de las Estructuras

### 1. Árbol AVL de Estudiantes (`AVLEstudiantes`)
| Operación | Complejidad Temporal | Justificación |
| :--- | :---: | :--- |
| **Inserción** | `O(log n)` | Recorre la altura del árbol para encontrar la posición de inserción y puede realizar rebalanceos. |
| **Búsqueda** | `O(log n)` | Compara el identificador y avanza por una de las ramas del árbol. |
| **Eliminación** | `O(log n)` | Localiza el nodo que debe eliminarse y actualiza el balance del árbol. |
| **Recorrido** | `O(n)` | Visita todos los nodos una vez. |

### 2. Árbol AVL de Prioridad (`AVLPrioridad`)
| Operación | Complejidad Temporal | Justificación |
| :--- | :---: | :--- |
| **Inserción** | `O(log n)` | Busca la posición correspondiente y mantiene el balance del árbol. |
| **Consultar prioridad** | `O(log n)` | Recorre la rama izquierda hasta encontrar el nodo mínimo. |
| **Extraer prioridad** | `O(log n)` | Localiza el nodo mínimo y posteriormente lo elimina, manteniendo el balance. |
| **Eliminación** | `O(log n)` | Localiza el nodo mediante su clave y realiza los ajustes necesarios. |
| **Recorrido** | `O(n)` | Visita todos los nodos del árbol. |

### 3. Gestión de Residencias (`Residencia[]`)
| Operación | Complejidad Temporal | Justificación |
| :--- | :---: | :--- |
| **Inicialización** | `O(n)` | Crea e inicializa las posiciones del arreglo. |
| **Búsqueda por ID** | `O(n)` | Puede recorrer el arreglo hasta localizar la residencia. |
| **Buscar cupo disponible** | `O(n)` | Puede revisar todas las posiciones hasta encontrar un cupo libre. |
| **Asignación** | `O(n)` | Busca la residencia correspondiente y realiza la asignación. |
| **Liberación** | `O(n)` | Busca la residencia ocupada y libera el cupo. |

---

## Entorno de Desarrollo

| Herramienta o tecnología | Descripción | Uso en el proyecto |
| :--- | :--- | :--- |
| **Java** | Lenguaje de programación orientado a objetos. | Desarrollo de la lógica y funcionalidades del sistema. |
| **JDK (Java Development Kit)** | Kit de desarrollo de Java. | Compilación y ejecución del código fuente. |
| **NetBeans IDE** | Entorno de desarrollo integrado. | Escritura, organización, compilación y depuración del código. |
| **Git** | Sistema de control de versiones. | Seguimiento de cambios y administración del código durante el desarrollo. |
| **GitHub** | Plataforma de alojamiento de repositorios. | Almacenamiento del proyecto y colaboración entre los integrantes del equipo. |

---

## Instrucciones de Ejecución

1. **Clonar el repositorio:**
   * **Desde el IDE:** Hacer clic en el botón verde **`<> Code`** en la parte superior del repositorio en GitHub, copiar la URL HTTPS y pegarla en la opción de clonación por Git/VCS del entorno de desarrollo.
   * **Desde la terminal:**
     ```bash
     git clone https://github.com/parias60/ENTREGA-2-RESIDENCIAS-UNAL.git
     ```
2. **Abrir el proyecto** en el IDE de Java.
3. **Ejecutar la clase principal** `residenciasunal.Main` para iniciar el menú interactivo en consola (o `MainExperimentos` para ejecutar las pruebas de rendimiento).

---

## Video Demostrativo

* **Enlace a YouTube:** [https://youtu.be/wtGWFI90klk?si=86baiMBlXNmU-DPR](https://youtu.be/wtGWFI90klk?si=86baiMBlXNmU-DPR)
