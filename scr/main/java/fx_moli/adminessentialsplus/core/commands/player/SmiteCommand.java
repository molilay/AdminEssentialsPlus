package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SmiteCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public SmiteCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.smite")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx smite <игрок>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        target.getWorld().strikeLightning(target.getLocation());
        sender.sendMessage(ChatColor.GREEN + "Молния ударила в " + target.getName() + "!");
        target.sendMessage(ChatColor.RED + "В вас ударила молния!");

        return true;
    }
}

