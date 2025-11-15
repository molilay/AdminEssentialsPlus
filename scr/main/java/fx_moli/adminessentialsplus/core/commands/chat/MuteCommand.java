package fx_moli.adminessentialsplus.core.commands.chat;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MuteCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Map<UUID, Long> mutedPlayers = new HashMap<>();

    public MuteCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.mute")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Использование: /mute <игрок> [время] [причина]");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        long muteTime = 300000; // 5 минут по умолчанию
        if (args.length > 1) {
            try {
                muteTime = Long.parseLong(args[1]) * 60000; // Минуты в миллисекунды
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Неверное время. Использую 5 минут.");
            }
        }

        String reason = "Не указана";
        if (args.length > 2) {
            reason = String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length));
        }

        mutedPlayers.put(target.getUniqueId(), System.currentTimeMillis() + muteTime);

        sender.sendMessage(ChatColor.GREEN + "Игрок " + target.getName() + " заглушен на " + (muteTime/60000) + " минут.");
        target.sendMessage(ChatColor.RED + "Вы заглушены на " + (muteTime/60000) + " минут. Причина: " + reason);

        return true;
    }

    public boolean isMuted(Player player) {
        Long muteTime = mutedPlayers.get(player.getUniqueId());
        if (muteTime == null) return false;

        if (System.currentTimeMillis() > muteTime) {
            mutedPlayers.remove(player.getUniqueId());
            return false;
        }

        return true;
    }
}