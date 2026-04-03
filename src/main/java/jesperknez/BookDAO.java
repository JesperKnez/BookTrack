package jesperknez;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BookDAO
{
    public boolean updateTitle(int bookID, String title)
    {
        String sql = "UPDATE Books SET title = ? WHERE bookID = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, title);
            stmt.setInt(2, bookID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error updating book: " + e.getMessage());
            return false;
        }
    }
}
