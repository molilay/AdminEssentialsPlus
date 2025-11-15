package fx_moli.adminessentialsplus;

import fx_moli.adminessentialsplus.core.commands.CommandManager;
import fx_moli.adminessentialsplus.core.database.DatabaseManager;
import fx_moli.adminessentialsplus.core.listeners.ChatListener;
import fx_moli.adminessentialsplus.core.listeners.InventoryListener;
import fx_moli.adminessentialsplus.core.listeners.PlayerListener;
import fx_moli.adminessentialsplus.core.managers.AIManager;
import fx_moli.adminessentialsplus.core.managers.BanItemManager;
import fx_moli.adminessentialsplus.core.managers.ConfigManager;
import fx_moli.adminessentialsplus.core.managers.InspectionManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AdminEssentialsPlus extends JavaPlugin {

    private ConfigManager configManager;
    private DatabaseManager databaseManager;
    private CommandManager commandManager;
    private InspectionManager inspectionManager;
    private BanItemManager banItemManager;
    private fx_moli.adminessentialsplus.core.gui.GUIManager guiManager;

    @Override
    public void onEnable() {
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        getLogger().info("  _  __     _    _      ");
        getLogger().info(" | |/ /___ | | _| |__   ");
        getLogger().info(" | ' // _ \\| |/ / '_ \\  ");
        getLogger().info(" | . \\  __/|   <| |_) | ");
        getLogger().info(" |_|\\_\\___||_|\\_\\_.__/  ");
        getLogger().info("");
        getLogger().info("╔═══════════════════════════════════════╗");
        getLogger().info("║  AdminEssentialsPlus v1.0.0          ║");
        getLogger().info("║  Создано специально для Kekrix       ║");
        getLogger().info("║  Автор: FX_MOLI                      ║");
        getLogger().info("╚═══════════════════════════════════════╝");
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        // Инициализация менеджеров (порядок важен!)
        configManager = new ConfigManager(this);
        configManager.loadConfigs();

        databaseManager = new DatabaseManager(this);
        databaseManager.connect();

        inspectionManager = new InspectionManager(this);

        banItemManager = new BanItemManager(this);
        banItemManager.loadBannedItems();

        // Инициализация GUI Manager
        guiManager = new fx_moli.adminessentialsplus.core.gui.GUIManager(this);

        // Регистрация команд
        commandManager = new CommandManager(this);
        commandManager.registerCommands();

        // Регистрация слушателей
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(banItemManager, this);
        getServer().getPluginManager().registerEvents(new fx_moli.adminessentialsplus.core.listeners.GodModeListener(this), this);
        getServer().getPluginManager().registerEvents(new fx_moli.adminessentialsplus.core.listeners.TeleportListener(this), this);
        
        // Регистрация нового listener для freeze
        fx_moli.adminessentialsplus.core.listeners.FreezeListener freezeListener = 
            new fx_moli.adminessentialsplus.core.listeners.FreezeListener(this);
        getServer().getPluginManager().registerEvents(freezeListener, this);
        
        // Регистрация GUI listener
        getServer().getPluginManager().registerEvents(
            new fx_moli.adminessentialsplus.core.gui.GUIListener(this, guiManager), this);

        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        getLogger().info("✓ Плагин успешно загружен для Kekrix!");
        getLogger().info("✓ Команд зарегистрировано: " + getDescription().getCommands().size());
        getLogger().info("✓ Kekrix Edition активирована!");
        getLogger().info("✓ Готов к работе!");
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public void onDisable() {
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        getLogger().info("Выключение AdminEssentialsPlus для Kekrix...");
        
        if (databaseManager != null) {
            databaseManager.disconnect();
            getLogger().info("✓ База данных отключена");
        }
        
        getLogger().info("✓ AdminEssentialsPlus успешно выключен!");
        getLogger().info("✓ До встречи на Kekrix!");
        getLogger().info("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    // Геттеры для менеджеров
    public ConfigManager getConfigManager() {
        return configManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public InspectionManager getInspectionManager() {
        return inspectionManager;
    }

    public BanItemManager getBanItemManager() {
        return banItemManager;
    }

    public fx_moli.adminessentialsplus.core.gui.GUIManager getGuiManager() {
        return guiManager;
    }
}