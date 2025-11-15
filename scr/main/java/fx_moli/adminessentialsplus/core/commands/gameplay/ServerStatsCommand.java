package fx_moli.adminessentialsplus.core.commands.gameplay;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ServerStatsCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ServerStatsCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.stats")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        double[] tps = Bukkit.getTPS();
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
        int onlinePlayers = Bukkit.getOnlinePlayers().size();
        int entities = Bukkit.getWorlds().stream().mapToInt(w -> w.getEntities().size()).sum();

        sender.sendMessage(ChatColor.GOLD + "=== Статистика сервера ===");
        sender.sendMessage(ChatColor.YELLOW + "TPS: " + ChatColor.WHITE + String.format("%.2f", tps[0]));
        sender.sendMessage(ChatColor.YELLOW + "Память: " + ChatColor.WHITE + usedMemory + "MB / " + maxMemory + "MB");
        sender.sendMessage(ChatColor.YELLOW + "Игроков онлайн: " + ChatColor.WHITE + onlinePlayers);
        sender.sendMessage(ChatColor.YELLOW + "Сущностей: " + ChatColor.WHITE + entities);
        sender.sendMessage(ChatColor.YELLOW + "Версия: " + ChatColor.WHITE + "Kekrix Edition 1.0.0");

        return true;
    }
}