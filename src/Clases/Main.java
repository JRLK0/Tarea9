/**
 * Main.java
 * 
 * Clase principal que contiene el punto de entrada de la aplicación.
 * Esta clase se encarga de iniciar la interfaz gráfica de usuario.
 */
package Clases;

public class Main {
    /**
     * Método principal que inicia la aplicación.
     * Crea una instancia del formulario de gestión de departamentos,
     * lo que automáticamente muestra la ventana principal de la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Crear y mostrar la ventana principal de la aplicación
        new FormularioDepartamentos();
    }
}