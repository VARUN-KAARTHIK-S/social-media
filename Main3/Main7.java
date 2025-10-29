package Main3;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main7 {

    static final int TOTAL_PERIODS = 7;
    
    // Database configuration - Update these values
    private static final String DB_URL = "jdbc:mysql://localhost:3306/attendance_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "9298"; // Your MySQL password
    
    // Load MySQL JDBC driver
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found! Please add mysql-connector-java.jar to classpath");
            e.printStackTrace();
        }
    }
    
    /**
     * Get a database connection
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    /**
     * Test database connection
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println("Database connection successful!");
            return true;
        } catch (SQLException e) {
            System.err.println("Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please check:");
            System.err.println("1. MySQL server is running");
            System.err.println("2. Database 'attendance_db' exists");
            System.err.println("3. Username and password are correct");
            return false;
        }
    }

    public static void main(String[] args) {
        // Test database connection first
        if (!testConnection()) {
            System.out.println("Cannot start application without database connection.");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        String date = getTodayDate();

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Mark attendance for a period");
            System.out.println("2. Print attendance table for today");
            System.out.println("3. Add student");
            System.out.println("4. Remove student");
            System.out.println("5. Show all students");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    markAttendance(sc, date);
                    break;
                case 2:
                    printAttendance(date);
                    break;
                case 3:
                    addStudent(sc);
                    break;
                case 4:
                    removeStudent(sc);
                    break;
                case 5:
                    showStudents();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    // -------------------------------
    // Utility: Get today's date
    // -------------------------------
    static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new java.util.Date());
    }

    // -------------------------------
    // 1. Mark Attendance
    // -------------------------------
    static void markAttendance(Scanner sc, String date) {
        List<Integer> rolls = new ArrayList<>();
        Map<Integer, String> students = new LinkedHashMap<>();

        // Load students
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT roll, name FROM students ORDER BY roll")) {
            while (rs.next()) {
                students.put(rs.getInt("roll"), rs.getString("name"));
                rolls.add(rs.getInt("roll"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        if (students.isEmpty()) {
            System.out.println("No students found! Add students first.");
            return;
        }

        int period = 0;
        while (period < 1 || period > TOTAL_PERIODS) {
            System.out.print("Enter period number (1-" + TOTAL_PERIODS + "): ");
            period = sc.nextInt();
            sc.nextLine();
        }

        for (Map.Entry<Integer, String> entry : students.entrySet()) {
            int roll = entry.getKey();
            String name = entry.getValue();

            System.out.print("Attendance for " + name + " (P/A/OD): ");
            String att = sc.nextLine().trim().toUpperCase();
            if (!att.equals("P") && !att.equals("A") && !att.equals("OD")) att = "A";

            saveAttendanceToDB(date, roll, period, att);
        }
        System.out.println("Attendance saved successfully for period " + period);
    }

    // -------------------------------
    // Save Attendance to DB
    // -------------------------------
    static void saveAttendanceToDB(String date, int roll, int period, String att) {
        String select = "SELECT * FROM attendance WHERE date=? AND roll=?";
        String insert = "INSERT INTO attendance (date, roll, period" + period + ") VALUES (?, ?, ?)";
        String update = "UPDATE attendance SET period" + period + "=? WHERE date=? AND roll=?";

        try (Connection conn = getConnection()) {
            PreparedStatement ps = conn.prepareStatement(select);
            ps.setString(1, date);
            ps.setInt(2, roll);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                PreparedStatement up = conn.prepareStatement(update);
                up.setString(1, att);
                up.setString(2, date);
                up.setInt(3, roll);
                up.executeUpdate();
            } else {
                PreparedStatement ins = conn.prepareStatement(insert);
                ins.setString(1, date);
                ins.setInt(2, roll);
                ins.setString(3, att);
                ins.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------
    // 2. Print Attendance
    // -------------------------------
    static void printAttendance(String date) {
        String sql = "SELECT s.roll, s.name, a.period1, a.period2, a.period3, a.period4, a.period5, a.period6, a.period7 " +
                     "FROM students s LEFT JOIN attendance a ON s.roll = a.roll AND a.date = ? ORDER BY s.roll";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, date);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nAttendance Summary for " + date);
            System.out.println("Roll\tName\t\tP\tOD\tA");

            while (rs.next()) {
                int pCount = 0, odCount = 0, aCount = 0;
                for (int i = 1; i <= TOTAL_PERIODS; i++) {
                    String att = rs.getString("period" + i);
                    if (att == null || att.equals("A")) aCount++;
                    else if (att.equals("P")) pCount++;
                    else if (att.equals("OD")) odCount++;
                }
                System.out.println(rs.getInt("roll") + "\t" + rs.getString("name") +
                                   "\t\t" + pCount + "\t" + odCount + "\t" + aCount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------
    // 3. Add Student
    // -------------------------------
    static void addStudent(Scanner sc) {
        System.out.print("Enter new student name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty!");
            return;
        }

        String sql = "INSERT INTO students (name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.executeUpdate();
            System.out.println("Student added: " + name);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------
    // 4. Remove Student
    // -------------------------------
    static void removeStudent(Scanner sc) {
        System.out.print("Enter roll number to remove: ");
        int roll = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM students WHERE roll = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roll);
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Student removed successfully!");
            else
                System.out.println("Student not found!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------------------
    // 5. Show Students
    // -------------------------------
    static void showStudents() {
        String sql = "SELECT roll, name FROM students ORDER BY roll";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nStudents:");
            while (rs.next()) {
                System.out.println(rs.getInt("roll") + " - " + rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}