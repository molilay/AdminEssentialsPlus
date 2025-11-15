package fx_moli.adminessentialsplus.core.commands.social;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HugCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public HugCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        if (args.length < 1) {
            player.sendMessage("§cИспользование: /hug <игрок>");
            return true;
        }
        
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage("§cИгрок не найден!");
            return true;
        }
        
        if (target.equals(player)) {
            player.sendMessage("§cНельзя обнять самого себя!");
            return true;
        }
        
        // Эффекты
        player.getWorld().spawnParticle(Particle.HEART, player.getLocation().add(0, 2, 0), 10, 0.5, 0.5, 0.5);
        target.getWorld().spawnParticle(Particle.HEART, target.getLocation().add(0, 2, 0), 10, 0.5, 0.5, 0.5);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2.0f);
        target.playSound(target.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.5f, 2.0f);
        
        // Сообщение
        Bukkit.broadcastMessage("§d❤ §e" + player.getName() + " §7обнял(а) §e" + target.getName() + " §d❤");
        
        return true;
    }
}
