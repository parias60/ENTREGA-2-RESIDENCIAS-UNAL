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
    
    
    //METODOS PARA LA ALTURA
    
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
    
    
    
    
    
    
    
    
    
    
    
    
    //METODOS ROTACIONES CUANDO DESBALANCEADO
    
    private Nodo rotarDerecha(Nodo y) {
        
        /*
                y                       x
               x            ->        l   y
              l   subtree              subtree
        */
        
        Nodo x = y.izquierdo;
        Nodo subtree = x.derecho;

        x.derecho = y;
        y.izquierdo = subtree;

        actualizarAltura(y);
        actualizarAltura(x);

        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecho;
        Nodo subtree = y.izquierdo;

        y.izquierdo = x;
        x.derecho = subtree;

        actualizarAltura(x);
        actualizarAltura(y);

        return y;
    }
    
    
    
    
    
    
    
    
    
    //MODIFICACION/BUSQUEDA EN EL ARBOL
    
    public boolean insert(Estudiante estudiante) {
        if (estudiante == null || estudiante.getId() == null){ return false;}
        if (contiene(estudiante.getId())) {return false;}
        
        raiz = insertRec(raiz, estudiante);
        tamanio++;
        return true;
    }
    
    private Nodo insertRec(Nodo n, Estudiante estudiante){
        if(n == null){return new Nodo(estudiante);}
        
        
        int comparacion = estudiante.getId().compareTo(n.estudiante.getId()); //compara el estudiante que ya está en n, y el que se va a ingresar
        if(comparacion < 0){
            n.izquierdo = insertRec(n.izquierdo, estudiante);
        }else if(comparacion > 0){
            n.derecho = insertRec(n.derecho, estudiante);
        }else{
            return n; // si hay repetido
        }
        
        actualizarAltura(n);
        int balance = factorBalance(n);

        // Rotaciones
        
        //LL
        if (balance > 1 && estudiante.getId().compareTo(n.izquierdo.estudiante.getId()) < 0)
            return rotarDerecha(n);
        //RR
        if (balance < -1 && estudiante.getId().compareTo(n.derecho.estudiante.getId()) > 0)
            return rotarIzquierda(n);
        
        
        //LR
        if (balance > 1 && estudiante.getId().compareTo(n.izquierdo.estudiante.getId()) > 0) {
            n.izquierdo = rotarIzquierda(n.izquierdo);
            return rotarDerecha(n);
        }
        
        //RL
        if (balance < -1 && estudiante.getId().compareTo(n.derecho.estudiante.getId()) < 0) {
            n.derecho = rotarDerecha(n.derecho);
            return rotarIzquierda(n);
        }

        return n;
    }

    public Estudiante buscar(String id) {
        Nodo resultado = buscarRec(raiz, id);
        
        
        if(resultado == null){return null;}else{return resultado.estudiante;}
    }
    
    private Nodo buscarRec(Nodo n, String id) {
        if (n == null || n.estudiante.getId().equals(id)) {
            return n;
        }
        if (id.compareTo(n.estudiante.getId()) < 0) {
            return buscarRec(n.izquierdo, id);
        }
        
        
        return buscarRec(n.derecho, id);
        
    }

    public boolean eliminar(String id) {
        if(!contiene(id)){return false;}
        raiz = eliminarRec(raiz, id);
        tamanio--;
        return true;
    }
    
    private Nodo eliminarRec(Nodo n, String id) {
        if(n == null){return null;}
        
        int comparacion = id.compareTo(raiz.estudiante.getId());
        if (comparacion < 0) {
            n.izquierdo = eliminarRec(n.izquierdo, id);
        } else if (comparacion > 0) {
            n.derecho = eliminarRec(n.derecho, id);
        } else {
            if ((n.izquierdo == null) || (n.derecho == null)) {
                Nodo temp = (n.izquierdo != null) ? n.izquierdo : n.derecho;
                if (temp == null) {
                    n = null;
                } else {
                    n = temp;
                }
            } else {
                Nodo temp = obtenerMinimoNodo(n.derecho);
                n.estudiante = temp.estudiante;
                n.derecho = eliminarRec(n.derecho, temp.estudiante.getId());
            }
        }

        if (n == null) return null;

        actualizarAltura(n);
        int balance = factorBalance(n);
        
        //LL

        if (balance > 1 && factorBalance(n.izquierdo) >= 0){
            return rotarDerecha(n);
        }
        
        //RR
         if (balance < -1 && factorBalance(n.derecho) <= 0){
            return rotarIzquierda(n);
        }

        //LR
        if (balance > 1 && factorBalance(n.izquierdo) < 0) {
            n.izquierdo = rotarIzquierda(n.izquierdo);
            return rotarDerecha(n);
        }

       
        //RL
        if (balance < -1 && factorBalance(n.derecho) > 0) {
            n.derecho = rotarDerecha(n.derecho);
            return rotarIzquierda(n);
        }

        return n;
    }
    
    
    
    private Nodo obtenerMinimoNodo(Nodo n) {
        Nodo aux = n;
        
        while(aux.izquierdo != null){
            aux = aux.izquierdo;
            
        }
        return aux;
    }
    

    public boolean contiene(String id) {
        return buscar(id) != null;
       
    }

    public Estudiante[] obtenerEnOrden() {
        // Implementacion pendiente
        return new Estudiante[0];
    }

    public int obtenerTamanio() {
        return tamanio;
    }


}
