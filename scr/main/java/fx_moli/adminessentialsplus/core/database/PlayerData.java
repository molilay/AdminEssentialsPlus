package fx_moli.adminessentialsplus.core.database;

import java.util.UUID;

public class PlayerData {

    private final UUID uuid;
    private String username;
    private long lastJoin;
    private long playTime;
    private String inventoryData;

    public PlayerData(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
        this.lastJoin = System.currentTimeMillis();
        this.playTime = 0;
    }

    // Getters and setters
    public UUID getUuid() { return uuid; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public long getLastJoin() { return lastJoin; }
    public void setLastJoin(long lastJoin) { this.lastJoin = lastJoin; }
    public long getPlayTime() { return playTime; }
    public void setPlayTime(long playTime) { this.playTime = playTime; }
    public String getInventoryData() { return inventoryData; }
    public void setInventoryData(String inventoryData) { this.inventoryData = inventoryData; }
}