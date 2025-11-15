package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KitCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public KitCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.kit")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx kit <название>");
            sender.sendMessage(ChatColor.YELLOW + "Доступные наборы: starter, tools, food");
            return true;
        }

        Player player = (Player) sender;
        String kitName = args[0].toLowerCase();
        giveKit(player, kitName);

        return true;
    }

    private void giveKit(Player player, String kitName) {
        FileConfiguration config = plugin.getConfig();
        String path = "kits." + kitName;

        if (!config.contains(path)) {
            // Создаем стандартные наборы
            createDefaultKits();
        }

        List<String> items = config.getStringList(path + ".items");
        if (items.isEmpty()) {
            player.sendMessage(ChatColor.RED + "Набор '" + kitName + "' не найден!");
            return;
        }

        int given = 0;
        for (String itemStr : items) {
            try {
                String[] parts = itemStr.split(":");
                Material material = Material.getMaterial(parts[0].toUpperCase());
                int amount = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;

                if (material != null) {
                    ItemStack item = new ItemStack(material, amount);
                    player.getInventory().addItem(item);
                    given++;
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Ошибка при выдаче предмета из набора: " + itemStr);
            }
        }

        player.sendMessage(ChatColor.GREEN + "Выдан набор: " + kitName + " (" + given + " предметов)");
    }

    private void createDefaultKits() {
        FileConfiguration config = plugin.getConfig();
        
        // Стартовый набор
        config.set("kits.starter.items", java.util.Arrays.asList(
            "WOODEN_SWORD:1",
            "WOODEN_PICKAXE:1",
            "WOODEN_AXE:1",
            "BREAD:16"
        ));

        // Набор инструментов
        config.set("kits.tools.items", java.util.Arrays.asList(
            "IRON_PICKAXE:1",
            "IRON_AXE:1",
            "IRON_SHOVEL:1",
            "IRON_HOE:1"
        ));

        // Набор еды
        config.set("kits.food.items", java.util.Arrays.asList(
            "COOKED_BEEF:32",
            "COOKED_PORKCHOP:32",
            "BREAD:32"
        ));

        plugin.saveConfig();
    }
}

