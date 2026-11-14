package residenciasunal;

import gestion.GestorSistema;
import interfaz.MenuPrincipal;

/** Punto de entrada de Residencias UNAL. */
public class ResidenciasUNAL {

    public static void main(String[] args) {
        GestorSistema gestor = new GestorSistema();
        MenuPrincipal menu = new MenuPrincipal(gestor);
        menu.iniciar();
    }
}
