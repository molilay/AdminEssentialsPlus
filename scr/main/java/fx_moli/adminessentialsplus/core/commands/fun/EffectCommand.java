package fx_moli.adminessentialsplus.core.commands.fun;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class EffectCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;

    public EffectCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("adminessentialsplus.effect")) {
            MessageUtil.sendError(sender, "Нет прав!");
            return true;
        }

        if (args.length < 2) {
            MessageUtil.sendError(sender, "Использование: /effect <игрок> <эффект> [длительность] [уровень]");
            MessageUtil.sendInfo(sender, "Примеры эффектов: speed, strength, jump_boost, regeneration, invisibility");
            return true;
        }

        Player target = plugin.getServer().getPlayer(args[0]);
        if (target == null) {
            MessageUtil.sendError(sender, "Игрок не найден!");
            return true;
        }

        String effectName = args[1].toUpperCase();
        int duration = args.length > 2 ? Integer.parseInt(args[2]) : 60;
        int amplifier = args.length > 3 ? Integer.parseInt(args[3]) - 1 : 0;

        try {
            PotionEffectType effectType;
            
            // Специальные команды
            if (effectName.equals("CLEAR") || effectName.equals("REMOVE")) {
                target.getActivePotionEffects().clear();
                for (PotionEffect effect : target.getActivePotionEffects()) {
                    target.removePotionEffect(effect.getType());
                }
                MessageUtil.sendSuccess(sender, "Все эффекты сняты с игрока §e" + target.getName());
                MessageUtil.sendInfo(target, "Все эффекты сняты!");
                return true;
            }
            
            effectType = PotionEffectType.getByName(effectName);
            
            if (effectType == null) {
                MessageUtil.sendError(sender, "Неизвестный эффект: " + effectName);
                return true;
            }

            PotionEffect effect = new PotionEffect(effectType, duration * 20, amplifier, false, true, true);
            target.addPotionEffect(effect);

            MessageUtil.sendSuccess(sender, "Эффект §e" + effectName + " §aприменен к §e" + target.getName());
            MessageUtil.sendInfo(target, "Вы получили эффект: §e" + effectName + " " + (amplifier + 1));
            MessageUtil.sendActionBar(target, "§d✨ " + effectName + " " + (amplifier + 1) + " §7(" + duration + "s)");

        } catch (Exception e) {
            MessageUtil.sendError(sender, "Ошибка при применении эффекта!");
        }

        return true;
    }
}
