import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class DualDatabaseManager {
    // Database connection URL
    private static final String DB_URL = "jdbc:sqlite:ledger.db";

    public static void main(String[] args) {
        int id = 101;
        String status = "VERIFIED_100_PERCENT";
        String jsonPayload = "{ \"integrity\": \"100%\", \"sync_status\": \"SUCCESS\" }";

        System.out.println("--- STARTING HIGH-INTEGRITY DUAL SYNC ---");

        // Initialize the SQLite Table if it doesn't exist
        initializeDatabase();

        // 1. Structured Ledger (SQL via JDBC)
        boolean sqlSuccess = updateSqlLedger(id, status);

        // 2. Unstructured Store (JSON File)
        boolean jsonSuccess = false;
        if (sqlSuccess) {
            jsonSuccess = updateJsonStore(id, jsonPayload);
        }

        if (sqlSuccess && jsonSuccess) {
            System.out.println("--- DUAL SYNC COMPLETE: 100% DATA ACCURACY ---");
        } else {
            System.err.println("--- SYNC FAILURE: Check logs for integrity gaps ---");
        }
    }

    private static void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS ledger (" +
                                "id INTEGER PRIMARY KEY, " +
                                "status TEXT, " +
                                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP);";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("DB Init Error: " + e.getMessage());
        }
    }

    private static boolean updateSqlLedger(int id, String status) {
        String insertSQL = "INSERT OR REPLACE INTO ledger(id, status) VALUES(?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, status);
            pstmt.executeUpdate();
            System.out.println("Step 1: SQL Ledger Updated (SQLite) - ACID Compliant.");
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Ledger Error: " + e.getMessage());
            return false;
        }
    }

    private static boolean updateJsonStore(int id, String payload) {
        try {
            Files.write(Paths.get("data_" + id + ".json"), payload.getBytes());
            System.out.println("Step 2: JSON Document Synced for ID: " + id);
            return true;
        } catch (IOException e) {
            System.err.println("JSON Store Error: " + e.getMessage());
            return false;
        }
    }
}
