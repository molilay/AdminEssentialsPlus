package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

/**
 * Команда массового исцеления всех игроков
 */
public class HealAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public HealAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.healall")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        int healed = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setHealth(20.0);
            player.setFoodLevel(20);
            player.setSaturation(20.0f);
            player.setFireTicks(0);
            
            // Убираем негативные эффекты
            for (PotionEffect effect : player.getActivePotionEffects()) {
                player.removePotionEffect(effect.getType());
            }
            
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
            MessageUtil.sendSuccess(player, "💚 Вы были исцелены!");
            healed++;
        }
        
        MessageUtil.sendSuccess(sender, "✅ Исцелено игроков: " + healed);
        Bukkit.broadcastMessage("§a§l[!] §aВсе игроки были исцелены!");
        
        return true;
    }
}
