package fx_moli.adminessentialsplus.core.managers;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;

/**
 * Менеджер системы наказаний
 * Управляет предупреждениями, банами, мутами и их историей
 */
public class PunishmentManager {
    private final AdminEssentialsPlus plugin;
    private final Map<UUID, Integer> warnings = new HashMap<>();
    private final Map<UUID, Long> tempBans = new HashMap<>();
    private final Map<UUID, Long> tempMutes = new HashMap<>();

    public PunishmentManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
        createTables();
        loadWarnings();
    }

    private void createTables() {
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Таблица предупреждений
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS warnings (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player_uuid TEXT NOT NULL," +
                "player_name TEXT NOT NULL," +
                "admin_uuid TEXT NOT NULL," +
                "admin_name TEXT NOT NULL," +
                "reason TEXT NOT NULL," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "active BOOLEAN DEFAULT 1" +
                ")"
            );

            // Таблица истории наказаний
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS punishment_history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player_uuid TEXT NOT NULL," +
                "player_name TEXT NOT NULL," +
                "admin_uuid TEXT NOT NULL," +
                "admin_name TEXT NOT NULL," +
                "type TEXT NOT NULL," +
                "reason TEXT NOT NULL," +
                "duration INTEGER," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

            // Таблица временных банов
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS temp_bans (" +
                "player_uuid TEXT PRIMARY KEY," +
                "player_name TEXT NOT NULL," +
                "admin_uuid TEXT NOT NULL," +
                "admin_name TEXT NOT NULL," +
                "reason TEXT NOT NULL," +
                "expires_at DATETIME NOT NULL," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );

        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка создания таблиц наказаний: " + e.getMessage());
        }
    }

    private void loadWarnings() {
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT player_uuid, COUNT(*) as count FROM warnings WHERE active = 1 GROUP BY player_uuid"
             )) {
            
            while (rs.next()) {
                UUID uuid = UUID.fromString(rs.getString("player_uuid"));
                int count = rs.getInt("count");
                warnings.put(uuid, count);
            }
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка загрузки предупреждений: " + e.getMessage());
        }
    }

    public void addWarning(Player player, Player admin, String reason) {
        UUID playerUUID = player.getUniqueId();
        
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO warnings (player_uuid, player_name, admin_uuid, admin_name, reason) VALUES (?, ?, ?, ?, ?)"
             )) {
            
            stmt.setString(1, playerUUID.toString());
            stmt.setString(2, player.getName());
            stmt.setString(3, admin.getUniqueId().toString());
            stmt.setString(4, admin.getName());
            stmt.setString(5, reason);
            stmt.executeUpdate();
            
            warnings.put(playerUUID, warnings.getOrDefault(playerUUID, 0) + 1);
            
            // Добавляем в историю
            addToHistory(player, admin, "WARN", reason, 0);
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка добавления предупреждения: " + e.getMessage());
        }
    }

    public int getWarnings(UUID playerUUID) {
        return warnings.getOrDefault(playerUUID, 0);
    }

    public void clearWarnings(UUID playerUUID) {
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "UPDATE warnings SET active = 0 WHERE player_uuid = ? AND active = 1"
             )) {
            
            stmt.setString(1, playerUUID.toString());
            stmt.executeUpdate();
            warnings.remove(playerUUID);
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка очистки предупреждений: " + e.getMessage());
        }
    }

    public List<String> getWarningHistory(UUID playerUUID) {
        List<String> history = new ArrayList<>();
        
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT admin_name, reason, timestamp FROM warnings WHERE player_uuid = ? AND active = 1 ORDER BY timestamp DESC"
             )) {
            
            stmt.setString(1, playerUUID.toString());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String admin = rs.getString("admin_name");
                String reason = rs.getString("reason");
                String timestamp = rs.getString("timestamp");
                history.add("§e" + timestamp + " §7- §c" + admin + " §7- §f" + reason);
            }
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка получения истории предупреждений: " + e.getMessage());
        }
        
        return history;
    }

    public void addToHistory(Player player, Player admin, String type, String reason, int duration) {
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO punishment_history (player_uuid, player_name, admin_uuid, admin_name, type, reason, duration) VALUES (?, ?, ?, ?, ?, ?, ?)"
             )) {
            
            stmt.setString(1, player.getUniqueId().toString());
            stmt.setString(2, player.getName());
            stmt.setString(3, admin.getUniqueId().toString());
            stmt.setString(4, admin.getName());
            stmt.setString(5, type);
            stmt.setString(6, reason);
            stmt.setInt(7, duration);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка добавления в историю: " + e.getMessage());
        }
    }

    public List<String> getPunishmentHistory(UUID playerUUID, int limit) {
        List<String> history = new ArrayList<>();
        
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT type, admin_name, reason, duration, timestamp FROM punishment_history WHERE player_uuid = ? ORDER BY timestamp DESC LIMIT ?"
             )) {
            
            stmt.setString(1, playerUUID.toString());
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String type = rs.getString("type");
                String admin = rs.getString("admin_name");
                String reason = rs.getString("reason");
                int duration = rs.getInt("duration");
                String timestamp = rs.getString("timestamp");
                
                String durationStr = duration > 0 ? " (" + duration + " мин)" : "";
                history.add("§e" + timestamp + " §7- §c" + type + durationStr + " §7- §6" + admin + " §7- §f" + reason);
            }
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка получения истории наказаний: " + e.getMessage());
        }
        
        return history;
    }

    public void addTempBan(UUID playerUUID, long expiresAt) {
        tempBans.put(playerUUID, expiresAt);
    }

    public boolean isTempBanned(UUID playerUUID) {
        Long expiresAt = tempBans.get(playerUUID);
        if (expiresAt == null) return false;
        
        if (System.currentTimeMillis() > expiresAt) {
            tempBans.remove(playerUUID);
            return false;
        }
        
        return true;
    }

    public void addTempMute(UUID playerUUID, long expiresAt) {
        tempMutes.put(playerUUID, expiresAt);
    }

    public boolean isTempMuted(UUID playerUUID) {
        Long expiresAt = tempMutes.get(playerUUID);
        if (expiresAt == null) return false;
        
        if (System.currentTimeMillis() > expiresAt) {
            tempMutes.remove(playerUUID);
            return false;
        }
        
        return true;
    }

    public long getTempMuteExpiry(UUID playerUUID) {
        return tempMutes.getOrDefault(playerUUID, 0L);
    }
}
