package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bdc_course";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "@root888";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (RuntimeException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
