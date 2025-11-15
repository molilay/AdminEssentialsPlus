package fx_moli.adminessentialsplus.core.commands.social;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HighFiveCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public HighFiveCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        if (args.length < 1) {
            player.sendMessage("§cИспользование: /highfive <игрок>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cИгрок не найден!");
            return true;
        }
        
        // Эффекты
        player.getWorld().spawnParticle(Particle.FIREWORK, player.getLocation().add(0, 1.5, 0), 5);
        target.getWorld().spawnParticle(Particle.FIREWORK, target.getLocation().add(0, 1.5, 0), 5);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.5f);
        target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_ATTACK_STRONG, 1.0f, 1.5f);
        
        // Сообщение
        Bukkit.broadcastMessage("§6✋ §e" + player.getName() + " §7дал(а) пятюню §e" + target.getName() + "§7! §6✋");
        
        return true;
    }
}
