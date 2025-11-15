package fx_moli.adminessentialsplus.core.managers;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InspectionManager {
    private final AdminEssentialsPlus plugin;
    private final Map<UUID, UUID> inspectingPlayers = new HashMap<>();
    private final Map<UUID, UUID> watchingPlayers = new HashMap<>();

    public InspectionManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    public void startInspecting(Player admin, Player target) {
        inspectingPlayers.put(admin.getUniqueId(), target.getUniqueId());
    }

    public void stopInspecting(Player admin) {
        inspectingPlayers.remove(admin.getUniqueId());
    }

    public void startWatching(Player admin, Player target) {
        watchingPlayers.put(admin.getUniqueId(), target.getUniqueId());
        admin.setInvisible(true);
    }

    public void stopWatching(Player admin) {
        watchingPlayers.remove(admin.getUniqueId());
        admin.setInvisible(false);
    }

    public boolean isInspecting(Player admin) {
        return inspectingPlayers.containsKey(admin.getUniqueId());
    }

    public boolean isWatching(Player admin) {
        return watchingPlayers.containsKey(admin.getUniqueId());
    }
}