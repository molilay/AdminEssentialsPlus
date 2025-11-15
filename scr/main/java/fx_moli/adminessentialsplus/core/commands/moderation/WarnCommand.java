package fx_moli.adminessentialsplus.core.commands.moderation;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WarnCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Map<UUID, Integer> warnings = new HashMap<>();

    public WarnCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.warn")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Использование: /warn <игрок> <причина>");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        int warnCount = warnings.getOrDefault(target.getUniqueId(), 0) + 1;
        warnings.put(target.getUniqueId(), warnCount);

        target.sendMessage(ChatColor.RED + "═══════════════════════════════");
        target.sendMessage(ChatColor.RED + "⚠ ВЫ ПОЛУЧИЛИ ПРЕДУПРЕЖДЕНИЕ ⚠");
        target.sendMessage(ChatColor.YELLOW + "Причина: " + ChatColor.WHITE + reason);
        target.sendMessage(ChatColor.YELLOW + "Администратор: " + ChatColor.WHITE + sender.getName());
        target.sendMessage(ChatColor.YELLOW + "Всего предупреждений: " + ChatColor.RED + warnCount);
        target.sendMessage(ChatColor.RED + "═══════════════════════════════");

        sender.sendMessage(ChatColor.GREEN + "Игрок " + target.getName() + " получил предупреждение (" + warnCount + ")");
        
        // Уведомление в staff chat
        String staffMsg = ChatColor.RED + "[WARN] " + ChatColor.YELLOW + target.getName() + 
                         ChatColor.RED + " получил предупреждение от " + ChatColor.WHITE + sender.getName() +
                         ChatColor.RED + ". Причина: " + ChatColor.WHITE + reason;
        
        for (Player staff : plugin.getServer().getOnlinePlayers()) {
            if (staff.hasPermission("adminessentialsplus.staffchat")) {
                staff.sendMessage(staffMsg);
            }
        }

        return true;
    }

    public int getWarnings(Player player) {
        return warnings.getOrDefault(player.getUniqueId(), 0);
    }
}

