import java.sql.*;

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

    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/record_company";
        String username = "root";
        String password = "@dolphin888";
        String getSongQuery = "SELECT name FROM songs LIMIT 9";
        String addSongQuery = "INSERT INTO " +
                "songs(id,name,length,album_id) " +
                "VALUES (8,'Beyond Today (Farewell Pt. 3)',5+(06/60),1)";

        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            if (conn != null) System.out.println("Connected");
            assert conn != null;

            getSongNamesQuery(conn, getSongQuery);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
