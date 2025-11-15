package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class JumpCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public JumpCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.jump")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();
        Vector direction = loc.getDirection().normalize();

        // Умножаем направление взгляда на расстояние
        double distance = args.length > 0 ? Double.parseDouble(args[0]) : 5.0;
        if (distance > 50) distance = 50; // Ограничение

        Location targetLoc = loc.clone().add(direction.multiply(distance));
        targetLoc.setY(loc.getY()); // Сохраняем высоту

        player.teleport(targetLoc);
        player.sendMessage(ChatColor.GREEN + "Прыжок на " + distance + " блоков!");

        return true;
    }
}

