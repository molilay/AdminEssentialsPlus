package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class BanCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public BanCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.ban")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /ban <игрок> [причина]");
            return true;
        }

        String targetName = args[0];
        String reason = "Не указана";
        if (args.length > 1) {
            reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        }

        Player target = plugin.getServer().getPlayer(targetName);
        UUID targetUUID = null;
        
        if (target != null) {
            targetUUID = target.getUniqueId();
            target.kickPlayer(ChatColor.RED + "Вы были забанены на сервере!\n" + 
                            ChatColor.YELLOW + "Причина: " + ChatColor.WHITE + reason + "\n" +
                            ChatColor.GRAY + "Администратор: " + sender.getName());
        } else {
            // Пытаемся найти оффлайн игрока
            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(targetName);
            if (offlinePlayer.hasPlayedBefore()) {
                targetUUID = offlinePlayer.getUniqueId();
            } else {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }
        }

        // Сохраняем бан в конфиг
        plugin.getConfig().set("bans." + targetUUID.toString() + ".name", targetName);
        plugin.getConfig().set("bans." + targetUUID.toString() + ".reason", reason);
        plugin.getConfig().set("bans." + targetUUID.toString() + ".admin", sender.getName());
        plugin.getConfig().set("bans." + targetUUID.toString() + ".time", System.currentTimeMillis());
        plugin.saveConfig();

        plugin.getServer().broadcastMessage(ChatColor.RED + targetName + " был забанен администратором " + sender.getName());
        sender.sendMessage(ChatColor.GREEN + "Игрок " + targetName + " забанен. Причина: " + reason);

        return true;
    }
}

