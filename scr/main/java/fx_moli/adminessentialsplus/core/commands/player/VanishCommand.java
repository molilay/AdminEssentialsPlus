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

public class VanishCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Set<UUID> vanishedPlayers = new HashSet<>();

    public VanishCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.vanish")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        boolean isVanished = vanishedPlayers.contains(player.getUniqueId());

        if (isVanished) {
            vanishedPlayers.remove(player.getUniqueId());
            player.setInvisible(false);
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                online.showPlayer(plugin, player);
            }
            player.sendMessage(ChatColor.GREEN + "Вы больше не невидимы");
        } else {
            vanishedPlayers.add(player.getUniqueId());
            player.setInvisible(true);
            for (Player online : plugin.getServer().getOnlinePlayers()) {
                if (!online.hasPermission("adminessentialsplus.vanish.see")) {
                    online.hidePlayer(plugin, player);
                }
            }
            player.sendMessage(ChatColor.GREEN + "Вы стали невидимы");
        }

        return true;
    }

    public boolean isVanished(Player player) {
        return vanishedPlayers.contains(player.getUniqueId());
    }
}

