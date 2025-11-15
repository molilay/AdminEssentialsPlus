package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Команда объединения предметов в инвентаре
 */
public class StackCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public StackCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendError(sender, "Только для игроков!");
            return true;
        }
        
        if (!player.hasPermission("adminessentialsplus.stack")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        int stacked = 0;
        ItemStack[] contents = player.getInventory().getContents();
        
        for (int i = 0; i < contents.length; i++) {
            ItemStack item1 = contents[i];
            if (item1 == null || item1.getType() == Material.AIR) continue;
            
            for (int j = i + 1; j < contents.length; j++) {
                ItemStack item2 = contents[j];
                if (item2 == null || item2.getType() == Material.AIR) continue;
                
                // Проверяем, можно ли объединить
                if (item1.isSimilar(item2)) {
                    int maxStack = item1.getMaxStackSize();
                    int amount1 = item1.getAmount();
                    int amount2 = item2.getAmount();
                    
                    if (amount1 < maxStack) {
                        int transfer = Math.min(maxStack - amount1, amount2);
                        item1.setAmount(amount1 + transfer);
                        item2.setAmount(amount2 - transfer);
                        
                        if (item2.getAmount() <= 0) {
                            contents[j] = null;
                        }
                        
                        stacked++;
                    }
                }
            }
        }
        
        player.getInventory().setContents(contents);
        
        if (stacked > 0) {
            MessageUtil.sendSuccess(player, "📦 Объединено стаков: " + stacked);
        } else {
            MessageUtil.sendInfo(player, "📦 Нечего объединять!");
        }
        
        return true;
    }
}
