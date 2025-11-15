package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Команда проверки IP адреса игрока и поиска альтов
 */
public class CheckIPCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public CheckIPCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.checkip")) {
            sender.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageUtil.error("Использование: /fx checkip <игрок>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(MessageUtil.error("Игрок должен быть онлайн!"));
            return true;
        }

        String targetIP = target.getAddress().getAddress().getHostAddress();
        
        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
        sender.sendMessage(MessageUtil.format("&6&lПроверка IP: &e" + target.getName()));
        sender.sendMessage("");
        sender.sendMessage(MessageUtil.format("&7IP адрес: &f" + targetIP));
        sender.sendMessage(MessageUtil.format("&7Пинг: &e" + target.getPing() + "ms"));
        sender.sendMessage("");

        // Поиск других игроков с таким же IP
        List<String> alts = new ArrayList<>();
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.equals(target)) {
                String playerIP = online.getAddress().getAddress().getHostAddress();
                if (playerIP.equals(targetIP)) {
                    alts.add(online.getName());
                }
            }
        }

        if (!alts.isEmpty()) {
            sender.sendMessage(MessageUtil.format("&c&lВозможные альты онлайн:"));
            for (String alt : alts) {
                sender.sendMessage(MessageUtil.format("  &7- &e" + alt));
            }
        } else {
            sender.sendMessage(MessageUtil.format("&a&lАльты не обнаружены"));
        }

        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));

        return true;
    }
}
