package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public PingCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.ping")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        Player target;
        if (args.length > 0) {
            target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendError(sender, "Игрок не найден!");
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Укажите игрока!");
                return true;
            }
            target = (Player) sender;
        }

        int ping = target.getPing();
        String color;
        String quality;

        if (ping < 50) {
            color = "§a";
            quality = "Отличный";
        } else if (ping < 100) {
            color = "§2";
            quality = "Хороший";
        } else if (ping < 150) {
            color = "§e";
            quality = "Средний";
        } else if (ping < 250) {
            color = "§6";
            quality = "Плохой";
        } else {
            color = "§c";
            quality = "Ужасный";
        }

        if (target == sender) {
            MessageUtil.sendInfo(sender, "Ваш пинг: " + color + ping + "ms §7(" + quality + ")");
        } else {
            MessageUtil.sendInfo(sender, "Пинг игрока §e" + target.getName() + "§7: " + color + ping + "ms §7(" + quality + ")");
        }

        return true;
    }
}
