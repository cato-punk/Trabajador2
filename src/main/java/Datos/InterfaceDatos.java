package Datos;
import  Modelo.Trabajador;
import java.util.ArrayList; // arraylist para el retorno  con Empresa

public interface InterfaceDatos {
    ArrayList<Trabajador> read(); //retorna arraylist
    void write(ArrayList<Trabajador> trabajadores); //recibe arraylist
    boolean update(Trabajador trabajador);
    boolean delete(String rut);
    void open(); // abrir donde estan los datos
    void close(); //cerrar datos
    void agregarTrabajador(Trabajador trabajador);
}