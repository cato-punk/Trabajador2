package Launcher;
import Datos.DatosTrabajador;
import Datos.InterfaceDatos;
import Controlador.TrabajadorControlador; // Importamos el nuevo controlador
import Ventanas.GUITrabajador;

import javax.swing.SwingUtilities;

public class Inicio {
    public static void main(String[] args) {
        InterfaceDatos datosTrabajador = new DatosTrabajador();
        TrabajadorControlador controlador = new TrabajadorControlador(datosTrabajador);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUITrabajador gui = new GUITrabajador(controlador); // Pasamos el controlador
                gui.setVisible(true); // visible la ventana
            }
        });

    }
}