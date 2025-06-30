package Modelo;
import java.util.ArrayList;
import java.util.List; // usamos ArrayList, a menudo se importa Lisst para la interfaz

public class Empresa {
    private ArrayList<Trabajador> trabajadores;

    public Empresa() {
        this.trabajadores = new ArrayList<>();
    }

    public void agregarTrabajador(Trabajador trabajador) {
        this.trabajadores.add(trabajador);
    }

    public Trabajador buscarTrabajadorPorRut(String rut) {
        for (Trabajador t : trabajadores) {
            if (t.getRut().equals(rut)) {
                return t;
            }
        }
        return null; // No encontrado
    }

    public ArrayList<Trabajador> listarTrabajadores() {
        return new ArrayList<>(this.trabajadores);
    }

    public boolean actualizarTrabajador(Trabajador trabajadorActualizado) {
        for (int i = 0; i < trabajadores.size(); i++) {
            if (trabajadores.get(i).getRut().equals(trabajadorActualizado.getRut())) {
                trabajadores.set(i, trabajadorActualizado);
                return true;
            }
        }
        return false; // no se encuentra
    }

    public boolean eliminarTrabajador(String rut) {
        return trabajadores.removeIf(t -> t.getRut().equals(rut));
    }
}