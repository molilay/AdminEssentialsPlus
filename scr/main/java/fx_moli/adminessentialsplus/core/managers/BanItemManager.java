package fx_moli.adminessentialsplus.core.managers;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class BanItemManager implements Listener {
    private final AdminEssentialsPlus plugin;
    private List<String> bannedItems;

    public BanItemManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    public void loadBannedItems() {
        if (plugin.getConfigManager() != null) {
            this.bannedItems = plugin.getConfigManager().getBannedItems();
            if (bannedItems != null) {
                plugin.getLogger().info("Loaded " + bannedItems.size() + " banned items");
            } else {
                this.bannedItems = new java.util.ArrayList<>();
                plugin.getLogger().warning("Banned items list is null, using empty list");
            }
        } else {
            this.bannedItems = new java.util.ArrayList<>();
            plugin.getLogger().warning("ConfigManager is null, using empty banned items list");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (bannedItems == null || bannedItems.isEmpty()) {
            return;
        }
        
        ItemStack item = event.getItem();
        if (item == null) return;

        String itemId = item.getType().getKey().toString();
        if (bannedItems.contains(itemId)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cЭтот предмет запрещен к использованию!");
        }
    }

    public List<String> getBannedItems() { return bannedItems; }
}