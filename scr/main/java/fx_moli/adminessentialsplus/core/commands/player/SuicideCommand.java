package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SuicideCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public SuicideCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.suicide")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            MessageUtil.sendError(sender, "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        player.setHealth(0);
        plugin.getServer().broadcastMessage("§7" + player.getName() + " §cпокончил с собой...");

        return true;
    }
}
