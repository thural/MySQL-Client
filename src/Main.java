import java.sql.*;
import java.util.Scanner;

public class Main {
    static void addSongQuery(Connection conn, String query) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute(query);
    }

    static void getSongNamesQuery(Connection conn, String query) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + ", ");
        }
    }

    static void customQuery(Connection conn) throws SQLException {
        // get and store query from user input
        System.out.println("please enter query to the DB");
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();

        // execute the query and store the result
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        // get resultSet metadata and get column count
        ResultSetMetaData resultSetMD = resultSet.getMetaData();
        int columnsNumber = resultSetMD.getColumnCount();

        // print the output
        System.out.println("query output: ");
        System.out.println();

        // print columns
        for (int i = 1; i < columnsNumber; i++)
            System.out.print(resultSetMD.getColumnName(i) + " ");
        System.out.println();

        // print rows
        while (resultSet.next()) {
            for (int i = 1; i < columnsNumber; i++)
                System.out.print(resultSet.getString(i) + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/record_company";
        String username = "root";
        String password = "@dolphin888";

        // some queries stored as String
        String getSongQuery = "SELECT name FROM songs LIMIT 9";
        String addSongQuery = "INSERT INTO " +
                "songs(id,name,length,album_id) " +
                "VALUES (8,'Beyond Today (Farewell Pt. 3)',5+(06/60),1)";

        // connect to database and execute some queries
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            if (conn != null) System.out.println("Connected");
            assert conn != null;

            // use custom query from user input
            customQuery(conn);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}