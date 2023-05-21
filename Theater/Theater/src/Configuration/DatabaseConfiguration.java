package Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {
    private static final String DB_URL = "jdbc:mysql://localhost:3310/theaterdatabase";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection databaseConnection;

    private DatabaseConfiguration() {}

    public static synchronized Connection getDatabaseConnection() {
        try {
            if (databaseConnection == null || databaseConnection.isClosed()) {
                databaseConnection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            }
        } catch (SQLException exception) {
            System.out.println("\n\uF0FB We weren't able to open the database! \uF0FB\n");
        }
        return databaseConnection;
    }
}
