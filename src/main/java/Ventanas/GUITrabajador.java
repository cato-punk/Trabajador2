package Ventanas;

import Controlador.TrabajadorControlador;
import Modelo.Trabajador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUITrabajador extends JFrame implements ActionListener {
    //atrib GUI
    private JTextField txtNombre, txtApellido, txtRut, txtIsapre, txtAfp;
    private JButton bLeer, bGuardar, bActualizar, bEliminar, bLimpiar;
    private JTextArea taSalida;
    //dependencia de controlador
    private TrabajadorControlador controlador;

    public GUITrabajador(TrabajadorControlador controlador) {
        this.controlador = controlador;
        setTitle("Gestión de Trabajadores v02");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        addListeners();

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                controlador.closeDataSource(); //cierra datos
                System.out.println("Aplicación cerrada y fuente de datos liberada.");
                System.exit(0);
            }
        });
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel pDatos = new JPanel(new GridLayout(5, 2, 5, 5));
        pDatos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pDatos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        pDatos.add(txtNombre);

        pDatos.add(new JLabel("Apellido:"));
        txtApellido = new JTextField();
        pDatos.add(txtApellido);

        pDatos.add(new JLabel("RUT:"));
        txtRut = new JTextField();
        pDatos.add(txtRut);

        pDatos.add(new JLabel("Isapre:"));
        txtIsapre = new JTextField();
        pDatos.add(txtIsapre);

        pDatos.add(new JLabel("AFP:"));
        txtAfp = new JTextField();
        pDatos.add(txtAfp);

        add(pDatos, BorderLayout.CENTER);

        JPanel pBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        bLeer = new JButton("Leer RUT");
        bGuardar = new JButton("Guardar Nuevo");
        bActualizar = new JButton("Actualizar");
        bEliminar = new JButton("Eliminar");
        bLimpiar = new JButton("Limpiar Campos");

        pBotones.add(bLeer);
        pBotones.add(bGuardar);
        pBotones.add(bActualizar);
        pBotones.add(bEliminar);
        pBotones.add(bLimpiar);

        add(pBotones, BorderLayout.NORTH);

        taSalida = new JTextArea(5, 40);
        taSalida.setEditable(false);
        JScrollPane scrollSalida = new JScrollPane(taSalida);
        add(scrollSalida, BorderLayout.SOUTH);
    }

    private void addListeners() {
        bLeer.addActionListener(this);
        bGuardar.addActionListener(this);
        bActualizar.addActionListener(this);
        bEliminar.addActionListener(this);
        bLimpiar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String mensajeResultado = "";
        Trabajador trabajadorEncontrado = null;

        if (e.getSource() == bLeer) {
            String rut = txtRut.getText().trim();
            trabajadorEncontrado = controlador.buscarTrabajadorPorRut(rut);
            if (trabajadorEncontrado != null) {
                mostrarTrabajadorEnCampos(trabajadorEncontrado);
                mensajeResultado = "Trabajador encontrado: " + trabajadorEncontrado.getNombre() + " " + trabajadorEncontrado.getApellido();
            } else {
                mensajeResultado = "Trabajador con RUT " + rut + " no encontrado o RUT inválido.";
                limpiarCamposExceptoRut();
            }
        } else if (e.getSource() == bGuardar) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String rut = txtRut.getText().trim();
            String isapre = txtIsapre.getText().trim();
            String afp = txtAfp.getText().trim();

            mensajeResultado = controlador.guardarNuevoTrabajador(nombre, apellido, rut, isapre, afp);
            if (!mensajeResultado.contains("Error") && !mensajeResultado.contains("existe")) {
                limpiarCampos();
            }
        } else if (e.getSource() == bActualizar) {
            String nombre = txtNombre.getText().trim();
            String apellido = txtApellido.getText().trim();
            String rut = txtRut.getText().trim();
            String isapre = txtIsapre.getText().trim();
            String afp = txtAfp.getText().trim();

            mensajeResultado = controlador.actualizarTrabajador(nombre, apellido, rut, isapre, afp);
            if (!mensajeResultado.contains("no encontrado") && !mensajeResultado.contains("inválido")) {
                limpiarCampos();
            }
        } else if (e.getSource() == bEliminar) {
            String rut = txtRut.getText().trim();
            int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar al trabajador con RUT " + rut + "?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                mensajeResultado = controlador.eliminarTrabajador(rut);
                if (!mensajeResultado.contains("no encontrado") && !mensajeResultado.contains("inválido")) {
                    limpiarCampos();
                }
            } else {
                mensajeResultado = "Eliminación cancelada.";
            }
        } else if (e.getSource() == bLimpiar) {
            limpiarCampos();
            mensajeResultado = "Campos limpiados.";
        }

        mostrarMensaje(mensajeResultado);
    }


    private void mostrarTrabajadorEnCampos(Trabajador trabajador) {
        txtNombre.setText(trabajador.getNombre());
        txtApellido.setText(trabajador.getApellido());
        txtRut.setText(trabajador.getRut());
        txtIsapre.setText(trabajador.getIsapre());
        txtAfp.setText(trabajador.getAfp());
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtRut.setText("");
        txtIsapre.setText("");
        txtAfp.setText("");
        taSalida.setText("");
    }

    private void limpiarCamposExceptoRut() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtIsapre.setText("");
        txtAfp.setText("");
        taSalida.setText("");
    }

    private void mostrarMensaje(String mensaje) {
        taSalida.setText(mensaje);
    }
}