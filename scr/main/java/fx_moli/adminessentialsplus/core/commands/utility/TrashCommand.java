package fx_moli.adminessentialsplus.core.commands.utility;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TrashCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public TrashCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        Inventory trash = Bukkit.createInventory(null, 27, "§c§lМусорка");
        player.openInventory(trash);
        player.sendMessage("§7Положите ненужные предметы в мусорку");
        
        return true;
    }
}
