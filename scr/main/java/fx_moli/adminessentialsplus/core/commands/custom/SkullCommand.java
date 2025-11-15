package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Команда получения головы игрока
 * Создано для Kekrix
 */
public class SkullCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public SkullCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            MessageUtil.sendError(sender, "Только для игроков!");
            return true;
        }
        
        if (!player.hasPermission("adminessentialsplus.skull")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        if (args.length < 1) {
            MessageUtil.sendError(sender, "Использование: /skull <игрок>");
            return true;
        }
        
        String targetName = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
        
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        
        if (meta != null) {
            meta.setOwningPlayer(target);
            meta.setDisplayName("§6Голова " + targetName);
            skull.setItemMeta(meta);
        }
        
        player.getInventory().addItem(skull);
        MessageUtil.sendSuccess(player, "💀 Вы получили голову " + targetName + "!");
        
        return true;
    }
}
