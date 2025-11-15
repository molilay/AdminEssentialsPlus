package fx_moli.adminessentialsplus.core.managers;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Менеджер кулдаунов команд
 * Предотвращает спам и злоупотребление командами
 */
public class CooldownManager {
    private final Map<String, Map<UUID, Long>> cooldowns = new HashMap<>();
    
    /**
     * Проверить, есть ли кулдаун у игрока
     */
    public boolean hasCooldown(Player player, String command) {
        if (player.hasPermission("adminessentials.bypass.cooldown")) {
            return false;
        }
        
        Map<UUID, Long> commandCooldowns = cooldowns.get(command);
        if (commandCooldowns == null) {
            return false;
        }
        
        Long cooldownEnd = commandCooldowns.get(player.getUniqueId());
        if (cooldownEnd == null) {
            return false;
        }
        
        return System.currentTimeMillis() < cooldownEnd;
    }
    
    /**
     * Получить оставшееся время кулдауна
     */
    public long getRemainingCooldown(Player player, String command) {
        Map<UUID, Long> commandCooldowns = cooldowns.get(command);
        if (commandCooldowns == null) {
            return 0;
        }
        
        Long cooldownEnd = commandCooldowns.get(player.getUniqueId());
        if (cooldownEnd == null) {
            return 0;
        }
        
        long remaining = cooldownEnd - System.currentTimeMillis();
        return Math.max(0, remaining);
    }
    
    /**
     * Установить кулдаун для игрока
     */
    public void setCooldown(Player player, String command, long seconds) {
        cooldowns.computeIfAbsent(command, k -> new HashMap<>())
                .put(player.getUniqueId(), System.currentTimeMillis() + (seconds * 1000));
    }
    
    /**
     * Сбросить кулдаун игрока
     */
    public void resetCooldown(Player player, String command) {
        Map<UUID, Long> commandCooldowns = cooldowns.get(command);
        if (commandCooldowns != null) {
            commandCooldowns.remove(player.getUniqueId());
        }
    }
    
    /**
     * Очистить все кулдауны игрока
     */
    public void clearPlayerCooldowns(Player player) {
        for (Map<UUID, Long> commandCooldowns : cooldowns.values()) {
            commandCooldowns.remove(player.getUniqueId());
        }
    }
    
    /**
     * Очистить все кулдауны
     */
    public void clearAllCooldowns() {
        cooldowns.clear();
    }
    
    /**
     * Очистить устаревшие кулдауны
     */
    public void cleanupExpiredCooldowns() {
        long now = System.currentTimeMillis();
        for (Map<UUID, Long> commandCooldowns : cooldowns.values()) {
            commandCooldowns.entrySet().removeIf(entry -> entry.getValue() < now);
        }
    }
}
