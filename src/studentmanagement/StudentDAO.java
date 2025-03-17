package studentmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import studentmanagement.DBConnection;


class StudentDAO {
    public void viewAllStudents() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }
        
        String sql = "SELECT * FROM students";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n=== Student List ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", DOB: " + rs.getString("dob") + ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void addStudent(String name, String dob, String email) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}