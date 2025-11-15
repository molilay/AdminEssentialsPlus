package fx_moli.adminessentialsplus.core.commands.teleport;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportHereCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public TeleportHereCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.tphere")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /tphere <игрок>");
            return true;
        }

        Player admin = (Player) sender;
        Player target = plugin.getServer().getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        target.teleport(admin.getLocation());
        sender.sendMessage(ChatColor.GREEN + target.getName() + " телепортирован к вам");
        target.sendMessage(ChatColor.GREEN + "Вы телепортированы к " + admin.getName());

        return true;
    }
}

