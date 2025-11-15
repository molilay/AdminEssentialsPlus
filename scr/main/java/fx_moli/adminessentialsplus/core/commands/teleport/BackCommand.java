package fx_moli.adminessentialsplus.core.commands.teleport;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BackCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Map<UUID, Location> lastLocations = new HashMap<>();

    public BackCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.back")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        Location lastLoc = lastLocations.get(player.getUniqueId());

        if (lastLoc == null) {
            player.sendMessage(ChatColor.RED + "Нет сохраненной позиции!");
            return true;
        }

        player.teleport(lastLoc);
        player.sendMessage(ChatColor.GREEN + "Возврат на предыдущую позицию");

        return true;
    }

    public void saveLocation(Player player) {
        lastLocations.put(player.getUniqueId(), player.getLocation());
    }
}

