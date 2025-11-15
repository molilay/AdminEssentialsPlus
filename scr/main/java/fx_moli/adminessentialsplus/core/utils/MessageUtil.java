package fx_moli.adminessentialsplus.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

public class MessageUtil {
    
    private static final String PREFIX = "§8[§6AEP§8] §r";
    private static final String ERROR_PREFIX = "§8[§c✗§8] §r";
    private static final String SUCCESS_PREFIX = "§8[§a✓§8] §r";
    private static final String INFO_PREFIX = "§8[§b!§8] §r";
    private static final String WARN_PREFIX = "§8[§e⚠§8] §r";
    
    // Красивые сообщения
    public static void sendSuccess(CommandSender sender, String message) {
        sender.sendMessage(SUCCESS_PREFIX + "§a" + message);
    }
    
    public static void sendError(CommandSender sender, String message) {
        sender.sendMessage(ERROR_PREFIX + "§c" + message);
    }
    
    public static void sendInfo(CommandSender sender, String message) {
        sender.sendMessage(INFO_PREFIX + "§7" + message);
    }
    
    public static void sendWarning(CommandSender sender, String message) {
        sender.sendMessage(WARN_PREFIX + "§e" + message);
    }
    
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(PREFIX + "§7" + message);
    }
    
    // Красивые заголовки
    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(
            "§6§l" + title,
            "§e" + subtitle,
            10, 70, 20
        );
    }
    
    public static void sendActionBar(Player player, String message) {
        player.sendActionBar(Component.text(message));
    }
    
    // Красивые разделители
    public static void sendHeader(CommandSender sender, String title) {
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("§6§l" + title);
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    public static void sendFooter(CommandSender sender) {
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
    
    // Форматирование
    public static String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        
        if (days > 0) return days + "д " + (hours % 24) + "ч";
        if (hours > 0) return hours + "ч " + (minutes % 60) + "м";
        if (minutes > 0) return minutes + "м " + (seconds % 60) + "с";
        return seconds + "с";
    }
    
    public static String formatLocation(org.bukkit.Location loc) {
        return String.format("§e%d§7, §e%d§7, §e%d", 
            loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }
    
    // Цветные сообщения
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    // Дополнительные методы для совместимости
    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    
    public static String error(String message) {
        return ERROR_PREFIX + "§c" + message;
    }
    
    public static String success(String message) {
        return SUCCESS_PREFIX + "§a" + message;
    }
    
    public static String info(String message) {
        return INFO_PREFIX + "§7" + message;
    }
    
    public static String warning(String message) {
        return WARN_PREFIX + "§e" + message;
    }
    
    // Прогресс бар
    public static String progressBar(int current, int max, int length) {
        int filled = (int) ((double) current / max * length);
        StringBuilder bar = new StringBuilder("§8[");
        
        for (int i = 0; i < length; i++) {
            if (i < filled) {
                bar.append("§a█");
            } else {
                bar.append("§7█");
            }
        }
        
        bar.append("§8] §e").append(current).append("§7/§e").append(max);
        return bar.toString();
    }
}
