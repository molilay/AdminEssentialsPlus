package fx_moli.adminessentialsplus.core.commands.builder;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PositionCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Map<UUID, org.bukkit.Location> pos1 = new HashMap<>();
    private final Map<UUID, org.bukkit.Location> pos2 = new HashMap<>();

    public PositionCommand(AdminEssentialsPlus plugin) {
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

        org.bukkit.Location loc = player.getLocation();

        if (label.equalsIgnoreCase("pos1")) {
            pos1.put(player.getUniqueId(), loc);
            player.sendMessage(ChatColor.GREEN + "Позиция 1 установлена: " +
                    ChatColor.YELLOW + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        } else if (label.equalsIgnoreCase("pos2")) {
            pos2.put(player.getUniqueId(), loc);
            player.sendMessage(ChatColor.GREEN + "Позиция 2 установлена: " +
                    ChatColor.YELLOW + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ());
        }

        return true;
    }
    
    public org.bukkit.Location getPos1(Player player) {
        return pos1.get(player.getUniqueId());
    }
    
    public org.bukkit.Location getPos2(Player player) {
        return pos2.get(player.getUniqueId());
    }
}