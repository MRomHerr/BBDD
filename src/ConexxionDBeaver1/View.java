package ConexxionDBeaver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase para crear y consultar una vista en una base de datos MySQL.
 * La vista muestra el numero de empleados y el salario medio por departamento.
 */
public class View {
    /**
     * Metodo principal que crea la vista 'totales' y muestra sus resultados.
     * @param args Argumentos de linea de comando (no utilizados).
     */
    public static void main(String[] args) {
        try {
            //carga el driver de mysql
            Class.forName("com.mysql.cj.jdbc.Driver");

            //establece la conexion con la base de datos 'hospital'
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/hospital", "root", "MRomHerr");

            //crea la sentencia sql para la vista 'totales'
            StringBuilder sql = new StringBuilder();
            sql.append("CREATE OR REPLACE VIEW totales AS ");
            sql.append("SELECT d.dept_no AS dep, d.dnombre, COUNT(e.emp_no) AS nemp, AVG(e.salario) AS media ");
            sql.append("FROM DEPT d ");
            sql.append("LEFT JOIN EMP e ON e.dept_no = d.dept_no ");
            sql.append("GROUP BY d.dept_no, d.dnombre;");

            //ejecuta la creacion de la vista
            Statement sentencia = conexion.createStatement();
            int filas = sentencia.executeUpdate(sql.toString());

            //informa sobre la creacion de la vista
            System.out.printf("Vista 'totales' creada correctamente. Filas afectadas: %d%n", filas);

            //consulta la vista 'totales'
            String consulta = "SELECT * FROM totales";
            ResultSet rs = sentencia.executeQuery(consulta);

            //muestra los resultados de la vista
            System.out.println("Resultados de la vista 'totales':");
            while (rs.next()) {
                int dep = rs.getInt("dep");
                String dnombre = rs.getString("dnombre");
                int nemp = rs.getInt("nemp");
                double media = rs.getDouble("media");

                System.out.printf("Departamento: %d, Nombre: %s, Número de empleados: %d, Salario medio: %.2f%n",
                        dep, dnombre, nemp, media);
            }

            //cierra los recursos
            rs.close();
            sentencia.close();
            conexion.close();
        } catch (ClassNotFoundException cnfe) {
            //maneja error al cargar el driver jdbc
            System.err.println("Error al cargar el driver JDBC");
            cnfe.printStackTrace();
        } catch (SQLException sqle) {
            //maneja error al ejecutar la consulta sql
            System.err.println("Error al ejecutar la consulta SQL");
            sqle.printStackTrace();
        }
    }
}