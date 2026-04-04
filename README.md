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

## 🚦 How to Run
1. Ensure you have the **SQLite JDBC Driver** in your classpath.
2. Compile the Manager:
   ```bash
   javac DualDatabaseManager.java
