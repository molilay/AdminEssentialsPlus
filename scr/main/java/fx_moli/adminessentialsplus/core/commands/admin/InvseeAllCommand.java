package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class InvseeAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public InvseeAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        if (!player.hasPermission("adminessentialsplus.invseeall")) {
            player.sendMessage("§cНет прав!");
            return true;
        }
        
        Inventory inv = Bukkit.createInventory(null, 54, "§6§lИнвентари игроков");
        
        int slot = 0;
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (slot >= 54) break;
            
            ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta meta = (SkullMeta) skull.getItemMeta();
            if (meta != null) {
                meta.setOwningPlayer(target);
                meta.setDisplayName("§6" + target.getName());
                meta.setLore(java.util.Arrays.asList(
                    "§7HP: §c" + String.format("%.1f", target.getHealth()),
                    "§7Голод: §e" + target.getFoodLevel(),
                    "§7Уровень: §a" + target.getLevel(),
                    "",
                    "§aКлик = Открыть инвентарь"
                ));
                skull.setItemMeta(meta);
            }
            
            inv.setItem(slot++, skull);
        }
        
        player.openInventory(inv);
        player.sendMessage("§a✔ Открыт список инвентарей");
        
        return true;
    }
}
