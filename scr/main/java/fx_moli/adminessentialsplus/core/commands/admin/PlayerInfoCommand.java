package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Команда для быстрого просмотра информации о игроке
 */
public class PlayerInfoCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public PlayerInfoCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.playerinfo")) {
            sender.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageUtil.error("Использование: /pinfo <игрок>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(MessageUtil.error("Игрок не найден!"));
            return true;
        }

        showPlayerInfo(sender, target);
        return true;
    }

    private void showPlayerInfo(CommandSender sender, Player target) {
        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
        sender.sendMessage(MessageUtil.format("&6&lИнформация о игроке: &e" + target.getName()));
        sender.sendMessage("");
        
        // Основная информация
        sender.sendMessage(MessageUtil.format("&7UUID: &f" + target.getUniqueId()));
        sender.sendMessage(MessageUtil.format("&7IP: &f" + target.getAddress().getAddress().getHostAddress()));
        sender.sendMessage(MessageUtil.format("&7Пинг: &f" + target.getPing() + "ms"));
        sender.sendMessage("");
        
        // Игровая информация
        sender.sendMessage(MessageUtil.format("&7Режим игры: &f" + target.getGameMode().name()));
        sender.sendMessage(MessageUtil.format("&7Здоровье: &c" + String.format("%.1f", target.getHealth()) + "&7/&c20.0"));
        sender.sendMessage(MessageUtil.format("&7Голод: &6" + target.getFoodLevel() + "&7/&620"));
        sender.sendMessage(MessageUtil.format("&7Уровень: &a" + target.getLevel() + " &7(Опыт: " + target.getExp() + ")"));
        sender.sendMessage("");
        
        // Местоположение
        sender.sendMessage(MessageUtil.format("&7Мир: &f" + target.getWorld().getName()));
        sender.sendMessage(MessageUtil.format("&7Координаты: &f" + 
            String.format("X: %.1f Y: %.1f Z: %.1f", 
                target.getLocation().getX(), 
                target.getLocation().getY(), 
                target.getLocation().getZ())));
        sender.sendMessage("");
        
        // Статусы
        sender.sendMessage(MessageUtil.format("&7Полет: " + (target.getAllowFlight() ? "&aВкл" : "&cВыкл")));
        sender.sendMessage(MessageUtil.format("&7Режим бога: " + (target.isInvulnerable() ? "&aВкл" : "&cВыкл")));
        sender.sendMessage(MessageUtil.format("&7Оператор: " + (target.isOp() ? "&aДа" : "&cНет")));
        
        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
    }
}
