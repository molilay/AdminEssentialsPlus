package fx_moli.adminessentialsplus.core.commands.justice;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BanItemCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public BanItemCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.banitem")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Использование: /banitem <предмет>");
            sender.sendMessage(ChatColor.YELLOW + "Пример: /banitem tnt");
            return true;
        }

        String itemId = args[0].toLowerCase();
        try {
            Material material = Material.valueOf(itemId.toUpperCase().replace("minecraft:", ""));
            sender.sendMessage(ChatColor.GREEN + "Предмет " + ChatColor.YELLOW + itemId + ChatColor.GREEN + " запрещен!");
        } catch (IllegalArgumentException e) {
            sender.sendMessage(ChatColor.RED + "Неверный предмет: " + itemId);
        }

        return true;
    }
}