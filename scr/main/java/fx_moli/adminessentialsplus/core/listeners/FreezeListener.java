package fx_moli.adminessentialsplus.core.listeners;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.commands.fun.FreezeCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class FreezeListener implements Listener {
    private final AdminEssentialsPlus plugin;
    private FreezeCommand freezeCommand;

    public FreezeListener(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    public void setFreezeCommand(FreezeCommand freezeCommand) {
        this.freezeCommand = freezeCommand;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (freezeCommand != null && freezeCommand.isFrozen(event.getPlayer())) {
            // Отменяем движение
            if (event.getFrom().getX() != event.getTo().getX() ||
                event.getFrom().getY() != event.getTo().getY() ||
                event.getFrom().getZ() != event.getTo().getZ()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (freezeCommand != null && freezeCommand.isFrozen(event.getPlayer())) {
            freezeCommand.unfreeze(event.getPlayer());
        }
    }
}
