package fx_moli.adminessentialsplus.core.commands.admin;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SeenCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public SeenCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.seen")) {
            sender.sendMessage("§cНет прав!");
            return true;
        }
        
        if (args.length < 1) {
            sender.sendMessage("§cИспользование: /seen <игрок>");
            return true;
        }
        
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        
        if (!target.hasPlayedBefore()) {
            sender.sendMessage("§cИгрок никогда не был на сервере!");
            return true;
        }
        
        if (target.isOnline()) {
            sender.sendMessage("§e" + target.getName() + " §aсейчас в сети!");
            return true;
        }
        
        long lastPlayed = target.getLastPlayed();
        long timeDiff = System.currentTimeMillis() - lastPlayed;
        
        long days = timeDiff / (1000 * 60 * 60 * 24);
        long hours = (timeDiff / (1000 * 60 * 60)) % 24;
        long minutes = (timeDiff / (1000 * 60)) % 60;
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        String date = sdf.format(new Date(lastPlayed));
        
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("§6Игрок: §e" + target.getName());
        sender.sendMessage("§6Последний вход: §e" + date);
        sender.sendMessage("§6Прошло: §e" + days + "д " + hours + "ч " + minutes + "м");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        return true;
    }
}
