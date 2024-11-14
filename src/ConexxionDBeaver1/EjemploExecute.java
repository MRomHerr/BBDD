package ConexxionDBeaver1;

import java.sql.*;

public class EjemploExecute {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // CONEXIÓN A MYSQL
        // Se carga el driver de MySQL para establecer la conexión
        Class.forName("com.mysql.cj.jdbc.Driver"); // Nombre actualizado del driver para versiones más nuevas de MySQL
        // Se establece la conexión con la base de datos 'hospital'
        Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/hospital", "root", "MRomHerr");

        // Consulta SQL para seleccionar todos los registros de la tabla 'departamentos'
        String sql = "SELECT * FROM DEPT";
        // Se crea un objeto Statement para ejecutar la consulta SQL
        Statement sentencia = conexion.createStatement();

        // Ejecutamos la consulta SQL y verificamos si devuelve un ResultSet
        boolean valor = sentencia.execute(sql);

        if (valor) {
            // Si la consulta devuelve un ResultSet (por ejemplo, una consulta SELECT)
            // Obtenemos el ResultSet con los resultados de la consulta
            ResultSet rs = sentencia.getResultSet();
            // Recorremos el ResultSet e imprimimos los datos de cada fila
            while (rs.next()) {
                System.out.printf("%d, %s, %s %n",
                        rs.getInt(1), rs.getString(2), rs.getString(3));
            }
            // Cerramos el ResultSet para liberar los recursos
            rs.close();
        } else {
            // Si la consulta no devuelve un ResultSet (por ejemplo, una consulta de actualización como INSERT, UPDATE o DELETE)
            // Obtenemos el número de filas afectadas por la consulta
            int f = sentencia.getUpdateCount();
            System.out.printf("Filas afectadas: %d \n", f);
        }

        // Cerramos el Statement y la conexión para liberar los recursos
        sentencia.close();
        conexion.close();
    } // Fin del metodo main
} // Fin de la clase EjemploExecute
