package Clases;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorDepartamentos {
    private Connection conexion;

    public GestorDepartamentos() {
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar a la base de datos
            conexion = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/09074313P",
                "root",            // Usuario
                "admin123"         // Contraseña (la del contenedor Docker)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertar(Departamento d) {
        try {
            String sql = "INSERT INTO departamentos (codigo, nombre, id_localizacion, id_manager) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, d.getCodigo());
            ps.setString(2, d.getNombre());
            ps.setInt(3, d.getIdLocalizacion());
            ps.setInt(4, d.getIdManager());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Departamento buscar(int codigo) {
        try {
            String sql = "SELECT * FROM departamentos WHERE codigo = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Departamento(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("id_localizacion"),
                    rs.getInt("id_manager")
                );
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void modificar(Departamento d) {
        try {
            String sql = "UPDATE departamentos SET nombre=?, id_localizacion=?, id_manager=? WHERE codigo=?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, d.getNombre());
            ps.setInt(2, d.getIdLocalizacion());
            ps.setInt(3, d.getIdManager());
            ps.setInt(4, d.getCodigo());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrar(int codigo) {
        try {
            String sql = "DELETE FROM departamentos WHERE codigo=?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, codigo);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Departamento> listarTodos() {
        List<Departamento> lista = new ArrayList<>();
        try {
            String sql = "SELECT * FROM departamentos";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                lista.add(new Departamento(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("id_localizacion"),
                    rs.getInt("id_manager")
                ));
            }
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void cerrar() {
        try {
            if (conexion != null) conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
