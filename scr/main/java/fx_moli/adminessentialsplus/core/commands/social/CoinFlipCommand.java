package fx_moli.adminessentialsplus.core.commands.social;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Random;

public class CoinFlipCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private final Random random = new Random();
    
    public CoinFlipCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cТолько для игроков!");
            return true;
        }
        
        boolean result = random.nextBoolean();
        String outcome = result ? "§6Орёл" : "§7Решка";
        
        player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0f, 1.0f);
        Bukkit.broadcastMessage("§e🎲 " + player.getName() + " §7подбросил(а) монетку! Выпало: " + outcome);
        
        return true;
    }
}
