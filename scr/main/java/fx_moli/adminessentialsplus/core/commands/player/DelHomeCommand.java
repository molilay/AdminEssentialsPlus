package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DelHomeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public DelHomeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.delhome")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx delhome <название>");
            return true;
        }

        Player player = (Player) sender;
        String homeName = args[0];
        String path = "homes." + player.getUniqueId() + "." + homeName;

        if (!plugin.getConfig().contains(path)) {
            player.sendMessage(ChatColor.RED + "Дом '" + homeName + "' не найден!");
            return true;
        }

        plugin.getConfig().set(path, null);
        plugin.saveConfig();
        player.sendMessage(ChatColor.GREEN + "Дом '" + homeName + "' удален!");

        return true;
    }
}

