package fx_moli.adminessentialsplus.core.listeners;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryListener implements Listener {
    private final AdminEssentialsPlus plugin;

    public InventoryListener(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // Блокируем клики в GUI инспекции
        if (event.getView().getTitle().contains("Инспекция:")) {
            event.setCancelled(true);
        }
    }
}