package ConexxionDBeaver1;

import java.sql.*;
import java.util.Scanner;

/**
 * Clase para gestionar la actualizacion de comisiones de empleados en una base de datos MySQL.
 */
public class UpdateComisionEMP {
    /**
     * Metodo principal que ejecuta el programa y muestra un menu de opciones.
     * @param args Argumentos de linea de comando (no utilizados).
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            //carga el driver de mysql
            Class.forName("com.mysql.cj.jdbc.Driver");

            //establece la conexion con la base de datos
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/hospital", "root", "MRomHerr");

            while (true) {
                //muestra el menu de opciones
                System.out.println("\nMenú de Opciones:");
                System.out.println("1. Mostrar empleados antes de modificar la comisión");
                System.out.println("2. Modificar la comisión de los empleados en un departamento");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");
                int opcion = sc.nextInt();
                sc.nextLine(); //consume el salto de linea despues del numero

                switch (opcion) {
                    case 1:
                        //muestra los empleados antes de la modificacion
                        mostrarEmpleados(conexion);
                        break;

                    case 2:
                        //solicita el numero de departamento y el incremento de comision
                        System.out.print("\nIntroduce el número de departamento: ");
                        String dep = sc.nextLine();
                        System.out.print("Introduce el incremento de la comisión: ");
                        float incrementoComision = sc.nextFloat();

                        //modifica la comision de los empleados en el departamento
                        actualizarComisionEmpleados(conexion, dep, incrementoComision);
                        break;

                    case 3:
                        //sale del programa
                        System.out.println("Saliendo...");
                        conexion.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }
            }
        } catch (ClassNotFoundException e) {
            //maneja error al cargar el driver de mysql
            System.err.println("Error al cargar el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            //maneja error de conexion o interaccion con la base de datos
            System.err.println("Error al conectar o interactuar con la base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra los empleados de la tabla EMP.
     * @param conexion La conexion a la base de datos.
     */
    private static void mostrarEmpleados(Connection conexion) {
        String sql = "SELECT EMP_NO, APELLIDO, COMISION, DEPT_NO FROM EMP";
        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\nLista de empleados:");
            while (rs.next()) {
                //extrae y muestra los datos de cada empleado
                int empNo = rs.getInt("EMP_NO");
                String apellido = rs.getString("APELLIDO");
                float comision = rs.getFloat("COMISION");
                int deptNo = rs.getInt("DEPT_NO");

                System.out.printf("Empleado N°: %d, Apellido: %s, Comisión: %.2f, Departamento N°: %d%n",
                        empNo, apellido, comision, deptNo);
            }
        } catch (SQLException e) {
            //maneja error al mostrar los empleados
            System.err.println("Error al mostrar los empleados.");
            e.printStackTrace();
        }
    }

    /**
     * Actualiza la comision de los empleados de un departamento especifico.
     * @param conexion La conexion a la base de datos.
     * @param dep El numero de departamento.
     * @param incrementoComision El incremento de comision a aplicar.
     */
    private static void actualizarComisionEmpleados(Connection conexion, String dep, float incrementoComision) {
        //sentencia sql para actualizar la comision de los empleados de un departamento
        String sql = "UPDATE EMP SET COMISION = COMISION + ? WHERE DEPT_NO = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            //establece el incremento de la comision y el numero de departamento
            stmt.setFloat(1, incrementoComision);  //incremento de la comision
            stmt.setInt(2, Integer.parseInt(dep));  //numero de departamento

            //ejecuta la sentencia update
            int filas = stmt.executeUpdate();
            System.out.printf("Filas afectadas (comisiones modificadas): %d%n", filas);

        } catch (SQLException e) {
            //maneja error al actualizar la comision de los empleados
            System.err.println("Error al actualizar la comisión de los empleados.");
            e.printStackTrace();
        }
    }
}