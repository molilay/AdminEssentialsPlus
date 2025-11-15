package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TopCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public TopCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.top")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        Location loc = player.getLocation();
        org.bukkit.World world = loc.getWorld();

        if (world == null) {
            return true;
        }

        // Ищем верхний блок
        int highestY = world.getHighestBlockYAt(loc.getBlockX(), loc.getBlockZ());
        Block highestBlock = world.getBlockAt(loc.getBlockX(), highestY, loc.getBlockZ());

        // Если блок не проходимый, ставим игрока на него, иначе на один блок выше
        if (highestBlock.getType() != Material.AIR && highestBlock.getType().isSolid()) {
            loc.setY(highestY + 1);
        } else {
            loc.setY(highestY);
        }

        player.teleport(loc);
        player.sendMessage(ChatColor.GREEN + "Телепортирован наверх!");

        return true;
    }
}

