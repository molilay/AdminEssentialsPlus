package fx_moli.adminessentialsplus.core.commands.justice;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LookupCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public LookupCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.lookup")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Использование: /lookup <игрок>");
            return true;
        }

        String playerName = args[0];
        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (player == null || !player.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sender.sendMessage(ChatColor.GOLD + "=== Информация об игроке: " + player.getName() + " ===");
        sender.sendMessage(ChatColor.YELLOW + "UUID: " + ChatColor.WHITE + player.getUniqueId());
        sender.sendMessage(ChatColor.YELLOW + "Первый вход: " + ChatColor.WHITE + sdf.format(new Date(player.getFirstPlayed())));

        if (player.getLastPlayed() > 0) {
            sender.sendMessage(ChatColor.YELLOW + "Последний вход: " + ChatColor.WHITE + sdf.format(new Date(player.getLastPlayed())));
        }

        sender.sendMessage(ChatColor.YELLOW + "Онлайн: " + ChatColor.WHITE + (player.isOnline() ? "Да" : "Нет"));
        sender.sendMessage(ChatColor.YELLOW + "Забанен: " + ChatColor.WHITE + (player.isBanned() ? "Да" : "Нет"));

        return true;
    }
}