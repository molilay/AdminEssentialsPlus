package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NickCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public NickCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.nick")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        Player target;
        String nickname;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Использование: /nick <ник> [игрок]");
                return true;
            }
            MessageUtil.sendError(sender, "Использование: /nick <ник> [игрок]");
            return true;
        }

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Укажите игрока!");
                return true;
            }
            target = (Player) sender;
            nickname = args[0];
        } else {
            if (!sender.hasPermission("adminessentialsplus.nick.others")) {
                MessageUtil.sendError(sender, "Нет прав для изменения ника других игроков!");
                return true;
            }
            target = plugin.getServer().getPlayer(args[1]);
            if (target == null) {
                MessageUtil.sendError(sender, "Игрок не найден!");
                return true;
            }
            nickname = args[0];
        }

        if (nickname.equalsIgnoreCase("off") || nickname.equalsIgnoreCase("reset")) {
            target.setDisplayName(target.getName());
            target.setPlayerListName(target.getName());
            MessageUtil.sendSuccess(target, "Ваш ник сброшен!");
            if (target != sender) {
                MessageUtil.sendSuccess(sender, "Ник игрока " + target.getName() + " сброшен!");
            }
        } else {
            String coloredNick = MessageUtil.colorize(nickname);
            target.setDisplayName(coloredNick);
            target.setPlayerListName(coloredNick);
            MessageUtil.sendSuccess(target, "Ваш ник изменен на: " + coloredNick);
            if (target != sender) {
                MessageUtil.sendSuccess(sender, "Ник игрока " + target.getName() + " изменен на: " + coloredNick);
            }
        }

        return true;
    }
}
