// Departamento.java
// Esta clase representa la entidad Departamento que almacena información sobre departamentos de una organización.
// Contiene atributos básicos como código, nombre, identificador de localización e identificador de manager.
package Clases;

public class Departamento {
    // Identificador único del departamento
    private int codigo;
    
    // Nombre descriptivo del departamento
    private String nombre;
    
    // Identificador de la localización física donde se encuentra el departamento
    private int idLocalizacion;
    
    // Identificador del empleado que gestiona/dirige el departamento
    private int idManager;

    /**
     * Constructor para crear un nuevo objeto Departamento con todos sus atributos
     * @param codigo Identificador único del departamento
     * @param nombre Nombre del departamento
     * @param idLocalizacion Identificador de la ubicación del departamento
     * @param idManager Identificador del gestor/director del departamento
     */
    public Departamento(int codigo, String nombre, int idLocalizacion, int idManager) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.idLocalizacion = idLocalizacion;
        this.idManager = idManager;
    }

    // Métodos getter y setter para cada atributo
    
    /**
     * Devuelve el código del departamento
     * @return código del departamento
     */
    public int getCodigo() { return codigo; }
    
    /**
     * Establece el código del departamento
     * @param codigo nuevo código a asignar
     */
    public void setCodigo(int codigo) { this.codigo = codigo; }

    /**
     * Devuelve el nombre del departamento
     * @return nombre del departamento
     */
    public String getNombre() { return nombre; }
    
    /**
     * Establece el nombre del departamento
     * @param nombre nuevo nombre a asignar
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Devuelve el ID de localización del departamento
     * @return ID de localización
     */
    public int getIdLocalizacion() { return idLocalizacion; }
    
    /**
     * Establece el ID de localización del departamento
     * @param idLocalizacion nuevo ID de localización
     */
    public void setIdLocalizacion(int idLocalizacion) { this.idLocalizacion = idLocalizacion; }

    /**
     * Devuelve el ID del manager del departamento
     * @return ID del manager
     */
    public int getIdManager() { return idManager; }
    
    /**
     * Establece el ID del manager del departamento
     * @param idManager nuevo ID de manager
     */
    public void setIdManager(int idManager) { this.idManager = idManager; }

    /**
     * Sobrescribe el método toString para mostrar la información del departamento en formato de texto
     * @return Cadena con el formato: "código - nombre - idLocalización - idManager"
     */
    @Override
    public String toString() {
        return codigo + " - " + nombre + " - " + idLocalizacion + " - " + idManager;
    }
}