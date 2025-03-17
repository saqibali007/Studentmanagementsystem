package studentmanagement;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            UserDAO userDAO = new UserDAO();
            StudentDAO studentDAO = new StudentDAO();
            AttendanceDAO attendanceDAO = new AttendanceDAO();
            GradeDAO gradeDAO = new GradeDAO();
            
            while (true) {
                System.out.println("\n=== Student Management System ===");
                System.out.println("1. Create Account");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                
                if (choice == 3) {
                    System.out.println("Exiting... Goodbye!");
                    break;
                }
                
                System.out.print("Enter Username: ");
                String username = scanner.nextLine();
                System.out.print("Enter Password: ");
                String password = scanner.nextLine();
                
                if (choice == 1) {
                    System.out.print("Enter Role (admin/teacher/student): ");
                    String role = scanner.nextLine();
                    userDAO.registerUser(username, password, role);
                    System.out.println("Account Created Successfully! Please Login.");
                    continue;
                }
                
                User authenticatedUser = userDAO.authenticateUser(username, password);
                if (authenticatedUser == null) {
                    System.out.println("Invalid login. Try again.");
                    continue;
                }
                
                String role = authenticatedUser.getRole();
                System.out.println("Logged in as: " + role);
                
                switch (role) {
                    case "admin":
                        AdminMenu(scanner, studentDAO, attendanceDAO, gradeDAO);
                        break;
                    case "teacher":
                        TeacherMenu(scanner, attendanceDAO, gradeDAO);
                        break;
                    case "student":
                        StudentMenu(scanner, authenticatedUser.getUsername(), attendanceDAO, gradeDAO);
                        break;
                    default:
                        System.out.println("Invalid role.");
                }
            }
        }
    }

    private static void AdminMenu(Scanner scanner, StudentDAO studentDAO, AttendanceDAO attendanceDAO, GradeDAO gradeDAO) {
        while (true) {
            System.out.println("\n=== Admin Menu ===");
            System.out.println("1. Add Student");
            System.out.println("2. Manage Attendance");
            System.out.println("3. Add Grades");
            System.out.println("4. Generate Reports");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int adminChoice = scanner.nextInt();
            scanner.nextLine();

            switch (adminChoice) {
                case 1:
                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Date of Birth (yyyy-MM-dd): ");
                    String dob = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    studentDAO.addStudent(name, dob, email);
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Enter Attendance (1=Present, 0=Absent): ");
                    int status = scanner.nextInt();
                    attendanceDAO.addAttendance(studentId, status);
                    break;
                case 3:
                    System.out.print("Enter Student ID: ");
                    int sid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter Grade: ");
                    double grade = scanner.nextDouble();
                    gradeDAO.addGrade(sid, subject, grade);
                    break;
                case 4:
                    generateReports(attendanceDAO, studentDAO, gradeDAO);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void TeacherMenu(Scanner scanner, AttendanceDAO attendanceDAO, GradeDAO gradeDAO) {
        while (true) {
            System.out.println("\n=== Teacher Menu ===");
            System.out.println("1. Manage Attendance");
            System.out.println("2. Manage Grades");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Student ID: ");
                    int studentId = scanner.nextInt();
                    System.out.print("Enter Attendance (1 = Present, 0 = Absent): ");
                    int attendance = scanner.nextInt();
                    scanner.nextLine();
                    attendanceDAO.addAttendance(studentId, attendance);
                    break;
                case 2:
                    System.out.print("Enter Student ID: ");
                    int sid = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Subject: ");
                    String subject = scanner.nextLine();
                    System.out.print("Enter Grade: ");
                    double grade = scanner.nextDouble();
                    scanner.nextLine();
                    gradeDAO.addGrade(sid, subject, grade);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void StudentMenu(Scanner scanner, String username, AttendanceDAO attendanceDAO, GradeDAO gradeDAO) {
        System.out.println("\n=== Student Menu ===");
        System.out.println("1. View Attendance");
        System.out.println("2. View Grades");
        System.out.println("3. Logout");
        System.out.print("Enter your choice: ");

        int studentChoice = scanner.nextInt();
        scanner.nextLine();

        switch (studentChoice) {
            case 1 -> attendanceDAO.viewAttendanceReport();
            case 2 -> gradeDAO.viewGradesReport();
            case 3 -> {
                System.out.println("Logging out...");
                return;
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
    }

    private static void generateReports(AttendanceDAO attendanceDAO, StudentDAO studentDAO, GradeDAO gradeDAO) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}