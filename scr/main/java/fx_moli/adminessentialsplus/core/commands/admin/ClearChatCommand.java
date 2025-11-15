package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ClearChatCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public ClearChatCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.clearchat")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        // Отправляем 100 пустых строк
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage(" ");
        }
        
        String adminName = sender.getName();
        Bukkit.broadcastMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        Bukkit.broadcastMessage("§e§l[Kekrix] §7Чат очищен администратором §e" + adminName);
        Bukkit.broadcastMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        return true;
    }
}
