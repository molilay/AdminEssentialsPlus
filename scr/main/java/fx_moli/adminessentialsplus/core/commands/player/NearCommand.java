package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class NearCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public NearCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.near")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        double radius = args.length > 0 ? Double.parseDouble(args[0]) : 50.0;

        List<Player> nearby = plugin.getServer().getOnlinePlayers().stream()
                .filter(p -> p.getWorld().equals(player.getWorld()))
                .filter(p -> p.getLocation().distance(player.getLocation()) <= radius)
                .filter(p -> !p.equals(player))
                .collect(Collectors.toList());

        if (nearby.isEmpty()) {
            player.sendMessage(ChatColor.YELLOW + "Рядом нет игроков в радиусе " + radius + " блоков");
            return true;
        }

        player.sendMessage(ChatColor.GOLD + "=== Игроки рядом (" + radius + " блоков) ===");
        for (Player p : nearby) {
            double distance = p.getLocation().distance(player.getLocation());
            player.sendMessage(ChatColor.YELLOW + "- " + p.getName() + ChatColor.GRAY + " (" + 
                             String.format("%.1f", distance) + " блоков)");
        }

        return true;
    }
}

