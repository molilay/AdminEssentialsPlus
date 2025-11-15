package fx_moli.adminessentialsplus.core.commands.social;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public MeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        if (args.length < 1) {
            player.sendMessage("§cИспользование: /me <действие>");
            return true;
        }
        
        String action = String.join(" ", args);
        Bukkit.broadcastMessage("§7* §e" + player.getName() + " §7" + action + " *");
        
        return true;
    }
}
