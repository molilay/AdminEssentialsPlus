package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

/**
 * Команда временного бана игрока
 */
public class TempBanCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public TempBanCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.tempban")) {
            sender.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(MessageUtil.error("Использование: /fx tempban <игрок> <время> [причина]"));
            sender.sendMessage(MessageUtil.format("&7Примеры времени: 10m, 1h, 1d, 7d"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(MessageUtil.error("Игрок не найден!"));
            return true;
        }

        if (target.hasPermission("adminessentialsplus.bypass.ban")) {
            sender.sendMessage(MessageUtil.error("Вы не можете забанить этого игрока!"));
            return true;
        }

        // Парсим время
        long duration = parseTime(args[1]);
        if (duration == -1) {
            sender.sendMessage(MessageUtil.error("Неверный формат времени! Используйте: 10m, 1h, 1d"));
            return true;
        }

        String reason = args.length > 2 ? String.join(" ", java.util.Arrays.copyOfRange(args, 2, args.length)) : "Нарушение правил";

        // Добавляем временный бан
        long expiresAt = System.currentTimeMillis() + duration;
        plugin.getPunishmentManager().addTempBan(target.getUniqueId(), expiresAt);

        // Добавляем в историю
        if (sender instanceof Player) {
            plugin.getPunishmentManager().addToHistory(target, (Player) sender, "TEMPBAN", reason, (int) (duration / 60000));
        }

        // Кикаем игрока
        String timeStr = formatTime(duration);
        target.kick(Component.text(MessageUtil.format(
            "&c&lВы временно забанены!\n\n" +
            "&7Причина: &f" + reason + "\n" +
            "&7Длительность: &e" + timeStr + "\n\n" +
            "&7Бан истекает: &e" + new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm").format(new java.util.Date(expiresAt))
        )));

        // Уведомления
        Bukkit.broadcast(Component.text(MessageUtil.warning(
            target.getName() + " временно забанен на " + timeStr + " | Причина: " + reason
        )), "adminessentialsplus.notify");

        sender.sendMessage(MessageUtil.success("Игрок " + target.getName() + " забанен на " + timeStr));

        return true;
    }

    private long parseTime(String time) {
        try {
            char unit = time.charAt(time.length() - 1);
            int amount = Integer.parseInt(time.substring(0, time.length() - 1));

            return switch (unit) {
                case 'm', 'м' -> TimeUnit.MINUTES.toMillis(amount);
                case 'h', 'ч' -> TimeUnit.HOURS.toMillis(amount);
                case 'd', 'д' -> TimeUnit.DAYS.toMillis(amount);
                case 'w', 'н' -> TimeUnit.DAYS.toMillis(amount * 7);
                default -> -1;
            };
        } catch (Exception e) {
            return -1;
        }
    }

    private String formatTime(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) % 24;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60;

        if (days > 0) return days + "д " + hours + "ч";
        if (hours > 0) return hours + "ч " + minutes + "м";
        return minutes + "м";
    }
}
