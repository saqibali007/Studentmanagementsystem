package studentmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class GradeDAO {
    public void viewGradesReport() {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }
        
        String sql = "SELECT student_id, subject, grade FROM grades";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            System.out.println("\n=== Grades Report ===");
            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("student_id") + ", Subject: " + rs.getString("subject") + ", Grade: " + rs.getDouble("grade"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving grades report.");
            e.printStackTrace();
        }
    }

    public void addGrades(int studentId) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter Subject: ");
            String subject = scanner.nextLine();

            System.out.print("Enter Grade: ");
            while (!scanner.hasNextDouble()) {  // Ensure valid double input
                System.out.println("Invalid input. Please enter a valid grade.");
                scanner.next(); // Clear invalid input
            }
            double grade = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline

            addGrade(studentId, subject, grade);

            System.out.print("Do you want to enter another grade? (yes/no): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("yes")) {
                break;
            }
        }
    }

    void addGrade(int studentId, String subject, double grade) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed.");
            return;
        }

        String sql = "INSERT INTO grades (student_id, subject, grade) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setString(2, subject);
            stmt.setDouble(3, grade);
            stmt.executeUpdate();
            System.out.println("Grade added successfully for subject: " + subject);
        } catch (SQLException e) {
            System.out.println("Error adding grade.");
            e.printStackTrace();
        }
    }
}
