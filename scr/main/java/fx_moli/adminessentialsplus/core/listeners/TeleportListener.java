package fx_moli.adminessentialsplus.core.listeners;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class TeleportListener implements Listener {
    private final AdminEssentialsPlus plugin;

    public TeleportListener(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (plugin.getCommandManager() != null && plugin.getCommandManager().getBackCommand() != null) {
            // Сохраняем позицию перед телепортом для команды /back
            plugin.getCommandManager().getBackCommand().saveLocation(event.getPlayer());
        }
    }
}

