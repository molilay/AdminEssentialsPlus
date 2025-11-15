package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Команда следа из частиц за игроком
 */
public class TrailCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Map<UUID, BukkitRunnable> activeTrails = new HashMap<>();
    
    public TrailCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.trail")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        if (args.length < 1) {
            MessageUtil.sendError(sender, "Использование: /trail <тип> [игрок]");
            sender.sendMessage("§7Типы: §eflame, heart, note, portal, magic, cloud, off");
            return true;
        }
        
        Player target;
        
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessageUtil.sendError(sender, "Игрок не найден!");
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Укажите игрока!");
                return true;
            }
            target = (Player) sender;
        }
        
        // Выключить след
        if (args[0].equalsIgnoreCase("off")) {
            BukkitRunnable task = activeTrails.remove(target.getUniqueId());
            if (task != null) {
                task.cancel();
                MessageUtil.sendSuccess(sender, "✨ След отключён для " + target.getName());
                MessageUtil.sendInfo(target, "✨ Ваш след отключён");
            } else {
                MessageUtil.sendError(sender, "У игрока нет активного следа!");
            }
            return true;
        }
        
        // Определяем тип частиц
        Particle particle;
        String trailName;
        
        switch (args[0].toLowerCase()) {
            case "flame", "fire" -> {
                particle = Particle.FLAME;
                trailName = "огненный";
            }
            case "heart", "love" -> {
                particle = Particle.HEART;
                trailName = "сердечки";
            }
            case "note", "music" -> {
                particle = Particle.NOTE;
                trailName = "ноты";
            }
            case "portal", "ender" -> {
                particle = Particle.PORTAL;
                trailName = "портал";
            }
            case "magic", "enchant" -> {
                particle = Particle.ENCHANT;
                trailName = "магический";
            }
            case "cloud", "smoke" -> {
                particle = Particle.CLOUD;
                trailName = "облака";
            }
            default -> {
                MessageUtil.sendError(sender, "Неверный тип! Используйте: flame, heart, note, portal, magic, cloud");
                return true;
            }
        }
        
        // Отключаем старый след если есть
        BukkitRunnable oldTask = activeTrails.remove(target.getUniqueId());
        if (oldTask != null) {
            oldTask.cancel();
        }
        
        // Создаём новый след
        Player finalTarget = target;
        BukkitRunnable trailTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (!finalTarget.isOnline()) {
                    activeTrails.remove(finalTarget.getUniqueId());
                    cancel();
                    return;
                }
                
                Location loc = finalTarget.getLocation();
                loc.getWorld().spawnParticle(particle, loc, 3, 0.3, 0.3, 0.3, 0.01);
            }
        };
        
        trailTask.runTaskTimer(plugin, 0, 2);
        activeTrails.put(target.getUniqueId(), trailTask);
        
        MessageUtil.sendSuccess(sender, "✨ След '" + trailName + "' включён для " + target.getName());
        MessageUtil.sendInfo(target, "✨ У вас теперь " + trailName + " след!");
        
        return true;
    }
}
