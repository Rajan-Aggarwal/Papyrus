package database;
import java.sql.*;

public class DAO
{
    private static Connection conn = null;

    public static Connection getConnection() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","oracle_user","password");

        } catch (Exception e) {
            System.out.println(e);
        }

        return conn;

    }


}