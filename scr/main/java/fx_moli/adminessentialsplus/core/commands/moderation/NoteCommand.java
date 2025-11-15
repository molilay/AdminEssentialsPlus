package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Команда для добавления заметок о игроках
 */
public class NoteCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public NoteCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
        createTable();
    }

    private void createTable() {
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate(
                "CREATE TABLE IF NOT EXISTS player_notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "player_uuid TEXT NOT NULL," +
                "player_name TEXT NOT NULL," +
                "admin_uuid TEXT NOT NULL," +
                "admin_name TEXT NOT NULL," +
                "note TEXT NOT NULL," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")"
            );
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка создания таблицы заметок: " + e.getMessage());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.note")) {
            sender.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(MessageUtil.error("Использование: /fx note <игрок> <add|list|clear> [текст]"));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        String action = args[1].toLowerCase();

        switch (action) {
            case "add":
                if (args.length < 3) {
                    sender.sendMessage(MessageUtil.error("Использование: /fx note <игрок> add <текст>"));
                    return true;
                }
                String note = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
                addNote(sender, target, note);
                sender.sendMessage(MessageUtil.success("Заметка добавлена для " + target.getName()));
                break;

            case "list":
                listNotes(sender, target);
                break;

            case "clear":
                clearNotes(sender, target);
                sender.sendMessage(MessageUtil.success("Заметки очищены для " + target.getName()));
                break;

            default:
                sender.sendMessage(MessageUtil.error("Неизвестное действие! Используйте: add, list, clear"));
                break;
        }

        return true;
    }

    private void addNote(CommandSender sender, OfflinePlayer target, String note) {
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO player_notes (player_uuid, player_name, admin_uuid, admin_name, note) VALUES (?, ?, ?, ?, ?)"
             )) {
            
            String adminUUID = sender instanceof Player ? ((Player) sender).getUniqueId().toString() : "CONSOLE";
            String adminName = sender instanceof Player ? ((Player) sender).getName() : "Консоль";
            
            stmt.setString(1, target.getUniqueId().toString());
            stmt.setString(2, target.getName());
            stmt.setString(3, adminUUID);
            stmt.setString(4, adminName);
            stmt.setString(5, note);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка добавления заметки: " + e.getMessage());
        }
    }

    private void listNotes(CommandSender sender, OfflinePlayer target) {
        List<String> notes = new ArrayList<>();
        
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT admin_name, note, timestamp FROM player_notes WHERE player_uuid = ? ORDER BY timestamp DESC"
             )) {
            
            stmt.setString(1, target.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String admin = rs.getString("admin_name");
                String note = rs.getString("note");
                String timestamp = rs.getString("timestamp");
                notes.add("§e" + timestamp + " §7- §6" + admin + " §7- §f" + note);
            }
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка получения заметок: " + e.getMessage());
        }

        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
        sender.sendMessage(MessageUtil.format("&6&lЗаметки о игроке: &e" + target.getName()));
        sender.sendMessage("");
        
        if (notes.isEmpty()) {
            sender.sendMessage(MessageUtil.format("&7Заметок нет"));
        } else {
            for (String note : notes) {
                sender.sendMessage(note);
            }
        }
        
        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
    }

    private void clearNotes(CommandSender sender, OfflinePlayer target) {
        try (Connection conn = plugin.getDatabaseManager().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "DELETE FROM player_notes WHERE player_uuid = ?"
             )) {
            
            stmt.setString(1, target.getUniqueId().toString());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            plugin.getLogger().severe("Ошибка очистки заметок: " + e.getMessage());
        }
    }
}
