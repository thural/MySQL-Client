import connection.DatabaseConnection;

import java.sql.Connection;

import static service.JdbcService.ongoingQuery;
import static service.JdbcService.performQuery;

public class Main extends DatabaseConnection {


    public static void main(String[] args) {

        try (Connection conn = getConnection()) {
            assert conn != null;
            System.out.println("connection has established");

            while (!ongoingQuery) performQuery(conn);

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}