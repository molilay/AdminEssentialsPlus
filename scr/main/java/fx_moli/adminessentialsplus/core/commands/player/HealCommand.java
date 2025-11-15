package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public HealCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.heal")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        Player target;
        if (args.length > 0 && sender.hasPermission("adminessentialsplus.heal.others")) {
            target = plugin.getServer().getPlayer(args[0]);
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

        target.setHealth(target.getMaxHealth());
        target.setFoodLevel(20);
        target.setSaturation(20);
        target.setFireTicks(0);
        target.setRemainingAir(target.getMaximumAir());

        if (target != sender) {
            sender.sendMessage(ChatColor.GREEN + "Игрок " + target.getName() + " вылечен!");
        }
        target.sendMessage(ChatColor.GREEN + "Вы вылечены!");

        return true;
    }
}

