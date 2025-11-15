package fx_moli.adminessentialsplus.core.commands.fun;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ExplodeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ExplodeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.explode")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            MessageUtil.sendError(sender, "Использование: /explode <игрок> [сила] [огонь] [разрушение]");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            MessageUtil.sendError(sender, "Игрок не найден!");
            return true;
        }

        float power = 4.0f;
        boolean fire = false;
        boolean breakBlocks = false;

        if (args.length > 1) {
            try {
                power = Float.parseFloat(args[1]);
                power = Math.min(power, 10.0f);
            } catch (NumberFormatException e) {
                MessageUtil.sendError(sender, "Неверная сила!");
                return true;
            }
        }

        if (args.length > 2) {
            fire = args[2].equalsIgnoreCase("true") || args[2].equalsIgnoreCase("да");
        }

        if (args.length > 3) {
            breakBlocks = args[3].equalsIgnoreCase("true") || args[3].equalsIgnoreCase("да");
        }

        target.getWorld().createExplosion(target.getLocation(), power, fire, breakBlocks);
        MessageUtil.sendSuccess(sender, "Взрыв создан у игрока §e" + target.getName() + "§a!");

        return true;
    }
}
