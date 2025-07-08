
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StudentDAO { 

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    
    public void insertStudent(String name, int age) { 
        String sql = "INSERT INTO students (name, age) VALUES (?, ?)"; 
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully for student: " + name);

        } catch (SQLException e) {
            System.err.println("Error inserting student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public void updateStudentAge(int studentId, int newAge) { 
        String sql = "UPDATE students SET age = ? WHERE id = ?"; 
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, newAge);
            pstmt.setInt(2, studentId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(rowsAffected + " row(s) updated successfully for student ID: " + studentId);
            } else {
                System.out.println("No student found with ID: " + studentId + " to update.");
            }

        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StudentDAO dao = new StudentDAO();

        dao.insertStudent("Charlie", 25);
        dao.insertStudent("Diana", 19);
        dao.updateStudentAge(1, 21); 
        dao.updateStudentAge(3, 26); 
    }
}