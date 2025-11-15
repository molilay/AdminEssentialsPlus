package fx_moli.adminessentialsplus.core.commands.world;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class WorldInfoCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public WorldInfoCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.worldinfo")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        World world;
        if (args.length > 0) {
            world = plugin.getServer().getWorld(args[0]);
            if (world == null) {
                MessageUtil.sendError(sender, "Мир не найден!");
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Укажите мир!");
                return true;
            }
            world = ((Player) sender).getWorld();
        }

        MessageUtil.sendHeader(sender, "Информация о мире: " + world.getName());
        sender.sendMessage("§7Тип: §e" + world.getEnvironment().name());
        sender.sendMessage("§7Сложность: §e" + world.getDifficulty().name());
        sender.sendMessage("§7Игроков: §e" + world.getPlayers().size());
        sender.sendMessage("§7Сущностей: §e" + world.getEntities().size());
        sender.sendMessage("§7Загруженных чанков: §e" + world.getLoadedChunks().length);
        sender.sendMessage("§7Время: §e" + formatTime(world.getTime()));
        sender.sendMessage("§7Погода: §e" + (world.hasStorm() ? "Дождь" : "Ясно"));
        sender.sendMessage("§7Спавн: §e" + MessageUtil.formatLocation(world.getSpawnLocation()));
        sender.sendMessage("§7PVP: §e" + (world.getPVP() ? "Включен" : "Выключен"));
        sender.sendMessage("§7Сид: §e" + world.getSeed());
        MessageUtil.sendFooter(sender);

        return true;
    }

    private String formatTime(long time) {
        long hours = (time / 1000 + 6) % 24;
        long minutes = (time % 1000) * 60 / 1000;
        return String.format("%02d:%02d", hours, minutes);
    }
}
