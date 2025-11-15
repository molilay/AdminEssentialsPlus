package fx_moli.adminessentialsplus.core.commands.utility;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class RepairCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public RepairCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        if (!player.hasPermission("adminessentialsplus.repair")) {
            player.sendMessage("§cНет прав!");
            return true;
        }
        
        ItemStack item = player.getInventory().getItemInMainHand();
        
        if (item.getType() == Material.AIR) {
            player.sendMessage("§cВозьмите предмет в руку!");
            return true;
        }
        
        if (item.getItemMeta() instanceof Damageable damageable) {
            if (damageable.getDamage() == 0) {
                player.sendMessage("§cПредмет не поврежден!");
                return true;
            }
            
            damageable.setDamage(0);
            item.setItemMeta(damageable);
            
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
            player.sendMessage("§a✔ Предмет починен!");
        } else {
            player.sendMessage("§cЭтот предмет нельзя починить!");
        }
        
        return true;
    }
}
