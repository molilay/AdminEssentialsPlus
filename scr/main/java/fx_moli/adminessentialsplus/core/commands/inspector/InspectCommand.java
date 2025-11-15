package fx_moli.adminessentialsplus.core.commands.inspector;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class InspectCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public InspectCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        Player admin = (Player) sender;
        if (!admin.hasPermission("adminessentialsplus.inspect")) {
            admin.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length < 1) {
            admin.sendMessage(ChatColor.RED + "Использование: /" + label + " <игрок>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            admin.sendMessage(ChatColor.RED + "Игрок не найден!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("inspect")) {
            openInspectionGUI(admin, target);
        } else if (cmd.getName().equalsIgnoreCase("watch")) {
            startSpectating(admin, target);
        }

        return true;
    }

    private void openInspectionGUI(Player admin, Player target) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "Инспекция: " + target.getName());

        // Копируем инвентарь
        for (int i = 0; i < 36; i++) {
            if (target.getInventory().getItem(i) != null) {
                inv.setItem(i, target.getInventory().getItem(i).clone());
            }
        }

        // Броня
        inv.setItem(45, target.getInventory().getHelmet());
        inv.setItem(46, target.getInventory().getChestplate());
        inv.setItem(47, target.getInventory().getLeggings());
        inv.setItem(48, target.getInventory().getBoots());

        // Вторая рука
        inv.setItem(49, target.getInventory().getItemInOffHand());

        admin.openInventory(inv);
        admin.sendMessage(ChatColor.GREEN + "Инспектируете " + target.getName());
        if (plugin.getInspectionManager() != null) {
            plugin.getInspectionManager().startInspecting(admin, target);
        }
    }

    private void startSpectating(Player admin, Player target) {
        admin.setInvisible(true);
        admin.setGameMode(org.bukkit.GameMode.SPECTATOR);
        admin.teleport(target.getLocation());
        admin.sendMessage(ChatColor.GREEN + "Наблюдаете за " + target.getName() + " (невидимый)");
        if (plugin.getInspectionManager() != null) {
            plugin.getInspectionManager().startWatching(admin, target);
        }
    }
}