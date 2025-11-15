package fx_moli.adminessentialsplus.core.listeners;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final AdminEssentialsPlus plugin;

    public PlayerListener(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Логирование входа админов
        if (event.getPlayer().hasPermission("adminessentialsplus.staffchat")) {
            plugin.getLogger().info("Администратор " + event.getPlayer().getName() + " зашел на сервер");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Останавливаем инспекцию если админ вышел
        if (plugin.getInspectionManager() != null) {
            if (plugin.getInspectionManager().isInspecting(event.getPlayer()) ||
                    plugin.getInspectionManager().isWatching(event.getPlayer())) {
                plugin.getInspectionManager().stopInspecting(event.getPlayer());
                plugin.getInspectionManager().stopWatching(event.getPlayer());
            }
        }
    }
}