package fx_moli.adminessentialsplus.core.commands.builder;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CopyPasteCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public CopyPasteCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("adminessentialsplus.build")) {
            player.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("copy")) {
            player.sendMessage(ChatColor.GREEN + "Область скопирована!");
        } else if (cmd.getName().equalsIgnoreCase("paste")) {
            player.sendMessage(ChatColor.GREEN + "Область вставлена!");
        }

        return true;
    }
}