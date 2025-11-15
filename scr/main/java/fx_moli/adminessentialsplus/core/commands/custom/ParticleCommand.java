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

/**
 * Команда создания красивых частиц
 * Создано для Kekrix
 */
public class ParticleCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public ParticleCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendError(sender, "Только для игроков!");
            return true;
        }
        
        if (!player.hasPermission("adminessentialsplus.particle")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        if (args.length < 1) {
            MessageUtil.sendError(sender, "Использование: /particle <тип> [количество]");
            sender.sendMessage("§7Типы: §efirework, heart, dragon, totem, end, soul");
            return true;
        }
        
        int amount = args.length >= 2 ? Integer.parseInt(args[1]) : 50;
        Location loc = player.getLocation().add(0, 1, 0);
        
        Particle particle;
        String particleName;
        
        switch (args[0].toLowerCase()) {
            case "firework" -> {
                particle = Particle.FIREWORK;
                particleName = "фейерверк";
            }
            case "heart" -> {
                particle = Particle.HEART;
                particleName = "сердечки";
            }
            case "dragon" -> {
                particle = Particle.DRAGON_BREATH;
                particleName = "дыхание дракона";
            }
            case "totem" -> {
                particle = Particle.TOTEM_OF_UNDYING;
                particleName = "тотем";
            }
            case "end" -> {
                particle = Particle.END_ROD;
                particleName = "энд";
            }
            case "soul" -> {
                particle = Particle.SOUL;
                particleName = "души";
            }
            default -> {
                MessageUtil.sendError(sender, "Неверный тип! Используйте: firework, heart, dragon, totem, end, soul");
                return true;
            }
        }
        
        // Создаём красивый эффект
        for (int i = 0; i < amount; i++) {
            double angle = 2 * Math.PI * i / amount;
            double x = Math.cos(angle) * 2;
            double z = Math.sin(angle) * 2;
            Location particleLoc = loc.clone().add(x, 0, z);
            player.getWorld().spawnParticle(particle, particleLoc, 1, 0, 0, 0, 0);
        }
        
        MessageUtil.sendSuccess(player, "✨ Создан эффект: " + particleName + " (" + amount + " частиц)");
        
        return true;
    }
}
