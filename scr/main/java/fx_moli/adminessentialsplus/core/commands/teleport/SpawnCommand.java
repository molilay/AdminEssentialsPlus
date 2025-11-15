package fx_moli.adminessentialsplus.core.commands.teleport;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public SpawnCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.spawn")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        Location spawn = getSpawnLocation();

        if (spawn == null) {
            spawn = player.getWorld().getSpawnLocation();
        }

        player.teleport(spawn);
        player.sendMessage(ChatColor.GREEN + "Телепортирован на спавн");

        return true;
    }

    private Location getSpawnLocation() {
        FileConfiguration config = plugin.getConfig();
        if (config.contains("spawn.world") && config.contains("spawn.x") && 
            config.contains("spawn.y") && config.contains("spawn.z")) {
            String worldName = config.getString("spawn.world");
            double x = config.getDouble("spawn.x");
            double y = config.getDouble("spawn.y");
            double z = config.getDouble("spawn.z");
            float yaw = (float) config.getDouble("spawn.yaw", 0);
            float pitch = (float) config.getDouble("spawn.pitch", 0);

            org.bukkit.World world = plugin.getServer().getWorld(worldName);
            if (world != null) {
                return new Location(world, x, y, z, yaw, pitch);
            }
        }
        return null;
    }
}

