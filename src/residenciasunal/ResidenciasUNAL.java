/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package residenciasunal;
import gestion.GestorSistema;
import interfaz.MenuPrincipal;
/**
 *
 * @author danie
 */
public class ResidenciasUNAL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GestorSistema gestor = new GestorSistema();

        MenuPrincipal menu = new MenuPrincipal(gestor);

        menu.iniciar();
    }
    
}
