package fx_moli.adminessentialsplus.core.managers;

import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Логирование действий администраторов
 * Записывает все важные действия для аудита
 */
public class ActionLogger {
    private final Path logFile;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public ActionLogger(Path dataFolder) {
        this.logFile = dataFolder.resolve("actions.log");
        try {
            if (!Files.exists(logFile)) {
                Files.createFile(logFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Записать действие в лог
     */
    public void log(CommandSender sender, String action, String details) {
        String timestamp = LocalDateTime.now().format(formatter);
        String senderName = sender instanceof Player ? sender.getName() : "CONSOLE";
        String logEntry = String.format("[%s] %s: %s - %s%n", timestamp, senderName, action, details);
        
        try {
            Files.writeString(logFile, logEntry, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Логировать команду
     */
    public void logCommand(CommandSender sender, String command, String[] args) {
        String fullCommand = command + " " + String.join(" ", args);
        log(sender, "COMMAND", fullCommand);
    }
    
    /**
     * Логировать наказание
     */
    public void logPunishment(CommandSender sender, String target, String type, String reason) {
        log(sender, "PUNISHMENT", String.format("%s -> %s (%s)", target, type, reason));
    }
    
    /**
     * Логировать телепортацию
     */
    public void logTeleport(CommandSender sender, String target, String location) {
        log(sender, "TELEPORT", String.format("%s -> %s", target, location));
    }
    
    /**
     * Логировать изменение игрового режима
     */
    public void logGamemode(CommandSender sender, String target, String gamemode) {
        log(sender, "GAMEMODE", String.format("%s -> %s", target, gamemode));
    }
    
    /**
     * Логировать выдачу предметов
     */
    public void logItemGive(CommandSender sender, String target, String item, int amount) {
        log(sender, "ITEM_GIVE", String.format("%s -> %s x%d", target, item, amount));
    }
    
    /**
     * Получить последние записи лога
     */
    public List<String> getRecentLogs(int count) {
        List<String> logs = new ArrayList<>();
        try {
            List<String> allLines = Files.readAllLines(logFile);
            int start = Math.max(0, allLines.size() - count);
            logs = allLines.subList(start, allLines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logs;
    }
    
    /**
     * Очистить старые логи
     */
    public void clearOldLogs(int daysToKeep) {
        // Реализация очистки старых логов
        // Можно добавить ротацию логов по дате
    }
}
