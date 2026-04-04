import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DualDatabaseManager {

    private static final String SQL_URL = "jdbc:sqlite:ledger.db";

    // This block runs the SECOND the class is loaded to force the driver to wake up
    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found in classpath!");
        }
    }

    public void syncSqlLedger(int recordId, String status) {
        try (Connection conn = DriverManager.getConnection(SQL_URL)) {
            // Create the table if it doesn't exist
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS ledger (id INTEGER, status TEXT)");
            }
            
            // Insert the data
            String sql = "INSERT INTO ledger(id, status) VALUES(?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, recordId);
                pstmt.setString(2, status);
                pstmt.executeUpdate();
                System.out.println("SQL Ledger Updated: ID " + recordId);
            }
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public void syncJsonStore(int recordId, String jsonPayload) {
        try {
            Files.write(Paths.get("data_" + recordId + ".json"), jsonPayload.getBytes());
            System.out.println("JSON Document Synced for ID: " + recordId);
        } catch (Exception e) {
            System.out.println("JSON Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        DualDatabaseManager manager = new DualDatabaseManager();
        manager.syncSqlLedger(101, "SYNCED");
        manager.syncJsonStore(101, "{ 'ai_prediction': '90%', 'notes': 'Verified' }");
    }
}
