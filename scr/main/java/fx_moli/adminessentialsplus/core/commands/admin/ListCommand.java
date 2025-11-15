package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class ListCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ListCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.list")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        int online = plugin.getServer().getOnlinePlayers().size();
        int max = plugin.getServer().getMaxPlayers();

        MessageUtil.sendHeader(sender, "Игроки онлайн: " + online + "/" + max);
        
        String players = plugin.getServer().getOnlinePlayers().stream()
            .map(p -> {
                String color = "§a";
                if (p.hasPermission("adminessentialsplus.*")) {
                    color = "§c";
                } else if (p.hasPermission("adminessentialsplus.staffchat")) {
                    color = "§6";
                }
                return color + p.getName();
            })
            .collect(Collectors.joining("§7, "));

        sender.sendMessage(players);
        MessageUtil.sendFooter(sender);

        return true;
    }
}
