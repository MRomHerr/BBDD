package ConexxionDBeaver1;

import java.sql.*;
import java.util.Scanner;

/**
 * Clase para gestionar operaciones en la tabla DEPT de una base de datos MySQL
 * utilizando PreparedStatement. Permite mostrar datos e insertar nuevos departamentos.
 */
public class PreparedStatementDep {
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
                System.out.println("1. Mostrar datos de la tabla DEPT");
                System.out.println("2. Insertar un nuevo departamento en la tabla DEPT");
                System.out.println("3. Salir");
                System.out.print("Selecciona una opción: ");
                int opcion = sc.nextInt();
                sc.nextLine(); //consume el salto de linea despues del numero

                switch (opcion) {
                    case 1:
                        //llama al metodo para mostrar los datos de la tabla dept
                        mostrarDatosDept(conexion);
                        break;

                    case 2:
                        //llama al metodo para insertar un nuevo departamento
                        insertarDepartamento(conexion, sc);
                        break;

                    case 3:
                        //cierra conexiones y sale del programa
                        System.out.println("Saliendo...");
                        conexion.close();
                        sc.close();
                        return;

                    default:
                        System.out.println("Opción no válida. Intenta de nuevo.");
                }
            }
        } catch (ClassNotFoundException e) {
            //maneja error de carga del driver
            System.err.println("Error al cargar el driver de MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            //maneja error de conexion o interaccion con la base de datos
            System.err.println("Error al conectar o interactuar con la base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Muestra todos los datos de la tabla DEPT usando PreparedStatement.
     * @param conexion La conexion a la base de datos.
     */
    private static void mostrarDatosDept(Connection conexion) {
        String sql = "SELECT * FROM DEPT";
        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\nDatos de la tabla DEPT:");
            while (rs.next()) {
                //extrae datos de cada fila y los muestra
                int depNo = rs.getInt("DEPT_NO");
                String dnombre = rs.getString("DNOMBRE");
                String loc = rs.getString("LOC");

                System.out.printf("Departamento N°: %d, Nombre: %s, Localidad: %s%n",
                        depNo, dnombre, loc);
            }
        } catch (SQLException e) {
            //maneja error al mostrar los datos
            System.err.println("Error al mostrar los datos de la tabla DEPT.");
            e.printStackTrace();
        }
    }

    /**
     * Inserta un nuevo departamento en la tabla DEPT usando PreparedStatement.
     * @param conexion La conexion a la base de datos.
     * @param scanner El Scanner para leer la entrada del usuario.
     */
    private static void insertarDepartamento(Connection conexion, Scanner scanner) {
        //solicita datos del nuevo departamento al usuario
        System.out.print("\nIntroduce el número del departamento: ");
        int depNo = scanner.nextInt();
        scanner.nextLine(); //consume el salto de linea despues del numero
        System.out.print("Introduce el nombre del departamento: ");
        String dnombre = scanner.nextLine();
        System.out.print("Introduce la localidad del departamento: ");
        String loc = scanner.nextLine();

        //prepara y ejecuta la consulta sql para insertar el nuevo departamento
        String sql = "INSERT INTO DEPT (DEPT_NO, DNOMBRE, LOC) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, depNo);
            stmt.setString(2, dnombre);
            stmt.setString(3, loc);

            int filas = stmt.executeUpdate();
            System.out.printf("Departamento insertado. Filas afectadas: %d%n", filas);
        } catch (SQLException e) {
            //maneja error al insertar el departamento
            System.err.println("Error al insertar el departamento.");
            e.printStackTrace();
        }
    }
}