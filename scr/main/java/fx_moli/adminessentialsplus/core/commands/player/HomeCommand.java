package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HomeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public HomeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.home")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        String homeName = args.length > 0 ? args[0] : "default";

        Location home = getHome(player, homeName);
        if (home == null) {
            if (homeName.equals("default")) {
                player.sendMessage(ChatColor.RED + "У вас нет дома! Используйте /fx sethome");
            } else {
                player.sendMessage(ChatColor.RED + "Дом '" + homeName + "' не найден!");
            }
            return true;
        }

        player.teleport(home);
        player.sendMessage(ChatColor.GREEN + "Телепортирован домой" + (homeName.equals("default") ? "" : " (" + homeName + ")"));

        return true;
    }

    private Location getHome(Player player, String homeName) {
        FileConfiguration config = plugin.getConfig();
        String path = "homes." + player.getUniqueId() + "." + homeName;
        
        if (!config.contains(path + ".world")) {
            return null;
        }

        String worldName = config.getString(path + ".world");
        double x = config.getDouble(path + ".x");
        double y = config.getDouble(path + ".y");
        double z = config.getDouble(path + ".z");
        float yaw = (float) config.getDouble(path + ".yaw", 0);
        float pitch = (float) config.getDouble(path + ".pitch", 0);

        org.bukkit.World world = plugin.getServer().getWorld(worldName);
        if (world != null) {
            return new Location(world, x, y, z, yaw, pitch);
        }
        return null;
    }
}

