package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GodCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Set<UUID> godModePlayers = new HashSet<>();

    public GodCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.god")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        Player target;
        if (args.length > 0 && sender.hasPermission("adminessentialsplus.god.others")) {
            target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Укажите игрока!");
            return true;
        }

        boolean isGod = godModePlayers.contains(target.getUniqueId());
        if (isGod) {
            godModePlayers.remove(target.getUniqueId());
            if (target != sender) {
                sender.sendMessage(ChatColor.GREEN + "Режим бога для " + target.getName() + " выключен");
            }
            target.sendMessage(ChatColor.GREEN + "Режим бога выключен");
        } else {
            godModePlayers.add(target.getUniqueId());
            if (target != sender) {
                sender.sendMessage(ChatColor.GREEN + "Режим бога для " + target.getName() + " включен");
            }
            target.sendMessage(ChatColor.GREEN + "Режим бога включен");
        }

        return true;
    }

    public boolean isGodMode(Player player) {
        return godModePlayers.contains(player.getUniqueId());
    }
}

