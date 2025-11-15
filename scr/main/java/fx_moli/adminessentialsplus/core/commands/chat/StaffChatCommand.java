package fx_moli.adminessentialsplus.core.commands.chat;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffChatCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public StaffChatCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.staffchat")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /" + label + " <сообщение>");
            return true;
        }

        String message = String.join(" ", args);
        String formatted = ChatColor.DARK_RED + "[Staff] " + ChatColor.RED + sender.getName() + ": " + ChatColor.WHITE + message;

        // Отправляем всем staff
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("adminessentialsplus.staffchat")) {
                player.sendMessage(formatted);
            }
        }

        // И в консоль
        Bukkit.getConsoleSender().sendMessage(formatted);

        return true;
    }
}