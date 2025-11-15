package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Команда радужной брони - меняет цвет брони каждую секунду
 * Создано для Kekrix
 */
public class RainbowCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public RainbowCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.rainbow")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        Player target;
        
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Укажите игрока!");
                return true;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendError(sender, "Игрок не найден!");
                return true;
            }
        }
        
        // Даём кожаную броню
        target.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
        target.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        target.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
        target.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
        
        Player finalTarget = target;
        
        // Анимация радуги
        new BukkitRunnable() {
            int hue = 0;
            int ticks = 0;
            
            @Override
            public void run() {
                if (!finalTarget.isOnline() || ticks++ > 600) { // 30 секунд
                    cancel();
                    return;
                }
                
                // Создаём радужный цвет
                Color color = Color.fromRGB(java.awt.Color.HSBtoRGB(hue / 360f, 1f, 1f));
                
                // Применяем к броне
                applyColor(finalTarget.getInventory().getHelmet(), color);
                applyColor(finalTarget.getInventory().getChestplate(), color);
                applyColor(finalTarget.getInventory().getLeggings(), color);
                applyColor(finalTarget.getInventory().getBoots(), color);
                
                hue = (hue + 10) % 360;
            }
        }.runTaskTimer(plugin, 0, 2);
        
        MessageUtil.sendSuccess(sender, "🌈 Радужная броня активирована для " + target.getName() + "!");
        MessageUtil.sendInfo(target, "🌈 У вас теперь радужная броня! (30 сек)");
        
        return true;
    }
    
    private void applyColor(ItemStack item, Color color) {
        if (item != null && item.getItemMeta() instanceof LeatherArmorMeta meta) {
            meta.setColor(color);
            item.setItemMeta(meta);
        }
    }
}
