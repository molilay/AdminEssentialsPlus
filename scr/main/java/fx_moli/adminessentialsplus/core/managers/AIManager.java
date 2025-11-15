package fx_moli.adminessentialsplus.core.managers;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.AIClient;

public class AIManager {
    private final AdminEssentialsPlus plugin;
    private AIClient aiClient;
    private boolean enabled;
    private String provider;

    public AIManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
        this.enabled = false;
    }

    public void initialize() {
        ConfigManager config = plugin.getConfigManager();
        if (config == null) {
            plugin.getLogger().warning("AI disabled: ConfigManager is null");
            return;
        }
        
        this.provider = config.getAIProvider();
        String apiKey = config.getGroqKey();
        String model = config.getAIConfig().getString("model.groq-model", "llama3-8b-8192");

        if (apiKey == null || apiKey.isEmpty() || apiKey.equals("your-groq-key-here")) {
            plugin.getLogger().warning("AI disabled: No API key configured");
            return;
        }

        this.aiClient = new AIClient(apiKey, model, provider, plugin);
        this.enabled = true;
        plugin.getLogger().info("AI Manager initialized with " + provider);
    }

    public void shutdown() { this.enabled = false; }
    public boolean isEnabled() { return enabled; }
    public AIClient getAIClient() { return aiClient; }
    public String getProvider() { return provider != null ? provider : "groq"; }
    public String analyzeChatMessage(String message, String player) {
        if (!enabled || aiClient == null) {
            return null;
        }
        return aiClient.moderateText(message);
    }
}