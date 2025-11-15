package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

public class KillAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public KillAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.killall")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        if (args.length < 1) {
            sender.sendMessage("§cИспользование: /killall <mobs/animals/all>");
            return true;
        }
        
        String type = args[0].toLowerCase();
        int killed = 0;
        
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                boolean shouldKill = false;
                
                switch (type) {
                    case "mobs":
                        shouldKill = entity instanceof Monster;
                        break;
                    case "animals":
                        shouldKill = entity instanceof Animals;
                        break;
                    case "all":
                        shouldKill = entity instanceof LivingEntity && !(entity instanceof Player);
                        break;
                }
                
                if (shouldKill) {
                    entity.remove();
                    killed++;
                }
            }
        }
        
        sender.sendMessage("§a✔ Убито сущностей: " + killed);
        Bukkit.broadcastMessage("§c§l[Kekrix] §cУдалено " + killed + " сущностей типа: " + type);
        
        return true;
    }
}
