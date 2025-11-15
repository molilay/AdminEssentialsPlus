package fx_moli.adminessentialsplus.core.listeners;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.commands.player.GodCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodModeListener implements Listener {
    private final AdminEssentialsPlus plugin;

    public GodModeListener(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Player) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getEntity();
            if (plugin.getCommandManager() != null) {
                GodCommand godCommand = plugin.getCommandManager().getGodCommand();
                if (godCommand != null && godCommand.isGodMode(player)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}

