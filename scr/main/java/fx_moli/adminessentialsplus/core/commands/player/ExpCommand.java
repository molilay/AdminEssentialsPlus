package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ExpCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ExpCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.exp")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx exp <количество> [игрок]");
            sender.sendMessage(ChatColor.YELLOW + "Используйте + или - для добавления/удаления опыта");
            return true;
        }

        Player target;
        String expStr = args[0];
        boolean isAdd = expStr.startsWith("+");
        boolean isRemove = expStr.startsWith("-");
        int amount;

        try {
            amount = Integer.parseInt(expStr.replace("+", "").replace("-", ""));
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "Неверное количество опыта!");
            return true;
        }

        if (args.length > 1 && sender.hasPermission("adminessentialsplus.exp.others")) {
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

        if (isAdd) {
            target.giveExp(amount);
            if (target != sender) {
                sender.sendMessage(ChatColor.GREEN + "Добавлено " + amount + " опыта игроку " + target.getName());
            }
            target.sendMessage(ChatColor.GREEN + "Вам добавлено " + amount + " опыта");
        } else if (isRemove) {
            int currentExp = target.getTotalExperience();
            target.setExp(0);
            target.setLevel(0);
            target.setTotalExperience(0);
            target.giveExp(Math.max(0, currentExp - amount));
            if (target != sender) {
                sender.sendMessage(ChatColor.GREEN + "Удалено " + amount + " опыта у игрока " + target.getName());
            }
            target.sendMessage(ChatColor.GREEN + "У вас удалено " + amount + " опыта");
        } else {
            target.setExp(0);
            target.setLevel(0);
            target.setTotalExperience(0);
            target.giveExp(amount);
            if (target != sender) {
                sender.sendMessage(ChatColor.GREEN + "Установлено " + amount + " опыта игроку " + target.getName());
            }
            target.sendMessage(ChatColor.GREEN + "Вам установлено " + amount + " опыта");
        }

        return true;
    }
}

