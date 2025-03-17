package studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AttendanceDAO {
    
    public boolean addAttendance(int studentId, int present) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed.");
            return false;
        }

String sql = "INSERT INTO attendance (student_id, date, present) VALUES (?, CURRENT_DATE, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, present);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewAttendanceReport() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }

        String sql = "SELECT a.id, s.name, a.date, a.present FROM attendance a JOIN students s ON a.student_id = s.id";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== Attendance Report ===");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String date = rs.getString("date");
                boolean present = rs.getBoolean("present");
                String status = present ? "Present" : "Absent";

                System.out.printf("ID: %d | Name: %s | Date: %s | Status: %s%n", id, name, date, status);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving attendance report.");
            e.printStackTrace();
        }
    }
}
