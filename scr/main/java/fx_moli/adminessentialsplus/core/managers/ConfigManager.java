package fx_moli.adminessentialsplus.core.managers;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {
    private final AdminEssentialsPlus plugin;
    private FileConfiguration config;
    private FileConfiguration aiConfig;
    private FileConfiguration banItemsConfig;

    public ConfigManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        // Main config
        plugin.saveDefaultConfig();
        config = plugin.getConfig();

        // AI config
        File aiConfigFile = new File(plugin.getDataFolder(), "ai-config.yml");
        if (!aiConfigFile.exists()) plugin.saveResource("ai-config.yml", false);
        aiConfig = YamlConfiguration.loadConfiguration(aiConfigFile);

        // Ban items config
        File banItemsFile = new File(plugin.getDataFolder(), "ban-items.yml");
        if (!banItemsFile.exists()) plugin.saveResource("ban-items.yml", false);
        banItemsConfig = YamlConfiguration.loadConfiguration(banItemsFile);

        plugin.getLogger().info("Configurations loaded successfully");
    }

    public boolean isAIEnabled() { return config.getBoolean("settings.ai-enabled", true); }
    public String getAIProvider() { return aiConfig.getString("provider", "groq"); }
    public String getGroqKey() { return aiConfig.getString("api.groq-key", ""); }
    public List<String> getBannedItems() { return banItemsConfig.getStringList("banned-items"); }

    public FileConfiguration getConfig() { return config; }
    public FileConfiguration getAIConfig() { return aiConfig; }
    public FileConfiguration getBanItemsConfig() { return banItemsConfig; }
}