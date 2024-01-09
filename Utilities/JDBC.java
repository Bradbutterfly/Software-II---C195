package Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class JDBC {
    private static final String protocol = "JDBC";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String JDBCUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String userName = "sqlUser";
    private static final String password = "Passw0rd!";
    private static Connection connection;
    private static PreparedStatement preparedStatement;
    /**
     * Connects to database
     */
    public static void openConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(JDBCUrl, userName, password);
            System.out.println("Connection successful!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /**
     * Closes connection to database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (Exception e) {
            System.out.println("Error:" + e.getMessage());
        }
    }
    /**
     * Gets connection
     */
    public static Connection getConnection() {
        return connection;
    }
    /**
     * Makes prepared statement
     * @param sqlStatement sql
     * @param connection connection
     * @throws SQLException sql
     */
    public static void makePreparedStatement(String sqlStatement, Connection connection) throws SQLException {
        if (connection != null)
            preparedStatement = connection.prepareStatement(sqlStatement);
        else
            System.out.println("Prepared Statement Creation Failed!");
    }
    /**
     * Gets prepared statement
     * @throws SQLException sql
     */
    public static PreparedStatement getPreparedStatement() throws SQLException {
        if (preparedStatement != null)
            return preparedStatement;
        else System.out.println("Null reference to Prepared Statement");
        return null;
    }
}


