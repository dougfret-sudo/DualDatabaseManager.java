import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DualDatabaseManager {
    public static void main(String[] args) {
        try {
            // 1. Structured Store (Simulated SQL)
            String csvData = "101,VERIFIED," + System.currentTimeMillis() + "\n";
            Files.write(Paths.get("ledger.csv"), csvData.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Step 1: Structured Ledger Updated (CSV)");

            // 2. Unstructured Store (JSON Payload)
            String json = "{ \"accuracy\": \"90%\", \"status\": \"Stable\" }";
            Files.write(Paths.get("data_101.json"), json.getBytes(), StandardOpenOption.CREATE);
            System.out.println("Step 2: JSON Document Synced for ID: 101");
            
            System.out.println("--- DUAL SYNC COMPLETE ---");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
