package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public BroadcastCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.broadcast")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            MessageUtil.sendError(sender, "Использование: /broadcast <сообщение>");
            return true;
        }

        String message = String.join(" ", args);
        String formatted = MessageUtil.colorize(message);

        plugin.getServer().broadcastMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        plugin.getServer().broadcastMessage("§6§l[ОБЪЯВЛЕНИЕ]");
        plugin.getServer().broadcastMessage("§f" + formatted);
        plugin.getServer().broadcastMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        MessageUtil.sendSuccess(sender, "Объявление отправлено!");

        return true;
    }
}
