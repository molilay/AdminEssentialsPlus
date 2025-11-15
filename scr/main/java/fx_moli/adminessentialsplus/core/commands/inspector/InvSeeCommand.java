package fx_moli.adminessentialsplus.core.commands.inspector;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InvSeeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public InvSeeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.invsee")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /invsee <игрок>");
            return true;
        }

        Player admin = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "Инвентарь: " + target.getName());

        // Копируем инвентарь
        for (int i = 0; i < 36; i++) {
            if (target.getInventory().getItem(i) != null) {
                inv.setItem(i, target.getInventory().getItem(i).clone());
            }
        }

        // Броня
        inv.setItem(45, target.getInventory().getHelmet());
        inv.setItem(46, target.getInventory().getChestplate());
        inv.setItem(47, target.getInventory().getLeggings());
        inv.setItem(48, target.getInventory().getBoots());

        // Вторая рука
        inv.setItem(49, target.getInventory().getItemInOffHand());

        admin.openInventory(inv);
        admin.sendMessage(ChatColor.GREEN + "Просматриваете инвентарь " + target.getName());

        return true;
    }
}

