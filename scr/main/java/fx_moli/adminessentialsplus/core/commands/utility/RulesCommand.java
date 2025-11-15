package fx_moli.adminessentialsplus.core.commands.utility;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RulesCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public RulesCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("§6§l  Правила сервера Kekrix");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("");
        sender.sendMessage("§e1. §7Запрещено использование читов");
        sender.sendMessage("§e2. §7Уважайте других игроков");
        sender.sendMessage("§e3. §7Не спамьте в чате");
        sender.sendMessage("§e4. §7Не гриферьте чужие постройки");
        sender.sendMessage("§e5. §7Следуйте указаниям администрации");
        sender.sendMessage("");
        sender.sendMessage("§7Полные правила: §b#1071225109735538728");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        return true;
    }
}
