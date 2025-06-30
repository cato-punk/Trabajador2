package Modelo;
import static org.junit.jupiter.api.Assertions.*;
import Modelo.Trabajador;

public class TrabajadorTest {

    public static void main(String[] args) {

        testCrearTrabajador();
        testActualizarDatosTrabajador();
        testToString();
    }

    public static void testCrearTrabajador() {
        System.out.println("--- Test: Crear Trabajador ---");
        Trabajador t1 = new Trabajador("Juan", "Perez", "1234567-8", "Colmena", "Habitat");
        System.out.println("Trabajador creado: " + t1);

        if (t1.getNombre().equals("Juan") && t1.getApellido().equals("Perez") &&
                t1.getRut().equals("1234567-8") && t1.getIsapre().equals("Colmena") &&
                t1.getAfp().equals("Habitat")) {
            System.out.println("Resultado: OK - Trabajador creado correctamente.");
        } else {
            System.out.println("Resultado: FALLO - Error al crear trabajador.");
        }
        System.out.println();
    }

    public static void testActualizarDatosTrabajador() {
        System.out.println("--- Test: Actualizar Datos Trabajador ---");
        Trabajador t2 = new Trabajador("Maria", "Gonzalez", "8765432-1", "Banmedica", "Modelo");
        System.out.println("Trabajador inicial: " + t2);

        t2.setIsapre("CruzBlanca");
        t2.setAfp("ProVida");
        System.out.println("Trabajador actualizado: " + t2);

        if (t2.getIsapre().equals("CruzBlanca") && t2.getAfp().equals("ProVida")) {
            System.out.println("Resultado: OK - Datos de Isapre y AFP actualizados.");
        } else {
            System.out.println("Resultado: FALLO - Error al actualizar datos.");
        }
        System.out.println();
    }

    public static void testToString() {
        System.out.println("--- Test: Método toString() ---");
        Trabajador t3 = new Trabajador("Carlos", "Muñoz", "9999999-9", "Consalud", "Cuprum");
        String expectedToString = "Trabajador{" +
                "nombre='Carlos', apellido='Muñoz', rut='9999999-9', " +
                "isapre='Consalud', afp='Cuprum'}";
        String actualToString = t3.toString();
        System.out.println("toString() esperado: " + expectedToString);
        System.out.println("toString() actual:    " + actualToString);

        if (actualToString.equals(expectedToString)) {
            System.out.println("Resultado: OK - Método toString() correcto.");
        } else {
            System.out.println("Resultado: FALLO - Método toString() incorrecto.");
        }
        System.out.println();
    }
}