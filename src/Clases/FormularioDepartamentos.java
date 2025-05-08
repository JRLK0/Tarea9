/**
 * FormularioDepartamentos.java
 * 
 * Este archivo implementa una interfaz gráfica para la gestión de departamentos
 * usando Java Swing. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre la entidad Departamento a través de un formulario visual.
 */
package Clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Clase principal que extiende de JFrame para crear la ventana de la aplicación
 * de gestión de departamentos.
 */
public class FormularioDepartamentos extends JFrame {
    // Campos de texto para introducir y mostrar los datos de departamentos
    private JTextField tfCodigo, tfNombre, tfLocalizacion, tfManager;
    // Área de texto para mostrar el listado de todos los departamentos
    private JTextArea areaListado;
    // Gestor que maneja la conexión con la base de datos y las operaciones CRUD
    private GestorDepartamentos gestor;

    /**
     * Constructor de la clase que inicializa la ventana de la aplicación
     */
    public FormularioDepartamentos() {
        // Establece el título de la ventana
        super("Formulario de Gestión de Departamentos");
        // Inicializa el gestor de departamentos para la conexión con la base de datos
        gestor = new GestorDepartamentos();
        // Configura todos los componentes visuales y listeners de la ventana
        configurarVentana();
        // Actualiza el listado inicial de departamentos al abrir la aplicación
        actualizarListado();
    }

