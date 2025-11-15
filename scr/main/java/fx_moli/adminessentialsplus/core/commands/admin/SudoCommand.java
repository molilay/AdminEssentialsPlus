package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SudoCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public SudoCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.sudo")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (args.length < 2) {
            MessageUtil.sendError(sender, "Использование: /sudo <игрок> <команда>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            MessageUtil.sendError(sender, "Игрок не найден!");
            return true;
        }

        String command = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        
        if (command.startsWith("c:")) {
            // Заставить написать в чат
            String message = command.substring(2);
            target.chat(message);
            MessageUtil.sendSuccess(sender, "Игрок §e" + target.getName() + " §aнаписал: §7" + message);
        } else {
            // Выполнить команду
            plugin.getServer().dispatchCommand(target, command);
            MessageUtil.sendSuccess(sender, "Игрок §e" + target.getName() + " §aвыполнил: §7/" + command);
        }

        return true;
    }
}
