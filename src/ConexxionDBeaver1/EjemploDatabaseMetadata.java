package ConexxionDBeaver1;

import java.sql.*;

public class EjemploDatabaseMetadata {
    public static void main(String[] args) {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión con la BD HOSPITAL
            Connection conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost/hospital", "root", "MRomHerr"); // Cambia a tus credenciales

            // Obtenemos metadatos de la base de datos
            DatabaseMetaData dbmd = conexion.getMetaData();

            // Información general sobre la base de datos
            String nombre = dbmd.getDatabaseProductName();
            String driver = dbmd.getDriverName();
            String url = dbmd.getURL();
            String usuario = dbmd.getUserName();

            System.out.println("INFORMACIÓN SOBRE LA BASE DE DATOS:");
            System.out.printf("Nombre: %s %n", nombre);
            System.out.printf("Driver: %s %n", driver);
            System.out.printf("URL: %s %n", url);
            System.out.printf("Usuario: %s %n", usuario);

            // Obtener y mostrar información de todas las tablas en la base de datos HOSPITAL
            ResultSet resul = dbmd.getTables(null, "hospital", null, new String[]{"TABLE"});
            Statement stmt = conexion.createStatement();

            while (resul.next()) {
                String tableName = resul.getString("TABLE_NAME");
                System.out.println("\nContenido de la tabla: " + tableName);

                // Ejecutar SELECT * en cada tabla
                ResultSet tableData = stmt.executeQuery("SELECT * FROM " + tableName);
                ResultSetMetaData rsmd = tableData.getMetaData();

                // Obtener y mostrar los nombres de las columnas
                int columnCount = rsmd.getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsmd.getColumnName(i) + "\t");
                }
                System.out.println();

                // Mostrar los datos de cada fila de la tabla
                while (tableData.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(tableData.getString(i) + "\t");
                    }
                    System.out.println();
                }
                tableData.close();
            }

            // Cerrar conexión y statement
            stmt.close();
            resul.close();
            conexion.close();
            System.out.println("Desconectado de la base de datos.");

        } catch (ClassNotFoundException cn) {
            cn.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
