package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public MenuCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.menu")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            MessageUtil.sendError(sender, "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        
        if (plugin.getGuiManager() != null) {
            plugin.getGuiManager().openMainMenu(player);
            MessageUtil.sendActionBar(player, "§6✨ Открыто главное меню");
        } else {
            MessageUtil.sendError(player, "GUI Manager не инициализирован!");
        }

        return true;
    }
}
