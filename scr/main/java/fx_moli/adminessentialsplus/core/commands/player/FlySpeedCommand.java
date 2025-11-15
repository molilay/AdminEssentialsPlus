package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlySpeedCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public FlySpeedCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.flyspeed")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Использование: /fx flyspeed <скорость> [игрок]");
                return true;
            }
            Player player = (Player) sender;
            float speed = player.getFlySpeed() * 10;
            sender.sendMessage(ChatColor.GREEN + "Текущая скорость полета: " + String.format("%.1f", speed));
            return true;
        }

        float speed;
        try {
            speed = Float.parseFloat(args[0]);
            if (speed < 0.1f || speed > 10.0f) {
                sender.sendMessage(ChatColor.RED + "Скорость должна быть от 0.1 до 10.0!");
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Неверное значение скорости!");
            return true;
        }

        Player target;
        if (args.length > 1 && sender.hasPermission("adminessentialsplus.flyspeed.others")) {
            target = plugin.getServer().getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            }
        } else if (sender instanceof Player) {
            target = (Player) sender;
        } else {
            sender.sendMessage(ChatColor.RED + "Укажите игрока!");
            return true;
        }

        float finalSpeed = speed / 10.0f;
        target.setFlySpeed(finalSpeed);
        if (target != sender) {
            sender.sendMessage(ChatColor.GREEN + "Скорость полета для " + target.getName() + " установлена: " + speed);
        }
        target.sendMessage(ChatColor.GREEN + "Скорость полета установлена: " + speed);

        return true;
    }
}

