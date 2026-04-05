# Java Dual-Database Sync Engine 🏗️

A high-integrity backend system designed to synchronize **Structured Data (SQL)** and **Unstructured Data (JSON)** simultaneously. This architecture ensures a "Single Source of Truth" while maintaining flexibility for AI and Chat payloads.

## 🛠️ The Dual-Sync Architecture
This system utilizes a **Hybrid Storage** approach:
1. **Relational Ledger (SQLite)**: Stores critical metadata, transaction IDs, and status flags with 100% ACID compliance.
2. **Document Store (JSON)**: Persists large, "fuzzy" AI outputs and conversation logs that require schema flexibility.

## 🚀 Key Features
- **JDBC Integration**: Utilizes Java Database Connectivity for secure SQL operations.
- **Strict Data Typing**: Leverages Java's strong typing to prevent data corruption during synchronization.
- **Thread-Safe Logic**: Designed to handle simultaneous data streams from multiple sources.

## 📁 File Structure
- `DualDatabaseManager.java`: The core logic engine handling the SQL/JSON handshake.
- `ledger.db`: The local SQLite database generated for relational tracking.
- `data_[ID].json`: Individual document files generated for unstructured payloads.

### 🚦 How to Run

1. **Download the Driver:** Download the latest [SQLite JDBC JAR](https://github.com) and place it in the project root.
2. **Compile:**
   ```bash
   javac -cp ".;sqlite-jdbc-3.45.2.0.jar" DualDatabaseManager.java
java -cp ".;sqlite-jdbc-3.45.2.0.jar" DualDatabaseManager

# Windows
javac -cp ".;sqlite-jdbc-3.45.2.0.jar" DualDatabaseManager.java

# macOS/Linux
javac -cp ".:sqlite-jdbc-3.45.2.0.jar" DualDatabaseManager.java
