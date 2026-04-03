package jesperknez;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadingHabitDAO
{
    public List<ReadingHabit> getAll()
    {
        List<ReadingHabit> habits = new ArrayList<>();
        String sql = "SELECT * FROM ReadingHabit ORDER BY habitID;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                ReadingHabit habit = new ReadingHabit(
                        rs.getInt("habitID"),
                        rs.getInt("userID"),
                        rs.getInt("bookID"),
                        rs.getInt("pagesRead"),
                        rs.getString("submissionMoment")
                );

                habits.add(habit);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching reading habits: " + e.getMessage());
        }
        return habits;
    }

    public List<ReadingHabit> getByUserId(int userId) {
        List<ReadingHabit> habits = new ArrayList<>();

        String sql = """
        SELECT h.habitID, h.userID, h.pagesRead, h.submissionMoment,\s
               b.bookID, b.title\s
        FROM ReadingHabit h
        INNER JOIN Books b ON h.bookID = b.bookID
        WHERE h.userID = ?;
       \s""";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book(
                            rs.getInt("bookID"),
                            rs.getString("title")
                    );

                    ReadingHabit habit = new ReadingHabit(
                            rs.getInt("habitID"),
                            rs.getInt("userID"),
                            book,
                            rs.getInt("pagesRead"),
                            rs.getString("submissionMoment")
                    );

                    habits.add(habit);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching habits with join: " + e.getMessage());
        }

        return habits;
    }

    public void deleteRow(int habitID) {
        String sql = "DELETE FROM ReadingHabit WHERE habitID = ?;";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, habitID);

            int affectedRows = stmt.executeUpdate();

        } catch (SQLException e) {
            // Bericht even aangepast
            System.err.println("Error deleting habit: " + e.getMessage());
        }

    }

    public int getTotalPagesRead()
    {
        String sql = "SELECT SUM(pagesRead) FROM ReadingHabit;";
        int total = 0;

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return total;
    }
}
