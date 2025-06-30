package Datos;

import Modelo.Empresa;
import Modelo.Trabajador;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class DatosTrabajador implements InterfaceDatos {
    private Empresa empresa;
    private static final String NOMBRE_ARCHIVO_JSON = "trabajadores.json";

    public DatosTrabajador() {
        this.empresa = new Empresa();
        loadDataFromFile();
    }

    @Override
    public ArrayList<Trabajador> read() {
        return empresa.listarTrabajadores();
    }

    @Override
    public void write(ArrayList<Trabajador> trabajadores) {
        this.empresa = new Empresa();
        for (Trabajador t : trabajadores) {
            this.empresa.agregarTrabajador(t);
        }
        saveDataToFile();
    }

    @Override
    public boolean update(Trabajador trabajador) {
        boolean success = empresa.actualizarTrabajador(trabajador);
        if (success) {
            saveDataToFile();
        }
        return success;
    }

    @Override
    public boolean delete(String rut) {
        boolean success = empresa.eliminarTrabajador(rut);
        if (success) {
            saveDataToFile();
        }
        return success;
    }

    @Override
    public void open() {
        System.out.println("Fuente de datos '" + NOMBRE_ARCHIVO_JSON + "' abierta.");
    }

    @Override
    public void close() {
        System.out.println("Fuente de datos '" + NOMBRE_ARCHIVO_JSON + "' cerrada. Asegurando guardado final.");
        saveDataToFile();
    }

    @Override
    public void agregarTrabajador(Trabajador trabajador) {
        this.empresa.agregarTrabajador(trabajador);
        saveDataToFile(); //
    }

    private void loadDataFromFile() {
        File file = new File(NOMBRE_ARCHIVO_JSON);
        if (file.exists() && file.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder jsonString = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonString.append(line);
                }

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Trabajador>>(){}.getType();
                ArrayList<Trabajador> loadedTrabajadores = gson.fromJson(jsonString.toString(), listType);

                if (loadedTrabajadores != null) {
                    for (Trabajador t : loadedTrabajadores) {
                        this.empresa.agregarTrabajador(t);
                    }
                }
                System.out.println("Datos cargados desde " + NOMBRE_ARCHIVO_JSON);

            } catch (IOException e) {
                System.err.println("Error al cargar datos desde " + NOMBRE_ARCHIVO_JSON + ": " + e.getMessage());
            }
        } else {
            System.out.println("Archivo " + NOMBRE_ARCHIVO_JSON + " no encontrado o vacío. Iniciando con datos vacíos.");
        }
    }

    private void saveDataToFile() {
        ArrayList<Trabajador> trabajadoresToSave = empresa.listarTrabajadores();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(trabajadoresToSave);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO_JSON))) {
            writer.write(jsonString);
            System.out.println("Datos guardados en " + NOMBRE_ARCHIVO_JSON);
        } catch (IOException e) {
            System.err.println("Error al guardar datos en " + NOMBRE_ARCHIVO_JSON + ": " + e.getMessage());
        }
    }
}