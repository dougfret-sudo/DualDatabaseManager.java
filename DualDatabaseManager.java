import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DualDatabaseManager {
    public static void main(String[] args) {
        try {
            int recordId = 101;
            String status = "VERIFIED";
            String jsonPayload = "{ \"accuracy\": \"90%\", \"notes\": \"Stable\" }";

            // 1. Structured Ledger (Simulates SQL 'Truth')
            String csvLine = recordId + "," + status + "," + System.currentTimeMillis() + "\n";
            Files.write(Paths.get("ledger_db.csv"), csvLine.getBytes(), 
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Step 1: Structured Ledger Updated (CSV)");

            // 2. Unstructured Store (JSON 'Fuzzy' Payload)
            Files.write(Paths.get("data_" + recordId + ".json"), jsonPayload.getBytes(), 
                        StandardOpenOption.CREATE);
            System.out.println("Step 2: JSON Document Synced for ID: " + recordId);
            
            System.out.println("--- DUAL SYNC COMPLETE ---");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
