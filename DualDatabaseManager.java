import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DualDatabaseManager {

    // 1. The "Structured" Store (Simulates SQL Ledger)
    public void syncStructuredLedger(int recordId, String status) {
        try {
            String csvLine = recordId + "," + status + "," + System.currentTimeMillis() + "\n";
            Files.write(Paths.get("ledger_db.csv"), csvLine.getBytes(), 
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Structured Ledger Updated (CSV): ID " + recordId);
        } catch (Exception e) {
            System.out.println("Ledger Error: " + e.getMessage());
        }
    }

    // 2. The "Unstructured" Store (JSON Payload)
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
        // Execute Dual Sync
        manager.syncStructuredLedger(101, "VERIFIED");
        manager.syncJsonStore(101, "{ 'accuracy': '90%', 'status': 'Stable' }");
    }
}
