package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KickCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public KickCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.kick")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /kick <игрок> [причина]");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        String reason = "Не указана";
        if (args.length > 1) {
            reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        }

        target.kickPlayer(ChatColor.RED + "Вы были исключены с сервера!\n" + 
                         ChatColor.YELLOW + "Причина: " + ChatColor.WHITE + reason + "\n" +
                         ChatColor.GRAY + "Администратор: " + sender.getName());

        plugin.getServer().broadcastMessage(ChatColor.RED + target.getName() + " был исключен администратором " + sender.getName());
        sender.sendMessage(ChatColor.GREEN + "Игрок " + target.getName() + " исключен. Причина: " + reason);

        return true;
    }
}

