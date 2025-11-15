package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Команда очистки чата
 */
public class ClearChatCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ClearChatCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.clearchat")) {
            sender.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        // Отправляем 100 пустых строк всем игрокам
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("adminessentialsplus.clearchat.bypass")) {
                for (int i = 0; i < 100; i++) {
                    player.sendMessage("");
                }
            }
        }

        String adminName = sender instanceof Player ? ((Player) sender).getName() : "Консоль";
        
        Bukkit.broadcastMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
        Bukkit.broadcastMessage(MessageUtil.format("&e&lЧат очищен администратором " + adminName));
        Bukkit.broadcastMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));

        return true;
    }
}
