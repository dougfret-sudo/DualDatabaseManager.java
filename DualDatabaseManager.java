    public void syncSqlLedger(int recordId, String status) {
        // This is the SQL command
        String sql = "INSERT INTO ledger(id, status) VALUES(?,?)";
        
        try {
            // STEP A: "Wake up" the SQLite Driver
            Class.forName("org.sqlite.JDBC");
            
            // STEP B: Connect and Execute
            try (Connection conn = DriverManager.getConnection(SQL_URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                // Create the table if it doesn't exist (First time only)
                conn.createStatement().execute("CREATE TABLE IF NOT EXISTS ledger (id INTEGER, status TEXT)");
                
                pstmt.setInt(1, recordId);
                pstmt.setString(2, status);
                pstmt.executeUpdate();
                System.out.println("SQL Ledger Updated: ID " + recordId);
            }
        } catch (Exception e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
