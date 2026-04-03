package jesperknez;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO
{
    public void addUser(int age, String gender, String Name)
    {
        String sql = "INSERT INTO User (age, gender, Name) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, age);
            stmt.setString(2, gender);
            stmt.setString(3, Name);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM User ORDER BY id ASC;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all patients: " + e.getMessage());
        }
        return users;
    }

    public int getUsersMeanAge()
    {
        String sql = "SELECT AVG(age) FROM User";

        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching all patients: " + e.getMessage());
        }
        return 0;
    }

    public int countUniqueUsersForBook(int bookId) {
        String sql = "SELECT COUNT(DISTINCT userID) FROM ReadingHabit WHERE bookID = ?;";
        int count = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error counting book " + bookId + ": " + e.getMessage());
        }

        return count;
    }



    private User mapRowToUser(ResultSet rs) throws SQLException
    {
        return new User(
                rs.getInt("userID"),
                rs.getInt("age"),
                rs.getString("gender"),
                rs.getString("Name")
        );
    }

    public int countUsersWithMultipleBooks() {
        String sql = """
        SELECT COUNT(*) FROM (
            SELECT userID\s
            FROM ReadingHabit\s
            GROUP BY userID\s
            HAVING COUNT(DISTINCT bookID) > 1
        ) AS multi_readers;
       \s""";

        int count = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return count;
    }
}
