import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DualDatabaseManager {

    private static final String SQL_URL = "jdbc:sqlite:ledger.db";

    // 1. Logic: Update the SQL 'Truth' Ledger
    public void syncSqlLedger(int recordId, String status) {
        String sql = "INSERT INTO ledger(id, status) VALUES(?,?)";
        
        try (Connection conn = DriverManager.getConnection(SQL_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recordId);
            pstmt.setString(2, status);
            pstmt.executeUpdate();
            System.out.println("SQL Ledger Updated: ID " + recordId);
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    // 2. Logic: Sync the 'Fuzzy' JSON Payload
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
        // Test a Dual Sync
        manager.syncSqlLedger(101, "SYNCED");
        manager.syncJsonStore(101, "{ 'ai_prediction': '90%', 'notes': 'Verified' }");
    }
}
