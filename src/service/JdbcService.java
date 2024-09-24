package service;

import enums.CommandType;

import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

import static enums.CommandType.values;

public class JdbcService {

    public static boolean ongoingQuery = false;


    public static CommandType detectCommandType(String query) {
        String command = query.split(" ")[0].toUpperCase();
        return Arrays.stream(values()).map(commandType -> commandType.detectType(command))
                .findFirst().orElseThrow(() -> new RuntimeException("invalid SQL command"));
    }

    public static void execute(Statement statement, String query) throws SQLException {
        boolean successfulOperation = statement.execute(query);
        if (successfulOperation) System.out.println("operation was successful");
        else System.err.println("operation was failed");
    }

    public static void executeUpdate(Statement statement, String query) throws SQLException {
        int numOfAffectedRows = statement.executeUpdate(query);
        System.out.println("number of affected rows: " + numOfAffectedRows);
    }

    public static void executeQuery(Statement statement, String query) throws SQLException {
        ResultSet resultSet = statement.executeQuery(query);
        printResultSet(resultSet);
    }

    public static void processQuery(
            Statement statement,
            CommandType commandType,
            String query) throws SQLException {
        switch (commandType) {
            case DDL, DML -> executeUpdate(statement, query);
            case DQL -> executeQuery(statement, query);
            case TCL, DCL -> execute(statement, query);
            default -> System.err.println("please enter a valid SQL command");
        }
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        // get resultSet metadata and get column count
        ResultSetMetaData resultSetMD = resultSet.getMetaData();
        int numOfColumns = resultSetMD.getColumnCount();

        System.out.println();

        // print columns
        for (int i = 1; i <= numOfColumns; i++)
            System.out.print(resultSetMD.getColumnName(i) + " ");
        System.out.println();

        // print rows
        while (resultSet.next()) {
            for (int i = 1; i <= numOfColumns; i++)
                System.out.print(resultSet.getString(i) + " ");
            System.out.println();
        }
    }

    public static void performQuery(Connection conn) {
        ongoingQuery = true;

        try {
            String query = buildQueryInput();

            // get SQL commandType
            CommandType currentType = detectCommandType(query);

            // create a JDBC statement
            Statement statement = conn.createStatement();

            // execute the query using the statement
            processQuery(statement, currentType, query);

        } catch (Exception e) {
            System.err.println("failed to perform query: " + e.getMessage());
        }

        ongoingQuery = false;
    }

    private static boolean isCommandComplete(StringBuilder sb) {
        return sb.charAt(sb.length() - 1) != ';';
    }

    private static String buildQueryInput() {
        StringBuilder sb = new StringBuilder();

        System.out.println("# please enter query");
        Scanner scanner = new Scanner(System.in);

        do {
            sb.append(scanner.nextLine());
            if (isCommandComplete(sb)) sb.append(" ");
        } while (isCommandComplete(sb));

        return sb.toString();
    }
}
