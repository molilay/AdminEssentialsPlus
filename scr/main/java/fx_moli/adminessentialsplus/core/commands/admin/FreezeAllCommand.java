package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FreezeAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public FreezeAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.freezeall")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        int frozen = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("adminessentialsplus.freeze.bypass")) {
                player.setWalkSpeed(0);
                player.setFlySpeed(0);
                player.sendTitle("§b§lЗАМОРОЖЕН", "§7Вы не можете двигаться", 10, 100, 10);
                frozen++;
            }
        }
        
        sender.sendMessage("§a✔ Заморожено игроков: " + frozen);
        Bukkit.broadcastMessage("§b§l[Kekrix] §bВсе игроки заморожены!");
        
        return true;
    }
}
