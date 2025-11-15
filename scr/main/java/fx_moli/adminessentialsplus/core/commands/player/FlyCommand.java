package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public FlyCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.fly")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        Player target;
        if (args.length > 0 && sender.hasPermission("adminessentialsplus.fly.others")) {
            target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Укажите игрока!");
            return true;
        }

        boolean flyEnabled = target.getAllowFlight();
        target.setAllowFlight(!flyEnabled);
        target.setFlying(!flyEnabled);

        String message = flyEnabled ? "полет выключен" : "полет включен";
        if (target != sender) {
            sender.sendMessage(ChatColor.GREEN + "Полет для " + target.getName() + " " + message);
        }
        target.sendMessage(ChatColor.GREEN + "Полет " + message);

        return true;
    }
}

