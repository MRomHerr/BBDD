package ConexxionDBeaver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Clase para gestionar operaciones de consulta y actualizacion de salarios
 * en la tabla EMP de una base de datos MySQL.
 */
public class Update {
    /**
     * Metodo principal que ejecuta el programa y muestra un menu de opciones.
     * @param args Argumentos de linea de comando (no utilizados).
     */
    public static void main(String[] args) {
        try {
            //carga el driver de mysql
            Class.forName("com.mysql.cj.jdbc.Driver");

            //establece la conexion con la base de datos
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/hospital", "root", "MRomHerr");

            //menu de opciones
            Scanner sc = new Scanner(System.in);
            int opcion;

            do {
                //muestra el menu y solicita una opcion
                System.out.println("Menú:");
                System.out.println("1. Mostrar salarios");
                System.out.println("2. Hacer un Update de salario");
                System.out.println("3. Salir");
                System.out.print("Elige una opción: ");
                opcion = sc.nextInt();

                switch (opcion) {
                    case 1:
                        mostrarSalarios(conexion);
                        break;
                    case 2:
                        hacerUpdate(conexion, sc);
                        break;
                    case 3:
                        System.out.println("¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (opcion != 3);

            //cierra la conexion y el scanner
            conexion.close();
            sc.close();

        } catch (ClassNotFoundException | SQLException e) {
            //maneja errores de conexion o de base de datos
            System.err.println("Error de conexión o de base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra los salarios de todos los empleados en la tabla EMP.
     * @param conexion La conexion a la base de datos.
     */
    public static void mostrarSalarios(Connection conexion) {
        try {
            Statement sentencia = conexion.createStatement();
            String query = "SELECT EMP_NO, APELLIDO, SALARIO FROM EMP";
            ResultSet rs = sentencia.executeQuery(query);

            //muestra los datos de cada empleado
            System.out.println("Empleados y salarios:");
            while (rs.next()) {
                int empNo = rs.getInt("EMP_NO");
                String apellido = rs.getString("APELLIDO");
                int salario = rs.getInt("SALARIO");
                System.out.printf("Empleado: %d, Apellido: %s, Salario: %d%n", empNo, apellido, salario);
            }

            rs.close();
            sentencia.close();
        } catch (SQLException e) {
            //maneja error al mostrar los salarios
            System.err.println("Error al mostrar los salarios.");
            e.printStackTrace();
        }
    }

    /**
     * Actualiza el salario de un empleado especifico.
     * @param conexion La conexion a la base de datos.
     * @param scanner El Scanner para leer la entrada del usuario.
     */
    public static void hacerUpdate(Connection conexion, Scanner scanner) {
        try {
            //solicita datos para el update
            System.out.print("Introduce el número del empleado (EMP_NO): ");
            int empNo = scanner.nextInt();
            System.out.print("Introduce el incremento en el salario: ");
            int incremento = scanner.nextInt();

            //construye y ejecuta la sentencia sql para el update
            String sql = String.format("UPDATE EMP SET SALARIO = SALARIO + %d WHERE EMP_NO = %d", incremento, empNo);
            Statement sentencia = conexion.createStatement();
            int filas = sentencia.executeUpdate(sql);

            System.out.printf("Salarios de %d empleados modificados.%n", filas);

            sentencia.close();
        } catch (SQLException e) {
            //maneja error al actualizar el salario
            System.err.println("Error al actualizar el salario.");
            e.printStackTrace();
        }
    }
}