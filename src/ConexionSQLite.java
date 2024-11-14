import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class ConexionSQLite {
    public static void main(String[] args) {
        // Ruta completa de la base de datos SQLite
        String url = "jdbc:sqlite:C:/Users/aludam2/Desktop/cositas/sqlite-tools-win-x64-3470000/ejemplo.db";  // Asegúrate de usar '/' en lugar de '\'

        // Establecer la conexión
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida.");

                // Crear un Statement para ejecutar consultas SQL
                Statement stmt = conn.createStatement();

                // Ejecutar una consulta para obtener todos los datos de la tabla 'departamentos'
                String query = "SELECT * FROM departamentos";
                ResultSet rs = stmt.executeQuery(query);

                // Mostrar los resultados
                while (rs.next()) {
                    int deptNo = rs.getInt("dept_no");
                    String dnombre = rs.getString("dnombre");
                    String loc = rs.getString("loc");

                    System.out.println("Dept No: " + deptNo + ", Nombre: " + dnombre + ", Ubicación: " + loc);
                }

            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            System.out.println("Error en la conexión a SQLite: " + e.getMessage());
        }
    }
}
