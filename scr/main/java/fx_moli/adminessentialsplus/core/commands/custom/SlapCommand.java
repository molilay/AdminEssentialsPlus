package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

/**
 * Команда шлепка - подбрасывает игрока с эффектами
 * Создано для Kekrix
 */
public class SlapCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Random random = new Random();
    
    public SlapCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.slap")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        if (args.length < 1) {
            MessageUtil.sendError(sender, "Использование: /slap <игрок> [сила]");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MessageUtil.sendError(sender, "Игрок не найден!");
            return true;
        }
        
        double power = args.length >= 2 ? Double.parseDouble(args[1]) : 1.0;
        power = Math.min(power, 5.0); // Максимум 5
        
        // Случайное направление
        Vector direction = new Vector(
            random.nextDouble() * 2 - 1,
            power,
            random.nextDouble() * 2 - 1
        ).normalize().multiply(power);
        
        target.setVelocity(direction);
        
        // Эффекты
        Location loc = target.getLocation();
        target.getWorld().spawnParticle(Particle.CRIT, loc, 20, 0.5, 0.5, 0.5, 0.1);
        target.getWorld().playSound(loc, Sound.ENTITY_PLAYER_ATTACK_CRIT, 1.0f, 0.5f);
        
        // Сообщения
        String senderName = sender instanceof Player ? sender.getName() : "Консоль";
        MessageUtil.sendSuccess(sender, "👋 Вы шлёпнули " + target.getName() + "!");
        target.sendMessage("§c§l👋 ШЛЁП! §r§7Вас шлёпнул " + senderName);
        
        // Broadcast
        Bukkit.broadcastMessage("§e§l[!] §e" + senderName + " §7шлёпнул §e" + target.getName() + "§7!");
        
        return true;
    }
}
