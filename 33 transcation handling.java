import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class TransactionHandling {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    public static void main(String[] args) {
       
        setupAccountsTable();

        
        transferMoney(1, 2, 150.00); 
        transferMoney(1, 3, 5000.00); 
        displayAccountBalances();
    }

    
    private static void setupAccountsTable() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            
            stmt.execute("DROP TABLE IF EXISTS accounts"); 
            stmt.execute("CREATE TABLE accounts (id INT PRIMARY KEY, balance DOUBLE)");
            stmt.execute("INSERT INTO accounts (id, balance) VALUES (1, 1000.00)");
            stmt.execute("INSERT INTO accounts (id, balance) VALUES (2, 500.00)");
            stmt.execute("INSERT INTO accounts (id, balance) VALUES (3, 200.00)"); 
            System.out.println("Accounts table set up with initial balances.");

        } catch (SQLException e) {
            System.err.println("Error setting up accounts table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    
    public static void transferMoney(int fromAccountId, int toAccountId, double amount) {
        Connection conn = null;
        PreparedStatement debitStmt = null;
        PreparedStatement creditStmt = null;
        PreparedStatement checkBalanceStmt = null;

        try {
            conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            conn.setAutoCommit(false); 

            System.out.println("\nAttempting to transfer $" + amount + " from account " + fromAccountId + " to account " + toAccountId);

            
            String checkSql = "SELECT balance FROM accounts WHERE id = ?";
            checkBalanceStmt = conn.prepareStatement(checkSql);
            checkBalanceStmt.setInt(1, fromAccountId);
            ResultSet rs = checkBalanceStmt.executeQuery();

            if (rs.next()) {
                double senderBalance = rs.getDouble("balance");
                if (senderBalance < amount) {
                    throw new SQLException("Insufficient funds in account " + fromAccountId + " for transfer.");
                }
            } else {
                throw new SQLException("Sender account " + fromAccountId + " not found.");
            }
            rs.close(); 

            
            String debitSql = "UPDATE accounts SET balance = balance - ? WHERE id = ?";
            debitStmt = conn.prepareStatement(debitSql);
            debitStmt.setDouble(1, amount);
            debitStmt.setInt(2, fromAccountId);
            int debitRows = debitStmt.executeUpdate();

            if (debitRows == 0) {
                throw new SQLException("Failed to debit account " + fromAccountId + ". Account not found or no rows updated.");
            }

            String creditSql = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
            creditStmt = conn.prepareStatement(creditSql);
            creditStmt.setDouble(1, amount);
            creditStmt.setInt(2, toAccountId);
            int creditRows = creditStmt.executeUpdate();

            if (creditRows == 0) {
                throw new SQLException("Failed to credit account " + toAccountId + ". Account not found or no rows updated.");
            }

            
            conn.commit();
            System.out.println("Transaction successful: $" + amount + " transferred from " + fromAccountId + " to " + toAccountId);

        } catch (SQLException e) {
            
            try {
                if (conn != null) {
                    conn.rollback();
                    System.err.println("Transaction rolled back due to: " + e.getMessage());
                }
            } catch (SQLException ex) {
                System.err.println("Error during rollback: " + ex.getMessage());
            }
            e.printStackTrace();
        } finally {
            
            try {
                if (checkBalanceStmt != null) checkBalanceStmt.close();
                if (debitStmt != null) debitStmt.close();
                if (creditStmt != null) creditStmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close()
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    private static void displayAccountBalances() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, balance FROM accounts ORDER BY id")) {

            System.out.println("\n--- Current Account Balances ---");
            while (rs.next()) {
                System.out.println("Account ID: " + rs.getInt("id") + ", Balance: $" + rs.getDouble("balance"));
            }
            System.out.println("--------------------------------");
        } catch (SQLException e) {
            System.err.println("Error displaying balances: " + e.getMessage());
            e.printStackTrace();
        }
    }
}