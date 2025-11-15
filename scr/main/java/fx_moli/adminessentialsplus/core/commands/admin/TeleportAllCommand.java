package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public TeleportAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.tpall")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        Location location;
        
        if (args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("§cИгрок не найден!");
                return true;
            }
            location = target.getLocation();
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cУкажите игрока!");
                return true;
            }
            location = ((Player) sender).getLocation();
        }
        
        int count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(location);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
            count++;
        }
        
        sender.sendMessage("§a✔ Телепортировано игроков: " + count);
        Bukkit.broadcastMessage("§e§l[Kekrix] §eВсе игроки телепортированы!");
        
        return true;
    }
}
