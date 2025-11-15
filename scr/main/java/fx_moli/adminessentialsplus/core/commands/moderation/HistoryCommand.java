package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Команда просмотра истории наказаний игрока
 */
public class HistoryCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public HistoryCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.history")) {
            sender.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageUtil.error("Использование: /fx history <игрок> [количество]"));
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        if (!target.hasPlayedBefore() && !target.isOnline()) {
            sender.sendMessage(MessageUtil.error("Игрок не найден!"));
            return true;
        }

        int limit = 10;
        if (args.length > 1) {
            try {
                limit = Integer.parseInt(args[1]);
                if (limit < 1 || limit > 50) limit = 10;
            } catch (NumberFormatException e) {
                limit = 10;
            }
        }

        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
        sender.sendMessage(MessageUtil.format("&c&lИстория наказаний: &e" + target.getName()));
        sender.sendMessage("");

        // Получаем историю наказаний
        List<String> history = plugin.getPunishmentManager().getPunishmentHistory(target.getUniqueId(), limit);
        
        if (history.isEmpty()) {
            sender.sendMessage(MessageUtil.format("&7История наказаний пуста"));
        } else {
            for (String entry : history) {
                sender.sendMessage(entry);
            }
        }

        sender.sendMessage("");
        
        // Показываем активные предупреждения
        int warnings = plugin.getPunishmentManager().getWarnings(target.getUniqueId());
        sender.sendMessage(MessageUtil.format("&e&lАктивные предупреждения: &c" + warnings));
        
        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));

        return true;
    }
}
