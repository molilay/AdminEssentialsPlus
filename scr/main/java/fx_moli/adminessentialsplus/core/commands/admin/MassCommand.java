package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Команда для массовых действий над игроками
 */
public class MassCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public MassCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.mass")) {
            sender.sendMessage(MessageUtil.error("У вас нет прав!"));
            return true;
        }

        if (args.length == 0) {
            showHelp(sender);
            return true;
        }

        String action = args[0].toLowerCase();

        switch (action) {
            case "gamemode":
            case "gm":
                if (args.length < 2) {
                    sender.sendMessage(MessageUtil.error("Использование: /mass gm <режим>"));
                    return true;
                }
                massGamemode(sender, args[1]);
                break;
            case "clear":
                massClearInventory(sender);
                break;
            case "kill":
                massKill(sender);
                break;
            case "kick":
                String reason = args.length > 1 ? String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length)) : "Массовый кик";
                massKick(sender, reason);
                break;
            case "tp":
                if (!(sender instanceof Player)) {
                    sender.sendMessage(MessageUtil.error("Только для игроков!"));
                    return true;
                }
                massTeleport((Player) sender);
                break;
            default:
                sender.sendMessage(MessageUtil.error("Неизвестное действие!"));
                showHelp(sender);
                break;
        }

        return true;
    }

    private void showHelp(CommandSender sender) {
        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
        sender.sendMessage(MessageUtil.format("&c&lМассовые действия"));
        sender.sendMessage("");
        sender.sendMessage(MessageUtil.format("&e/mass gm <режим> &7- Изменить режим всем"));
        sender.sendMessage(MessageUtil.format("&e/mass clear &7- Очистить инвентарь всем"));
        sender.sendMessage(MessageUtil.format("&e/mass kill &7- Убить всех игроков"));
        sender.sendMessage(MessageUtil.format("&e/mass kick [причина] &7- Кикнуть всех"));
        sender.sendMessage(MessageUtil.format("&e/mass tp &7- ТП всех к себе"));
        sender.sendMessage(MessageUtil.format("&8&m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"));
    }

    private void massGamemode(CommandSender sender, String mode) {
        GameMode gameMode;
        try {
            gameMode = GameMode.valueOf(mode.toUpperCase());
        } catch (IllegalArgumentException e) {
            // Попробуем по номеру
            switch (mode) {
                case "0": gameMode = GameMode.SURVIVAL; break;
                case "1": gameMode = GameMode.CREATIVE; break;
                case "2": gameMode = GameMode.ADVENTURE; break;
                case "3": gameMode = GameMode.SPECTATOR; break;
                default:
                    sender.sendMessage(MessageUtil.error("Неверный режим игры!"));
                    return;
            }
        }

        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setGameMode(gameMode);
            count++;
        }

        Bukkit.broadcastMessage(MessageUtil.info("Режим игры изменен на " + gameMode.name() + " для всех игроков"));
        sender.sendMessage(MessageUtil.success("Изменено игроков: " + count));
    }

    private void massClearInventory(CommandSender sender) {
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.getInventory().clear();
            count++;
        }

        Bukkit.broadcastMessage(MessageUtil.warning("Инвентарь очищен у всех игроков"));
        sender.sendMessage(MessageUtil.success("Очищено игроков: " + count));
    }

    private void massKill(CommandSender sender) {
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.setHealth(0);
            count++;
        }

        Bukkit.broadcastMessage(MessageUtil.warning("Все игроки убиты"));
        sender.sendMessage(MessageUtil.success("Убито игроков: " + count));
    }

    private void massKick(CommandSender sender, String reason) {
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("adminessentialsplus.bypass.kick")) {
                continue;
            }
            p.kick(net.kyori.adventure.text.Component.text(
                MessageUtil.format("&cВы были кикнуты\n&7Причина: &f" + reason)
            ));
            count++;
        }

        sender.sendMessage(MessageUtil.success("Кикнуто игроков: " + count));
    }

    private void massTeleport(Player admin) {
        int count = 0;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.equals(admin)) {
                p.teleport(admin.getLocation());
                count++;
            }
        }

        Bukkit.broadcastMessage(MessageUtil.info("Все игроки телепортированы к " + admin.getName()));
        admin.sendMessage(MessageUtil.success("Телепортировано: " + count));
    }
}
