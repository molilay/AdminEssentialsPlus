package fx_moli.adminessentialsplus.core.commands.custom;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Команда запуска игрока как ракеты с обратным отсчётом
 */
public class RocketCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public RocketCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("adminessentialsplus.rocket")) {
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
        
        Player finalTarget = target;
        
        // Обратный отсчёт
        new BukkitRunnable() {
            int count = 3;
            
            @Override
            public void run() {
                if (count > 0) {
                    finalTarget.sendTitle("§c§l" + count, "§7Приготовьтесь к запуску!", 0, 20, 10);
                    finalTarget.playSound(finalTarget.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1.0f, 1.0f);
                    count--;
                } else {
                    // ЗАПУСК!
                    finalTarget.sendTitle("§e§l🚀 ЗАПУСК!", "", 0, 40, 20);
                    finalTarget.setVelocity(new Vector(0, 3, 0));
                    finalTarget.playSound(finalTarget.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 2.0f, 1.0f);
                    
                    // Эффект ракеты
                    new BukkitRunnable() {
                        int ticks = 0;
                        
                        @Override
                        public void run() {
                            if (ticks++ > 60 || finalTarget.isOnGround()) {
                                cancel();
                                return;
                            }
                            
                            Location loc = finalTarget.getLocation();
                            loc.getWorld().spawnParticle(Particle.FLAME, loc, 10, 0.2, 0.2, 0.2, 0.01);
                            loc.getWorld().spawnParticle(Particle.SMOKE, loc, 5, 0.1, 0.1, 0.1, 0.01);
                        }
                    }.runTaskTimer(plugin, 0, 1);
                    
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
        
        MessageUtil.sendSuccess(sender, "🚀 Запуск ракеты для " + target.getName() + "!");
        
        return true;
    }
}
