package fx_moli.adminessentialsplus.core.commands.builder;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.TreeType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ForestGenCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ForestGenCommand(AdminEssentialsPlus plugin) {
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

        int radius = 10;
        TreeType treeType = TreeType.TREE;

        if (args.length > 0) {
            try {
                radius = Integer.parseInt(args[0]);
                radius = Math.min(radius, 50);
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Неверный радиус. Использую 10.");
            }
        }

        if (args.length > 1) {
            treeType = getTreeType(args[1]);
        }

        player.sendMessage(ChatColor.GREEN + "Лес сгенерирован! Радиус: " + radius + ", Тип: " + treeType);

        return true;
    }

    private TreeType getTreeType(String type) {
        switch (type.toLowerCase()) {
            case "oak": return TreeType.TREE;
            case "birch": return TreeType.BIRCH;
            case "spruce": return TreeType.REDWOOD;
            case "jungle": return TreeType.JUNGLE;
            case "acacia": return TreeType.ACACIA;
            case "dark_oak": return TreeType.DARK_OAK;
            default: return TreeType.TREE;
        }
    }
}