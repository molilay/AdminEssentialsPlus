package fx_moli.adminessentialsplus.core.commands.player;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HatCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public HatCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.hat")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (!(sender instanceof Player)) {
            MessageUtil.sendError(sender, "Только для игроков!");
            return true;
        }

        Player player = (Player) sender;
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (hand == null || hand.getType() == Material.AIR) {
            MessageUtil.sendError(player, "Держите предмет в руке!");
            return true;
        }

        ItemStack helmet = player.getInventory().getHelmet();
        player.getInventory().setHelmet(hand.clone());
        player.getInventory().setItemInMainHand(helmet);

        MessageUtil.sendSuccess(player, "Предмет надет на голову!");
        MessageUtil.sendActionBar(player, "§6✨ Новая шляпа! ✨");

        return true;
    }
}
