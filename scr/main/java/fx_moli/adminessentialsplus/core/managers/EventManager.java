package fx_moli.adminessentialsplus.core.managers;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;

public class EventManager {

    private final AdminEssentialsPlus plugin;
    private boolean eventActive = false;
    private String currentEventType = "";

    public EventManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    public void startEvent(String eventType) {
        this.eventActive = true;
        this.currentEventType = eventType;

        plugin.getLogger().info("Starting event: " + eventType);
        // Event-specific initialization logic would go here
    }

    public void stopEvent() {
        this.eventActive = false;
        this.currentEventType = "";

        plugin.getLogger().info("Event stopped");
        // Cleanup logic would go here
    }

    public boolean isEventActive() {
        return eventActive;
    }

    public String getCurrentEventType() {
        return currentEventType;
    }
}