package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class EnchantCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public EnchantCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.enchant")) {
            sender.sendMessage(ChatColor.RED + "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только для игроков!");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Использование: /fx enchant <зачарование> <уровень>");
            sender.sendMessage(ChatColor.YELLOW + "Пример: /fx enchant sharpness 5");
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            player.sendMessage(ChatColor.RED + "Держите предмет в руке!");
            return true;
        }

        try {
            Enchantment enchantment = Enchantment.getByName(args[0].toUpperCase());
            if (enchantment == null) {
                player.sendMessage(ChatColor.RED + "Зачарование '" + args[0] + "' не найдено!");
                return true;
            }

            int level = Integer.parseInt(args[1]);
            if (level < 1 || level > 10) {
                player.sendMessage(ChatColor.RED + "Уровень должен быть от 1 до 10!");
                return true;
            }

            item.addUnsafeEnchantment(enchantment, level);
            player.sendMessage(ChatColor.GREEN + "Зачарование " + args[0] + " " + level + " применено!");
        } catch (NumberFormatException e) {
            player.sendMessage(ChatColor.RED + "Неверный уровень зачарования!");
        }

        return true;
    }
}

