package fx_moli.adminessentialsplus.core.commands.fun;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class LaunchCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public LaunchCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.launch")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        Player target;
        double power = 2.0;

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Использование: /launch <игрок> [сила]");
                return true;
            }
            target = (Player) sender;
        } else {
            target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendError(sender, "Игрок не найден!");
                return true;
            }
            if (args.length > 1) {
                try {
                    power = Double.parseDouble(args[1]);
                    power = Math.min(power, 10.0);
                } catch (NumberFormatException e) {
                    MessageUtil.sendError(sender, "Неверная сила!");
                    return true;
                }
            }
        }

        Vector velocity = new Vector(0, power, 0);
        target.setVelocity(velocity);
        
        MessageUtil.sendSuccess(sender, "Игрок §e" + target.getName() + " §aзапущен в воздух!");
        MessageUtil.sendTitle(target, "🚀", "Полетели!");
        MessageUtil.sendActionBar(target, "§6Wheeeee!");

        return true;
    }
}
