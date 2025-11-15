package fx_moli.adminessentialsplus.core.commands.fun;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FreezeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Set<UUID> frozenPlayers = new HashSet<>();

    public FreezeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.freeze")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            MessageUtil.sendError(sender, "Использование: /freeze <игрок>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            MessageUtil.sendError(sender, "Игрок не найден!");
            return true;
        }

        UUID uuid = target.getUniqueId();
        if (frozenPlayers.contains(uuid)) {
            frozenPlayers.remove(uuid);
            target.setWalkSpeed(0.2f);
            target.setFlySpeed(0.1f);
            MessageUtil.sendSuccess(target, "Вы разморожены!");
            MessageUtil.sendSuccess(sender, "Игрок §e" + target.getName() + " §aразморожен!");
            MessageUtil.sendTitle(target, "❄", "Разморожен!");
        } else {
            frozenPlayers.add(uuid);
            target.setWalkSpeed(0.0f);
            target.setFlySpeed(0.0f);
            MessageUtil.sendWarning(target, "Вы заморожены администратором!");
            MessageUtil.sendSuccess(sender, "Игрок §e" + target.getName() + " §aзаморожен!");
            MessageUtil.sendTitle(target, "❄", "Заморожен!");
        }

        return true;
    }

    public boolean isFrozen(Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }

    public void unfreeze(Player player) {
        frozenPlayers.remove(player.getUniqueId());
        player.setWalkSpeed(0.2f);
        player.setFlySpeed(0.1f);
    }
}
