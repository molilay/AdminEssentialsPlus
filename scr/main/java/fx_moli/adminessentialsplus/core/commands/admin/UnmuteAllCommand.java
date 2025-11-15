package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnmuteAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public UnmuteAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.unmuteall")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        MuteAllCommand.unmuteAll();
        
        sender.sendMessage("§a✔ Все игроки размучены!");
        Bukkit.broadcastMessage("§a§l[Kekrix] §aЧат включён!");
        
        return true;
    }
}
