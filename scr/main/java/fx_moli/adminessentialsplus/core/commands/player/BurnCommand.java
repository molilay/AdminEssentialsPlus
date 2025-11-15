package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BurnCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public BurnCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.burn")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx burn <игрок> [время]");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        int ticks = 100; // 5 секунд по умолчанию
        if (args.length > 1) {
            try {
                ticks = Integer.parseInt(args[1]) * 20; // секунды в тики
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Неверное время!");
                return true;
            }
        }

        target.setFireTicks(ticks);
        sender.sendMessage(ChatColor.GREEN + "Игрок " + target.getName() + " подожжен на " + (ticks/20) + " секунд");
        target.sendMessage(ChatColor.RED + "Вы подожжены!");

        return true;
    }
}

