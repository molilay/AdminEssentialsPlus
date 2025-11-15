package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Команда ночного зрения
 * Создано для Kekrix
 */
public class NightVisionCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public NightVisionCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.nightvision")) {
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
        
        if (target.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
            target.removePotionEffect(PotionEffectType.NIGHT_VISION);
            MessageUtil.sendSuccess(sender, "🌙 Ночное зрение выключено для " + target.getName());
            MessageUtil.sendInfo(target, "🌙 Ночное зрение выключено");
        } else {
            target.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));
            MessageUtil.sendSuccess(sender, "🌙 Ночное зрение включено для " + target.getName());
            MessageUtil.sendInfo(target, "🌙 Ночное зрение включено");
        }
        
        return true;
    }
}
