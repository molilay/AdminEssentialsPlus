package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Команда быстрых действий для админов
 * Упрощает выполнение частых задач
 */
public class QuickActionsCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public QuickActionsCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtil.error("Эта команда только для игроков!"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("adminessentialsplus.quickactions")) {
            player.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        if (args.length == 0) {
            showHelp(player);
            return true;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "healall":
                healAllPlayers(player);
                break;
            case "feedall":
                feedAllPlayers(player);
                break;
            case "tpall":
                teleportAllToMe(player);
                break;
            case "clearlag":
                clearLag(player);
                break;
            case "day":
                setDay(player);
                break;
            case "night":
                setNight(player);
                break;
            case "sun":
                setSun(player);
                break;
            case "rain":
                setRain(player);
                break;
            default:
                player.sendMessage(MessageUtil.error("Неизвестное действие: " + action));
                showHelp(player);
                break;
        }

        return true;
    }

    private void showHelp(Player player) {
        player.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
        player.sendMessage(MessageUtil.format("&6&lБыстрые действия"));
        player.sendMessage("");
        player.sendMessage(MessageUtil.format("&e/qa healall &7- Вылечить всех"));
        player.sendMessage(MessageUtil.format("&e/qa feedall &7- Накормить всех"));
        player.sendMessage(MessageUtil.format("&e/qa tpall &7- ТП всех к себе"));
        player.sendMessage(MessageUtil.format("&e/qa clearlag &7- Очистить лаги"));
        player.sendMessage(MessageUtil.format("&e/qa day &7- Установить день"));
        player.sendMessage(MessageUtil.format("&e/qa night &7- Установить ночь"));
        player.sendMessage(MessageUtil.format("&e/qa sun &7- Убрать дождь"));
        player.sendMessage(MessageUtil.format("&e/qa rain &7- Включить дождь"));
        player.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
    }

    private void healAllPlayers(Player admin) {
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setHealth(20.0);
            p.setFoodLevel(20);
            p.setFireTicks(0);
            count++;
        }
        Bukkit.broadcastMessage(MessageUtil.success("Все игроки вылечены администратором " + admin.getName()));
        admin.sendMessage(MessageUtil.success("Вылечено игроков: " + count));
    }

    private void feedAllPlayers(Player admin) {
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setFoodLevel(20);
            p.setSaturation(20);
            count++;
        }
        Bukkit.broadcastMessage(MessageUtil.success("Все игроки накормлены администратором " + admin.getName()));
        admin.sendMessage(MessageUtil.success("Накормлено игроков: " + count));
    }

    private void teleportAllToMe(Player admin) {
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(admin)) {
                p.teleport(admin.getLocation());
                count++;
            }
        }
        Bukkit.broadcastMessage(MessageUtil.info("Все игроки телепортированы к " + admin.getName()));
        admin.sendMessage(MessageUtil.success("Телепортировано игроков: " + count));
    }

    private void clearLag(Player admin) {
        admin.getWorld().getEntities().stream()
            .filter(e -> !(e instanceof Player))
            .forEach(e -> e.remove());
        Bukkit.broadcastMessage(MessageUtil.success("Лаги очищены администратором " + admin.getName()));
    }

    private void setDay(Player admin) {
        admin.getWorld().setTime(1000);
        Bukkit.broadcastMessage(MessageUtil.info("Время изменено на день"));
    }

    private void setNight(Player admin) {
        admin.getWorld().setTime(13000);
        Bukkit.broadcastMessage(MessageUtil.info("Время изменено на ночь"));
    }

    private void setSun(Player admin) {
        admin.getWorld().setStorm(false);
        admin.getWorld().setThundering(false);
        Bukkit.broadcastMessage(MessageUtil.info("Погода изменена на солнечную"));
    }

    private void setRain(Player admin) {
        admin.getWorld().setStorm(true);
        Bukkit.broadcastMessage(MessageUtil.info("Погода изменена на дождь"));
    }
}
