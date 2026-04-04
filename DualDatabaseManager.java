import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DualDatabaseManager {
    public static void main(String[] args) throws Exception {
        int id = 101;
        String status = "VERIFIED";
        String json = "{ 'accuracy': '90%', 'status': 'Stable' }";

        // 1. Structured Ledger (Simulates SQL)
        String csvLine = id + "," + status + "," + System.currentTimeMillis() + "\n";
        Files.write(Paths.get("ledger.csv"), csvLine.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        System.out.println("Step 1: Structured Ledger Updated (CSV)");

        // 2. Unstructured Store (JSON Payload)
        Files.write(Paths.get("data_" + id + ".json"), json.getBytes(), StandardOpenOption.CREATE);
        System.out.println("Step 2: JSON Document Synced for ID: " + id);
        
        System.out.println("--- DUAL SYNC COMPLETE ---");
    }
}
