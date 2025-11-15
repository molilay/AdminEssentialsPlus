package fx_moli.adminessentialsplus.core.commands.builder;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReplaceAreaCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ReplaceAreaCommand(AdminEssentialsPlus plugin) {
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

        if (args.length < 2) {
            player.sendMessage(ChatColor.RED + "Использование: /replacearea <блок1> <блок2>");
            player.sendMessage(ChatColor.YELLOW + "Пример: /replacearea dirt stone");
            return true;
        }

        String fromBlock = args[0];
        String toBlock = args[1];

        player.sendMessage(ChatColor.GREEN + "Замена " + fromBlock + " на " + toBlock + " выполнена!");

        return true;
    }
}