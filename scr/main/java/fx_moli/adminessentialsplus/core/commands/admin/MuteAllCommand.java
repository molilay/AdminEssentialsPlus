package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MuteAllCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    private static final Set<UUID> mutedPlayers = new HashSet<>();
    
    public MuteAllCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.muteall")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        int muted = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.hasPermission("adminessentialsplus.mute.bypass")) {
                mutedPlayers.add(player.getUniqueId());
                player.sendMessage("§c§l[!] §cВы замучены администрацией!");
                muted++;
            }
        }
        
        sender.sendMessage("§a✔ Замучено игроков: " + muted);
        Bukkit.broadcastMessage("§c§l[Kekrix] §cЧат отключён администрацией!");
        
        return true;
    }
    
    public static boolean isMuted(UUID uuid) {
        return mutedPlayers.contains(uuid);
    }
    
    public static void unmute(UUID uuid) {
        mutedPlayers.remove(uuid);
    }
    
    public static void unmuteAll() {
        mutedPlayers.clear();
    }
}
