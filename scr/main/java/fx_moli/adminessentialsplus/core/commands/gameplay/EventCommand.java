package fx_moli.adminessentialsplus.core.commands.gameplay;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EventCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public EventCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.event")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Использование: /event <start|stop> <тип>");
            sender.sendMessage(ChatColor.YELLOW + "Типы: spleef, dropparty, race");
            return true;
        }

        String action = args[0].toLowerCase();

        if (action.equals("start")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Использование: /event start <тип>");
                return true;
            }

            String type = args[1].toLowerCase();
            sender.sendMessage(ChatColor.GREEN + "Запускается ивент: " + type);

        } else if (action.equals("stop")) {
            sender.sendMessage(ChatColor.GREEN + "Ивент остановлен!");

        } else {
            sender.sendMessage(ChatColor.RED + "Использование: /event <start|stop> <тип>");
        }

        return true;
    }
}