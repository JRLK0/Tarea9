/**
 * GestorDepartamentos.java
 * Esta clase maneja la conexión a la base de datos y realiza operaciones CRUD 
 * (Crear, Leer, Actualizar, Eliminar) para la entidad Departamento.
 * Implementa el patrón DAO (Data Access Object) para gestionar la persistencia
 * de los departamentos en una base de datos MySQL.
 */
package Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorDepartamentos {
    // Objeto Connection para manejar la conexión a la base de datos
    private Connection conexion;

    /**
     * Constructor que establece la conexión con la base de datos MySQL.
     * Carga el driver JDBC y establece la conexión usando credenciales específicas.
     */
    public GestorDepartamentos() {
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer conexión con la base de datos
            // Se especifica la URL de conexión, el usuario y la contraseña
            conexion = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/09074313P", // URL de la base de datos
                "root",            // Usuario de MySQL
                "admin123"         // Contraseña (la utilizada en el contenedor Docker)
            );
        } catch (Exception e) {
            // Captura y muestra cualquier error durante la conexión
            e.printStackTrace();
        }
    }

    /**
     * Inserta un nuevo departamento en la base de datos.
     * @param d El objeto Departamento que contiene los datos a insertar
     */
    public void insertar(Departamento d) {
        try {
            // Prepara la sentencia SQL para la inserción con parámetros
            String sql = "INSERT INTO departamentos (codigo, nombre, id_localizacion, id_manager) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            
            // Asigna los valores del departamento a los parámetros de la consulta
            ps.setInt(1, d.getCodigo());
            ps.setString(2, d.getNombre());
            ps.setInt(3, d.getIdLocalizacion());
            ps.setInt(4, d.getIdManager());
            
            // Ejecuta la consulta de inserción
            ps.executeUpdate();
            
            // Cierra el PreparedStatement para liberar recursos
            ps.close();
        } catch (SQLException e) {
            // Captura y muestra errores de SQL
            e.printStackTrace();
        }
    }

    /**
     * Busca un departamento por su código en la base de datos.
     * @param codigo El código único del departamento a buscar
     * @return El objeto Departamento si es encontrado, o null si no existe
     */
    public Departamento buscar(int codigo) {
        try {
            // Prepara la sentencia SQL para buscar por código
            String sql = "SELECT * FROM departamentos WHERE codigo = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            // Ejecuta la consulta y obtiene el resultado
            ResultSet rs = ps.executeQuery();
            
            // Si encuentra un resultado, crea y devuelve un objeto Departamento con los datos
            if (rs.next()) {
                return new Departamento(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("id_localizacion"),
                    rs.getInt("id_manager")
                );
            }
            
            // Cierra los recursos
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Si no encuentra nada o hay un error, retorna null
        return null;
    }

    /**
     * Modifica los datos de un departamento existente en la base de datos.
     * @param d El objeto Departamento con los nuevos datos (el código debe existir)
     */
    public void modificar(Departamento d) {
        try {
            // Prepara la sentencia SQL para actualizar todos los campos excepto el código
            String sql = "UPDATE departamentos SET nombre=?, id_localizacion=?, id_manager=? WHERE codigo=?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            
            // Asigna los nuevos valores a los parámetros
            ps.setString(1, d.getNombre());
            ps.setInt(2, d.getIdLocalizacion());
            ps.setInt(3, d.getIdManager());
            ps.setInt(4, d.getCodigo());
            
            // Ejecuta la consulta de actualización
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un departamento de la base de datos por su código.
     * @param codigo El código del departamento a eliminar
     */
    public void borrar(int codigo) {
        try {
            // Prepara la sentencia SQL para eliminar por código
            String sql = "DELETE FROM departamentos WHERE codigo=?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codigo);
            
            // Ejecuta la consulta de eliminación
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Recupera todos los departamentos almacenados en la base de datos.
     * @return Una lista con todos los objetos Departamento
     */
    public List<Departamento> listarTodos() {
        // Crea una lista vacía para almacenar los resultados
        List<Departamento> lista = new ArrayList<>();
        try {
            // Prepara la sentencia SQL para seleccionar todos los departamentos
            String sql = "SELECT * FROM departamentos";
            Statement st = conexion.createStatement();
            
            // Ejecuta la consulta y obtiene los resultados
            ResultSet rs = st.executeQuery(sql);
            
            // Procesa cada fila del resultado y añade un nuevo Departamento a la lista
            while (rs.next()) {
                lista.add(new Departamento(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("id_localizacion"),
                    rs.getInt("id_manager")
                ));
            }
            
            // Cierra los recursos
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Devuelve la lista completa de departamentos
        return lista;
    }

    /**
     * Cierra la conexión con la base de datos cuando ya no se necesita.
     * Debe llamarse cuando se termine de usar el gestor.
     */
    public void cerrar() {
        try {
            // Verifica que la conexión exista antes de intentar cerrarla
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
