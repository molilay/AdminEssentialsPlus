package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

/**
 * Команда очистки лагов - удаляет дропнутые предметы и мобов
 */
public class ClearLagCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public ClearLagCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.clearlag")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        int itemsRemoved = 0;
        int mobsRemoved = 0;
        
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                // Удаляем предметы
                if (entity instanceof Item) {
                    entity.remove();
                    itemsRemoved++;
                }
                // Удаляем стрелы и другие снаряды
                else if (entity instanceof Projectile) {
                    entity.remove();
                    itemsRemoved++;
                }
                // Удаляем враждебных мобов (опционально)
                else if (args.length > 0 && args[0].equalsIgnoreCase("mobs")) {
                    if (entity instanceof Monster) {
                        entity.remove();
                        mobsRemoved++;
                    }
                }
            }
        }
        
        MessageUtil.sendSuccess(sender, "🧹 Очистка завершена!");
        sender.sendMessage("§7Удалено предметов: §e" + itemsRemoved);
        if (mobsRemoved > 0) {
            sender.sendMessage("§7Удалено мобов: §e" + mobsRemoved);
        }
        
        Bukkit.broadcastMessage("§e§l[!] §eСервер очищен от лагов!");
        
        return true;
    }
}
