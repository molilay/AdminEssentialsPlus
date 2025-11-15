package fx_moli.adminessentialsplus.core.commands.teleport;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public TeleportCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.tp")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /tp <игрок> или /tp <x> <y> <z> или /tp <игрок1> <игрок2>");
            return true;
        }

        if (args.length == 1) {
            // /tp <игрок>
            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }
            player.teleport(target.getLocation());
            player.sendMessage(ChatColor.GREEN + "Телепортирован к " + target.getName());
            return true;
        }

        if (args.length == 2 && sender.hasPermission("adminessentialsplus.tp.others")) {
            // /tp <игрок1> <игрок2>
            Player target1 = plugin.getServer().getPlayer(args[0]);
            Player target2 = plugin.getServer().getPlayer(args[1]);
            if (target1 == null || target2 == null) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }
            target1.teleport(target2.getLocation());
            sender.sendMessage(ChatColor.GREEN + target1.getName() + " телепортирован к " + target2.getName());
            target1.sendMessage(ChatColor.GREEN + "Вы телепортированы к " + target2.getName());
            return true;
        }

        if (args.length >= 3) {
            // /tp <x> <y> <z>
            try {
                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);
                Location loc = new Location(player.getWorld(), x, y, z);
                player.teleport(loc);
                player.sendMessage(ChatColor.GREEN + "Телепортирован на координаты: " + x + ", " + y + ", " + z);
                return true;
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Неверные координаты!");
                return true;
            }
        }

        return true;
    }
}

