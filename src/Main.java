import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    static List<String> DDL = List.of("CREATE", "DROP", "ALTER", "TRUNCATE");
    static List<String> DML = List.of("INSERT", "UPDATE", "DELETE", "CALL", "EXPLAIN");
    static List<String> TCL = List.of("COMMIT", "SAVEPOINT", "ROLLBACK", "SET");
    static List<String> DCL = List.of("GRANT", "REVOKE");
    static List<String> DQL = List.of("SELECT");


    static CommandType detectCommandType(String query) {
        String command = query.split(" ")[0];
        if (DDL.contains(command)) return CommandType.DDL;
        else if (DML.contains(command)) return CommandType.DML;
        else if (TCL.contains(command)) return CommandType.TCL;
        else if (DQL.contains(command)) return CommandType.DQL;
        else if (DCL.contains(command)) return CommandType.DCL;
        else return CommandType.NULL;
    }

    static void execute(Statement statement, String query) throws SQLException {
        boolean successfulOperation = statement.execute(query);
        if (successfulOperation) {
            System.out.println("operation was successful");
        } else System.out.println("operation was failed");
    }

    static void executeUpdate(Statement statement, String query) throws SQLException {
        int numOfAffectedRows = statement.executeUpdate(query);
        System.out.println("number of affected rows: " + numOfAffectedRows);
    }

    static void executeQuery(Statement statement, String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        printResultSet(resultSet);
    }

    static void performQuery(
            Statement statement,
            CommandType commandType,
            String query) throws SQLException {
        switch (commandType) {
            case DDL -> executeUpdate(statement, query);
            case DML, DQL -> executeQuery(statement, query);
            case TCL, DCL -> execute(statement, query);
            default -> System.out.println("please enter a valid SQL command");
        }
    }

    static void printResultSet(ResultSet resultSet) throws SQLException {
        // get resultSet metadata and get column count
        ResultSetMetaData resultSetMD = resultSet.getMetaData();
        int columnsNumber = resultSetMD.getColumnCount();

        // print the output
        System.out.println("query output: ");
        System.out.println();

        // print columns
        for (int i = 1; i <= columnsNumber; i++)
            System.out.print(resultSetMD.getColumnName(i) + " ");
        System.out.println();

        // print rows
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++)
                System.out.print(resultSet.getString(i) + " ");
            System.out.println();
        }
    }

    static void customQuery(Connection conn) throws SQLException {
        // get and store query from user input
        System.out.println("please enter query for the DB");
        Scanner scanner = new Scanner(System.in);
        String query = scanner.nextLine();

        // get SQL commandType
        CommandType currentType = detectCommandType(query);

        // create a JDBC statement
        Statement statement = conn.createStatement();

        // execute the query using the statement
        performQuery(statement, currentType, query);
    }

    public static void main(String[] args) {
        String dbURL = "jdbc:mysql://localhost:3306/record_company";
        String username = "root";
        String password = "@dolphin888";

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