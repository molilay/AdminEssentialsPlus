package fx_moli.adminessentialsplus.core.commands.teleport;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportPosCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public TeleportPosCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.tppos")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx tppos <x> <y> <z> [yaw] [pitch]");
            return true;
        }

        Player player = (Player) sender;

        try {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);
            float yaw = args.length > 3 ? Float.parseFloat(args[3]) : player.getLocation().getYaw();
            float pitch = args.length > 4 ? Float.parseFloat(args[4]) : player.getLocation().getPitch();

            Location loc = new Location(player.getWorld(), x, y, z, yaw, pitch);
            player.teleport(loc);
            player.sendMessage(ChatColor.GREEN + "Телепортирован на координаты: " + 
                             String.format("%.1f, %.1f, %.1f", x, y, z));

        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Неверные координаты!");
        }

        return true;
    }
}

