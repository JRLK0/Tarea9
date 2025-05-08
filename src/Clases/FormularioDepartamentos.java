// FormularioDepartamentos.java adaptado al diseño de la tarea
package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class FormularioDepartamentos extends JFrame {
    private JTextField tfCodigo, tfNombre, tfLocalizacion, tfManager;
    private JTextArea areaListado;
    private GestorDepartamentos gestor;

    public FormularioDepartamentos() {
        super("Formulario de Gestión de Departamentos");
        gestor = new GestorDepartamentos();
        configurarVentana();
        actualizarListado();
    }

    private void configurarVentana() {
        setLayout(new GridLayout(1, 2));

        // Panel izquierdo - formulario
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(6, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Departamento:"));

        tfCodigo = new JTextField();
        tfNombre = new JTextField();
        tfLocalizacion = new JTextField();
        tfManager = new JTextField();

        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(tfCodigo);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(tfNombre);
        panelFormulario.add(new JLabel("ID Localización:"));
        panelFormulario.add(tfLocalizacion);
        panelFormulario.add(new JLabel("ID Manager:"));
        panelFormulario.add(tfManager);

        JButton btnInsertar = new JButton("Insertar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");

        panelFormulario.add(btnInsertar);
        panelFormulario.add(btnModificar);
        panelFormulario.add(btnBorrar);

        // Panel derecho - listado
        JPanel panelListado = new JPanel(new BorderLayout(5, 5));
        panelListado.setBorder(BorderFactory.createTitledBorder("Mostrar Datos del Departamento:"));

        areaListado = new JTextArea();
        areaListado.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaListado);
        panelListado.add(scroll, BorderLayout.CENTER);

        JButton btnMostrar = new JButton("Mostrar");
        JPanel panelBotonMostrar = new JPanel();
        panelBotonMostrar.add(btnMostrar);
        panelListado.add(panelBotonMostrar, BorderLayout.SOUTH);

        // Añadir ambos paneles al frame
        add(panelFormulario);
        add(panelListado);

        // Acciones de botones
        btnInsertar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                String nombre = tfNombre.getText().trim();
                int loc = Integer.parseInt(tfLocalizacion.getText().trim());
                int man = Integer.parseInt(tfManager.getText().trim());

                if (gestor.buscar(codigo) != null) {
                    JOptionPane.showMessageDialog(this, "Ese código ya existe.");
                    return;
                }

                gestor.insertar(new Departamento(codigo, nombre, loc, man));
                JOptionPane.showMessageDialog(this, "Insertado correctamente.");
                limpiarCampos(); actualizarListado();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnModificar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                Departamento d = gestor.buscar(codigo);
                if (d == null) {
                    JOptionPane.showMessageDialog(this, "No encontrado.");
                    return;
                }
                String nombre = tfNombre.getText().trim();
                int loc = Integer.parseInt(tfLocalizacion.getText().trim());
                int man = Integer.parseInt(tfManager.getText().trim());
                gestor.modificar(new Departamento(codigo, nombre, loc, man));
                JOptionPane.showMessageDialog(this, "Modificado correctamente.");
                limpiarCampos(); actualizarListado();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnBorrar.addActionListener(e -> {
            try {
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                if (gestor.buscar(codigo) == null) {
                    JOptionPane.showMessageDialog(this, "No encontrado.");
                    return;
                }
                gestor.borrar(codigo);
                JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
                limpiarCampos(); actualizarListado();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnMostrar.addActionListener(e -> actualizarListado());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 350);
        setVisible(true);
    }

    private void limpiarCampos() {
        tfCodigo.setText("");
        tfNombre.setText("");
        tfLocalizacion.setText("");
        tfManager.setText("");
    }

    private void actualizarListado() {
        List<Departamento> lista = gestor.listarTodos();
        areaListado.setText("");
        for (Departamento d : lista) {
            areaListado.append(d.toString() + "\n");
        }
    }
}
