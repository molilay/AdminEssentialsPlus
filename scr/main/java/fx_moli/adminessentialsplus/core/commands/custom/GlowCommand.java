package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Команда свечения игрока
 */
public class GlowCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public GlowCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.glow")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        Player target;
        
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Укажите игрока!");
                return true;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendError(sender, "Игрок не найден!");
                return true;
            }
        }
        
        boolean newState = !target.isGlowing();
        target.setGlowing(newState);
        
        if (newState) {
            MessageUtil.sendSuccess(sender, "✨ Свечение включено для " + target.getName());
            MessageUtil.sendInfo(target, "✨ Вы теперь светитесь!");
        } else {
            MessageUtil.sendSuccess(sender, "🌑 Свечение выключено для " + target.getName());
            MessageUtil.sendInfo(target, "🌑 Свечение отключено");
        }
        
        return true;
    }
}
