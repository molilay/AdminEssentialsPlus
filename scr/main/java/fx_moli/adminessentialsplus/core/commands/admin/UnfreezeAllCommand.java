package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UnfreezeAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public UnfreezeAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.unfreezeall")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        int unfrozen = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setWalkSpeed(0.2f);
            player.setFlySpeed(0.1f);
            player.sendTitle("§a§lРАЗМОРОЖЕН", "§7Вы можете двигаться", 10, 40, 10);
            unfrozen++;
        }
        
        sender.sendMessage("§a✔ Разморожено игроков: " + unfrozen);
        Bukkit.broadcastMessage("§a§l[Kekrix] §aВсе игроки разморожены!");
        
        return true;
    }
}