    /**
     * Método que configura todos los elementos visuales de la interfaz
     * y establece los manejadores de eventos
     */
    private void configurarVentana() {
        // Divide la ventana en dos columnas usando GridLayout
        setLayout(new GridLayout(1, 2));

        // Panel izquierdo - formulario para introducir datos
        JPanel panelFormulario = new JPanel();
        // 7 filas (campos + botones), 2 columnas con espaciado de 5 píxeles
        panelFormulario.setLayout(new GridLayout(7, 2, 5, 5));
        // Añade un borde con título al panel
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Departamento:"));

        // Inicializa los campos de texto para la entrada de datos
        tfCodigo = new JTextField();
        tfNombre = new JTextField();
        tfLocalizacion = new JTextField();
        tfManager = new JTextField();

        // Añade etiquetas y campos de texto al panel del formulario
        panelFormulario.add(new JLabel("Código:"));
        panelFormulario.add(tfCodigo);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(tfNombre);
        panelFormulario.add(new JLabel("ID Localización:"));
        panelFormulario.add(tfLocalizacion);
        panelFormulario.add(new JLabel("ID Manager:"));
        panelFormulario.add(tfManager);

        // Crea los botones para las operaciones CRUD
        JButton btnInsertar = new JButton("Insertar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBorrar = new JButton("Borrar");
        JButton btnBuscar = new JButton("Buscar");

        // Añade los botones al panel del formulario
        panelFormulario.add(btnInsertar);
        panelFormulario.add(btnModificar);
        panelFormulario.add(btnBorrar);
        panelFormulario.add(btnBuscar);

        // Panel derecho - para mostrar el listado de departamentos
        JPanel panelListado = new JPanel(new BorderLayout(5, 5));
        panelListado.setBorder(BorderFactory.createTitledBorder("Mostrar Datos del Departamento:"));

        // Área de texto para mostrar todos los departamentos, no editable
        areaListado = new JTextArea();
        areaListado.setEditable(false);
        // Añade scrollbars al área de texto para permitir desplazamiento
        JScrollPane scroll = new JScrollPane(areaListado);
        panelListado.add(scroll, BorderLayout.CENTER);

        // Botón para actualizar el listado de departamentos
        JButton btnMostrar = new JButton("Mostrar");
        JPanel panelBotonMostrar = new JPanel();
        panelBotonMostrar.add(btnMostrar);
        panelListado.add(panelBotonMostrar, BorderLayout.SOUTH);

        // Añade ambos paneles principales a la ventana
        add(panelFormulario);
        add(panelListado);

        // Configura la acción del botón Insertar usando expresiones lambda
        btnInsertar.addActionListener(e -> {
            try {
                // Obtiene los datos desde los campos de texto
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                String nombre = tfNombre.getText().trim();
                int loc = Integer.parseInt(tfLocalizacion.getText().trim());
                int man = Integer.parseInt(tfManager.getText().trim());

                // Verifica si el código ya existe para evitar duplicados
                if (gestor.buscar(codigo) != null) {
                    JOptionPane.showMessageDialog(this, "Ese código ya existe.");
                    return;
                }

                // Crea e inserta el nuevo departamento
                gestor.insertar(new Departamento(codigo, nombre, loc, man));
                JOptionPane.showMessageDialog(this, "Insertado correctamente.");
                // Limpia los campos y actualiza el listado tras la inserción
                limpiarCampos(); 
                actualizarListado();
            } catch (Exception ex) {
                // Captura y muestra cualquier error durante la inserción
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Configura la acción del botón Modificar
        btnModificar.addActionListener(e -> {
            try {
                // Obtiene el código y verifica que el departamento exista
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                Departamento d = gestor.buscar(codigo);
                if (d == null) {
                    JOptionPane.showMessageDialog(this, "No encontrado.");
                    return;
                }
                // Obtiene los nuevos datos de los campos de texto
                String nombre = tfNombre.getText().trim();
                int loc = Integer.parseInt(tfLocalizacion.getText().trim());
                int man = Integer.parseInt(tfManager.getText().trim());
                // Modifica el departamento con los nuevos datos
                gestor.modificar(new Departamento(codigo, nombre, loc, man));
                JOptionPane.showMessageDialog(this, "Modificado correctamente.");
                // Limpia los campos y actualiza el listado tras la modificación
                limpiarCampos(); 
                actualizarListado();
            } catch (Exception ex) {
                // Captura y muestra cualquier error durante la modificación
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Configura la acción del botón Borrar
        btnBorrar.addActionListener(e -> {
            try {
                // Obtiene el código y verifica que el departamento exista
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                Departamento dep = gestor.buscar(codigo);
                if (dep == null) {
                    JOptionPane.showMessageDialog(this, "No encontrado.");
                    return;
                }
                // Solicita confirmación antes de borrar
                int confirm = JOptionPane.showConfirmDialog(this, 
                    "¿Seguro que deseas borrar este departamento?", 
                    "Confirmar", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Si confirma, procede a borrar el departamento
                    gestor.borrar(codigo);
                    JOptionPane.showMessageDialog(this, "Eliminado correctamente.");
                    // Limpia los campos y actualiza el listado tras el borrado
                    limpiarCampos(); 
                    actualizarListado();
                }
            } catch (Exception ex) {
                // Captura y muestra cualquier error durante el borrado
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Configura la acción del botón Buscar
        btnBuscar.addActionListener(e -> {
            try {
                // Obtiene el código a buscar
                int codigo = Integer.parseInt(tfCodigo.getText().trim());
                // Busca el departamento por su código
                Departamento d = gestor.buscar(codigo);
                if (d != null) {
                    // Si lo encuentra, rellena los campos con sus datos
                    tfNombre.setText(d.getNombre());
                    tfLocalizacion.setText(String.valueOf(d.getIdLocalizacion()));
                    tfManager.setText(String.valueOf(d.getIdManager()));
                } else {
                    // Si no lo encuentra, muestra un mensaje
                    JOptionPane.showMessageDialog(this, "Departamento no encontrado.");
                }
            } catch (Exception ex) {
                // Captura y muestra cualquier error durante la búsqueda
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        // Configura la acción del botón Mostrar para actualizar el listado
        btnMostrar.addActionListener(e -> actualizarListado());

        // Añade detector de doble clic en el área de listado para cargar datos
        areaListado.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                // Verifica si se hizo doble clic
                if (e.getClickCount() == 2) {
                    // Obtiene el texto seleccionado
                    String linea = areaListado.getSelectedText();
                    if (linea == null || linea.isEmpty()) return;
                    // Divide la línea por el separador " - "
                    String[] partes = linea.split(" - ");
                    // Si tiene los 4 datos del departamento, los carga en los campos
                    if (partes.length >= 4) {
                        tfCodigo.setText(partes[0].trim());
                        tfNombre.setText(partes[1].trim());
                        tfLocalizacion.setText(partes[2].trim());
                        tfManager.setText(partes[3].trim());
                    }
                }
            }
        });

        // Configura propiedades básicas de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cierra la aplicación al cerrar la ventana
        setSize(700, 400);  // Establece el tamaño inicial
        setVisible(true);  // Hace visible la ventana
    }

    /**
     * Método para limpiar todos los campos de texto del formulario
     */
    private void limpiarCampos() {
        tfCodigo.setText("");
        tfNombre.setText("");
        tfLocalizacion.setText("");
        tfManager.setText("");
    }

    /**
     * Método para actualizar el listado de departamentos en el área de texto
     * Obtiene todos los departamentos de la base de datos y los muestra
     */
    private void actualizarListado() {
        // Obtiene la lista de todos los departamentos
        List<Departamento> lista = gestor.listarTodos();
        // Limpia el área de texto
        areaListado.setText("");
        // Añade cada departamento en una nueva línea
        for (Departamento d : lista) {
            areaListado.append(d.toString() + "\n");
        }
    }
}
