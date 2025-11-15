package fx_moli.adminessentialsplus.core.managers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Менеджер варпов (точек телепортации)
 */
public class WarpManager {
    private final File warpsFile;
    private FileConfiguration warpsConfig;
    private final Map<String, Location> warps = new HashMap<>();
    
    public WarpManager(File dataFolder) {
        this.warpsFile = new File(dataFolder, "warps.yml");
        loadWarps();
    }
    
    /**
     * Загрузить варпы из файла
     */
    public void loadWarps() {
        if (!warpsFile.exists()) {
            try {
                warpsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
        
        warpsConfig = YamlConfiguration.loadConfiguration(warpsFile);
        warps.clear();
        
        if (warpsConfig.contains("warps")) {
            for (String warpName : warpsConfig.getConfigurationSection("warps").getKeys(false)) {
                String path = "warps." + warpName + ".";
                String worldName = warpsConfig.getString(path + "world");
                World world = Bukkit.getWorld(worldName);
                
                if (world != null) {
                    double x = warpsConfig.getDouble(path + "x");
                    double y = warpsConfig.getDouble(path + "y");
                    double z = warpsConfig.getDouble(path + "z");
                    float yaw = (float) warpsConfig.getDouble(path + "yaw");
                    float pitch = (float) warpsConfig.getDouble(path + "pitch");
                    
                    Location location = new Location(world, x, y, z, yaw, pitch);
                    warps.put(warpName.toLowerCase(), location);
                }
            }
        }
    }
    
    /**
     * Сохранить варпы в файл
     */
    public void saveWarps() {
        for (Map.Entry<String, Location> entry : warps.entrySet()) {
            String path = "warps." + entry.getKey() + ".";
            Location loc = entry.getValue();
            
            warpsConfig.set(path + "world", loc.getWorld().getName());
            warpsConfig.set(path + "x", loc.getX());
            warpsConfig.set(path + "y", loc.getY());
            warpsConfig.set(path + "z", loc.getZ());
            warpsConfig.set(path + "yaw", loc.getYaw());
            warpsConfig.set(path + "pitch", loc.getPitch());
        }
        
        try {
            warpsConfig.save(warpsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Создать новый варп
     */
    public boolean createWarp(String name, Location location) {
        warps.put(name.toLowerCase(), location);
        saveWarps();
        return true;
    }
    
    /**
     * Удалить варп
     */
    public boolean deleteWarp(String name) {
        if (warps.remove(name.toLowerCase()) != null) {
            warpsConfig.set("warps." + name, null);
            saveWarps();
            return true;
        }
        return false;
    }
    
    /**
     * Получить локацию варпа
     */
    public Location getWarp(String name) {
        return warps.get(name.toLowerCase());
    }
    
    /**
     * Проверить существование варпа
     */
    public boolean warpExists(String name) {
        return warps.containsKey(name.toLowerCase());
    }
    
    /**
     * Получить список всех варпов
     */
    public Set<String> getWarpNames() {
        return new HashSet<>(warps.keySet());
    }
    
    /**
     * Телепортировать игрока к варпу
     */
    public boolean teleportToWarp(Player player, String warpName) {
        Location location = getWarp(warpName);
        if (location != null) {
            player.teleport(location);
            return true;
        }
        return false;
    }
    
    /**
     * Получить количество варпов
     */
    public int getWarpCount() {
        return warps.size();
    }
}
