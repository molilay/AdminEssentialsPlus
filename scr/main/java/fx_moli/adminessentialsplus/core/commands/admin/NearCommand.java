package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NearCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public NearCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        int radius = args.length >= 1 ? Integer.parseInt(args[0]) : 100;
        
        player.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        player.sendMessage("§6§lИгроки рядом (радиус " + radius + " блоков):");
        player.sendMessage("");
        
        int count = 0;
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.equals(player)) continue;
            if (!target.getWorld().equals(player.getWorld())) continue;
            
            double distance = player.getLocation().distance(target.getLocation());
            if (distance <= radius) {
                player.sendMessage("§e" + target.getName() + " §7- §e" + String.format("%.1f", distance) + " блоков");
                count++;
            }
        }
        
        if (count == 0) {
            player.sendMessage("§7Никого нет рядом");
        }
        
        player.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        return true;
    }
}
