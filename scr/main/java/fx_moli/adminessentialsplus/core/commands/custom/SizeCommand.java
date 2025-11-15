package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Команда изменения размера игрока
 */
public class SizeCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public SizeCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.size")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }
        
        if (args.length < 1) {
            MessageUtil.sendError(sender, "Использование: /size <tiny/small/normal/large/giant> [игрок]");
            return true;
        }
        
        Player target;
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessageUtil.sendError(sender, "Игрок не найден!");
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                MessageUtil.sendError(sender, "Укажите игрока!");
                return true;
            }
            target = (Player) sender;
        }
        
        double scale;
        String sizeName;
        
        switch (args[0].toLowerCase()) {
            case "tiny" -> {
                scale = 0.25;
                sizeName = "крошечный";
            }
            case "small" -> {
                scale = 0.5;
                sizeName = "маленький";
            }
            case "normal" -> {
                scale = 1.0;
                sizeName = "обычный";
            }
            case "large" -> {
                scale = 2.0;
                sizeName = "большой";
            }
            case "giant" -> {
                scale = 4.0;
                sizeName = "гигантский";
            }
            default -> {
                MessageUtil.sendError(sender, "Неверный размер! Используйте: tiny, small, normal, large, giant");
                return true;
            }
        }
        
        // Используем GENERIC_SCALE для Paper 1.21+
        try {
            // Пытаемся получить атрибут GENERIC_SCALE через рефлексию для совместимости
            Attribute scaleAttribute = null;
            for (Attribute attr : Attribute.values()) {
                if (attr.name().equals("GENERIC_SCALE")) {
                    scaleAttribute = attr;
                    break;
                }
            }
            
            if (scaleAttribute != null) {
                target.getAttribute(scaleAttribute).setBaseValue(scale);
            } else {
                MessageUtil.sendError(sender, "Атрибут GENERIC_SCALE недоступен. Требуется Paper 1.21.3+");
                return true;
            }
        } catch (Exception e) {
            MessageUtil.sendError(sender, "Ошибка изменения размера: " + e.getMessage());
            return true;
        }
        
        MessageUtil.sendSuccess(sender, "📏 Размер " + target.getName() + " изменён на: " + sizeName);
        MessageUtil.sendInfo(target, "📏 Ваш размер изменён на: " + sizeName);
        
        return true;
    }
}
