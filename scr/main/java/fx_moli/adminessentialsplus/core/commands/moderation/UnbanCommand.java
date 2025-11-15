package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class UnbanCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public UnbanCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.unban")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /unban <игрок>");
            return true;
        }

        String targetName = args[0];
        ConfigurationSection bansSection = plugin.getConfig().getConfigurationSection("bans");
        
        if (bansSection == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не забанен!");
            return true;
        }

        boolean found = false;
        for (String uuid : bansSection.getKeys(false)) {
            String name = bansSection.getString(uuid + ".name");
            if (name != null && name.equalsIgnoreCase(targetName)) {
                plugin.getConfig().set("bans." + uuid, null);
                plugin.saveConfig();
                found = true;
                break;
            }
        }

        if (found) {
            plugin.getServer().broadcastMessage(ChatColor.GREEN + targetName + " был разбанен администратором " + sender.getName());
            sender.sendMessage(ChatColor.GREEN + "Игрок " + targetName + " разбанен!");
        } else {
            sender.sendMessage(ChatColor.RED + "Игрок не найден в списке банов!");
        }

        return true;
    }
}

