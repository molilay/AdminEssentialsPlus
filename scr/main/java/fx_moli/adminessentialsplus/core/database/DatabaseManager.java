package fx_moli.adminessentialsplus.core.database;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;

import java.sql.*;
import java.util.logging.Level;

public class DatabaseManager {

    private final AdminEssentialsPlus plugin;
    private Connection connection;

    public DatabaseManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    public void connect() {
        try {
            // Using SQLite for simplicity
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + "/data.db");

            createTables();
            plugin.getLogger().info("Database connected successfully");

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to database", e);
        }
    }

    private void createTables() {
        if (connection == null) {
            plugin.getLogger().warning("Database connection is null, cannot create tables");
            return;
        }
        
        try (Statement stmt = connection.createStatement()) {
            // Player data table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS player_data (" +
                            "uuid TEXT PRIMARY KEY," +
                            "username TEXT," +
                            "last_join DATETIME," +
                            "play_time INTEGER," +
                            "inventory_data TEXT" +
                            ")"
            );

            // Ban records table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS ban_records (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "player_uuid TEXT," +
                            "admin_uuid TEXT," +
                            "reason TEXT," +
                            "banned_at DATETIME," +
                            "expires_at DATETIME" +
                            ")"
            );

            // Chat logs table
            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS chat_logs (" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "player_uuid TEXT," +
                            "message TEXT," +
                            "timestamp DATETIME" +
                            ")"
            );

        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not create database tables", e);
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Error closing database connection", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}