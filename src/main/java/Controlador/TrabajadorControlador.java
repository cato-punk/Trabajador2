package Controlador;
import Datos.InterfaceDatos;
import Modelo.Trabajador;
import java.util.ArrayList;

public class TrabajadorControlador {
    private InterfaceDatos datosTrabajador; // dependencia de datos

    public TrabajadorControlador(InterfaceDatos datosTrabajador) {
        this.datosTrabajador = datosTrabajador;
        this.datosTrabajador.open();
    }

    //para manejar la busqueda de un trabajador
    public Trabajador buscarTrabajadorPorRut(String rut) {
        if (!validarRut(rut)) {
            System.err.println("Error de validación: RUT inválido.");
            return null;
        }

        ArrayList<Trabajador> trabajadores = datosTrabajador.read();
        for (Trabajador t : trabajadores) {
            if (t.getRut().equalsIgnoreCase(rut)) {
                return t;
            }
        }
        return null;
    }

    //manejar el guardado de un nuevo trabajador
    public String guardarNuevoTrabajador(String nombre, String apellido, String rut, String isapre, String afp) {
        //datos de entrada
        String validacionMensaje = validarDatosTrabajador(nombre, apellido, rut, isapre, afp);
        if (validacionMensaje != null) {
            return validacionMensaje;
        }

        Trabajador nuevoTrabajador = new Trabajador(nombre, apellido, rut, isapre, afp);

        //ya existe un trabajador con el rut
        if (buscarTrabajadorPorRut(rut) != null) {
            return "Ya existe un trabajador con este RUT. Use 'Actualizar' si desea modificarlo.";
        }

        ((Datos.DatosTrabajador) datosTrabajador).agregarTrabajador(nuevoTrabajador);

        datosTrabajador.write(datosTrabajador.read());

        return "Trabajador guardado exitosamente.";
    }

    //manejar la actualizacion de un trabajador
    public String actualizarTrabajador(String nombre, String apellido, String rut, String isapre, String afp) {
        String validacionMensaje = validarDatosTrabajador(nombre, apellido, rut, isapre, afp);
        if (validacionMensaje != null) {
            return validacionMensaje;
        }

        Trabajador trabajadorActualizado = new Trabajador(nombre, apellido, rut, isapre, afp);

        if (datosTrabajador.update(trabajadorActualizado)) {
            datosTrabajador.write(datosTrabajador.read());
            return "Trabajador actualizado exitosamente.";
        } else {
            return "Trabajador con RUT " + rut + " no encontrado para actualizar.";
        }
    }

    //eliminacion de un trabajador
    public String eliminarTrabajador(String rut) {
        if (!validarRut(rut)) {
            return "RUT inválido para eliminación.";
        }

        if (datosTrabajador.delete(rut)) {
            datosTrabajador.write(datosTrabajador.read());
            return "Trabajador con RUT " + rut + " eliminado exitosamente.";
        } else {
            return "Trabajador con RUT " + rut + " no encontrado para eliminar.";
        }
    }

    public void closeDataSource() {
        datosTrabajador.close();
    }

    private String validarDatosTrabajador(String nombre, String apellido, String rut, String isapre, String afp) {
        if (nombre.isEmpty() || apellido.isEmpty() || rut.isEmpty() || isapre.isEmpty() || afp.isEmpty()) {
            return "Todos los campos son obligatorios.";
        }
        if (!nombre.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return "Nombre inválido. Solo se permiten letras y espacios.";
        }
        if (!apellido.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$")) {
            return "Apellido inválido. Solo se permiten letras y espacios.";
        }
        if (!validarRut(rut)) {
            return "RUT inválido. Debe contener entre 7 y 9 dígitos numéricos.";
        }
        return null; // Retorna null si todas las validaciones pasan
    }

    private boolean validarRut(String rut) {
        return rut.matches("^\\d{7,9}$"); // de 7 a 9 digitos
    }
}