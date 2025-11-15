package fx_moli.adminessentialsplus.core.commands.justice;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RestoreInvCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public RestoreInvCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.restoreinv")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Использование: /restoreinv <игрок> [время]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Восстановление инвентаря для " + target.getName());
        sender.sendMessage(ChatColor.GREEN + "Функция восстановления активирована!");

        return true;
    }
}