package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClearCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ClearCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.clear")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        Player target;
        if (args.length > 0 && sender.hasPermission("adminessentialsplus.clear.others")) {
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

        target.getInventory().clear();
        target.getInventory().setArmorContents(null);
        target.getInventory().setItemInOffHand(null);

        if (target != sender) {
            sender.sendMessage(ChatColor.GREEN + "Инвентарь игрока " + target.getName() + " очищен!");
        }
        target.sendMessage(ChatColor.GREEN + "Ваш инвентарь очищен!");

        return true;
    }
}

