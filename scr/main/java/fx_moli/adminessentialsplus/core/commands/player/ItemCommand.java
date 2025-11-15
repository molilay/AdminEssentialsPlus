package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public ItemCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.item")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx item <предмет> [количество] [игрок]");
            return true;
        }

        Player target;
        int amount = 1;
        int materialIndex = 0;

        // Определяем количество
        if (args.length > 1) {
            try {
                amount = Integer.parseInt(args[1]);
                if (amount < 1) amount = 1;
                if (amount > 64) amount = 64;
                materialIndex = 1;
            } catch (NumberFormatException e) {
                // Не число, значит это имя игрока или часть названия материала
            }
        }

        // Определяем игрока
        if (args.length > 2 || (args.length > 1 && !args[1].matches("\\d+"))) {
            String playerName = args[args.length - 1];
            target = plugin.getServer().getPlayer(playerName);
            if (target == null && sender.hasPermission("adminessentialsplus.item.others")) {
                sender.sendMessage(ChatColor.RED + "Игрок не найден!");
                return true;
            } else if (target == null) {
                target = sender instanceof Player ? (Player) sender : null;
            }
        } else {
            target = sender instanceof Player ? (Player) sender : null;
        }

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Укажите игрока!");
            return true;
        }

        // Получаем материал
        String materialName = args[0].toUpperCase();
        Material material = Material.getMaterial(materialName);

        if (material == null) {
            sender.sendMessage(ChatColor.RED + "Предмет '" + args[0] + "' не найден!");
            return true;
        }

        ItemStack item = new ItemStack(material, amount);
        target.getInventory().addItem(item);

        if (target != sender) {
            sender.sendMessage(ChatColor.GREEN + "Выдан " + amount + "x " + materialName + " игроку " + target.getName());
        }
        target.sendMessage(ChatColor.GREEN + "Вам выдан " + amount + "x " + materialName);

        return true;
    }
}

