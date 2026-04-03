package jesperknez;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:booktrack.sqlite";
    private static final String USER = "jesper";
    private static final String PASSWORD = "a2Qf1jX689";

    // Returns a working connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
