/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package estructuras;
import modelos.Estudiante;

/**
 *
 * @author danie
 */
public class AVLestudiantes {
    
    
    //NODO DEFINICION
   
      private class Nodo {
        Estudiante estudiante;
        Nodo izquierdo;
        Nodo derecho;
        int altura;

      public Nodo(Estudiante estudiante) {
            this.estudiante = estudiante;
            this.izquierdo = null;
            this.derecho = null;
            this.altura = 1;
        }
      }

      //AVL DEFINICION
      
    private Nodo raiz;
    private int tamanio;

    public AVLestudiantes() {
        raiz = null;
        tamanio = 0;
    }
    
     private int altura(Nodo n) {
         if(n == null){
             return 0;
         }else{
             return n.altura;
         }
    }
    
    public int obtenerAltura(){  //LA ALTURA DE LA RAIZ
        return altura(raiz);
    }
    
    public boolean estaVacio(){
        return raiz == null;
    }
    
    public int factorBalance(Nodo n){
        if(n == null){
            return 0;
        }else{
            return altura(n.izquierdo) - altura(n.derecho);
        }
    }
    
    public void actualizarAltura(Nodo n){
        if(n != null){
            n.altura = Math.max(altura(n.izquierdo), altura(n.derecho))+1; //la altura maxima de sus hijos +1
        }
    }
    public boolean insertar(Estudiante estudiante) {
        // Implementacion pendiente
        return false;
    }

    public Estudiante buscar(String id) {
        // Implementacion pendiente
        return null;
    }

    public boolean eliminar(String id) {
        // Implementacion pendiente
        return false;
    }

    public boolean contiene(String id) {
        // Implementacion pendiente
        return false;
    }

    public Estudiante[] obtenerEnOrden() {
        // Implementacion pendiente
        return new Estudiante[0];
    }

    public int obtenerTamanio() {
        return tamanio;
    }

    // Metodos auxiliares privados:
    // altura()
    // actualizarAltura()
    // factorEquilibrio()
    // rotarIzquierda()
    // rotarDerecha()
    // insertarRecursivo()
    // buscarRecursivo()
    // eliminarRecursivo()
    // recorrerEnOrden()

}
