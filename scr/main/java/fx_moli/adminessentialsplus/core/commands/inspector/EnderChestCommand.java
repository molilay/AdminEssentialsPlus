package fx_moli.adminessentialsplus.core.commands.inspector;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EnderChestCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public EnderChestCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.enderchest")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player admin = (Player) sender;
        Player target;

        if (args.length > 0 && sender.hasPermission("adminessentialsplus.enderchest.others")) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }
        } else {
            target = admin;
        }

        admin.openInventory(target.getEnderChest());
        if (target != admin) {
            admin.sendMessage(ChatColor.GREEN + "Просматриваете эндер-сундук " + target.getName());
        } else {
            admin.sendMessage(ChatColor.GREEN + "Открыт ваш эндер-сундук");
        }

        return true;
    }
}

