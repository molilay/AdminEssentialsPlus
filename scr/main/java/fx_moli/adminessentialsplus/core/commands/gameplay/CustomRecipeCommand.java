package fx_moli.adminessentialsplus.core.commands.gameplay;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class CustomRecipeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public CustomRecipeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.recipe")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        sender.sendMessage(ChatColor.GREEN + "Открыт интерфейс управления рецептами!");

        return true;
    }
}