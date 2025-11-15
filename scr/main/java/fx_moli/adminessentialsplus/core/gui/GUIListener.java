package fx_moli.adminessentialsplus.core.gui;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.utils.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GUIListener implements Listener {
    private final AdminEssentialsPlus plugin;
    private final GUIManager guiManager;
    
    // Хранение выбранной команды для каждого админа
    private final Map<UUID, String> selectedCommands = new HashMap<>();

    public GUIListener(AdminEssentialsPlus plugin, GUIManager guiManager) {
        this.plugin = plugin;
        this.guiManager = guiManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        
        String title = event.getView().getTitle();
        ItemStack clicked = event.getCurrentItem();
        
        if (clicked == null || clicked.getType() == Material.AIR) return;
        
        // Проверяем, что это наше GUI
        if (!title.contains("§")) return;
        
        event.setCancelled(true);
        
        String itemName = clicked.getItemMeta() != null ? clicked.getItemMeta().getDisplayName() : "";
        
        // Обработка кнопки "Назад"
        if (itemName.contains("Назад")) {
            guiManager.openMainMenu(player);
            return;
        }
        
        // Главное меню - выбор категории
        if (title.contains("Выбор команды")) {
            handleMainMenuClick(player, itemName);
        }
        // Меню категорий команд
        else if (title.contains("Управление игроками")) {
            handleCategoryClick(player, itemName, "player");
        }
        else if (title.contains("Телепортация")) {
            handleCategoryClick(player, itemName, "teleport");
        }
        else if (title.contains("Модерация")) {
            handleCategoryClick(player, itemName, "moderation");
        }
        else if (title.contains("Развлечения")) {
            handleCategoryClick(player, itemName, "fun");
        }
        else if (title.contains("Эффекты")) {
            handleCategoryClick(player, itemName, "effects");
        }
        else if (title.contains("Изменение")) {
            handleCategoryClick(player, itemName, "modify");
        }
        else if (title.contains("Просмотр")) {
            handleCategoryClick(player, itemName, "view");
        }
        else if (title.contains("Быстрые действия")) {
            handleQuickActionsClick(player, itemName);
        }
        // Выбор игрока
        else if (title.contains("Выбор игрока")) {
            handlePlayerSelection(player, clicked);
        }
        // Список игроков
        else if (title.contains("Список игроков")) {
            if (clicked.getType() == Material.PLAYER_HEAD) {
                String targetName = itemName.replace("§6", "");
                Player target = Bukkit.getPlayer(targetName);
                if (target != null) {
                    guiManager.openPlayerActionsMenu(player, target);
                }
            }
        }
        // Статистика
        else if (title.contains("Статистика сервера")) {
            player.closeInventory();
            player.performCommand("fx serverstats");
        }
    }

    private void handleMainMenuClick(Player player, String itemName) {
        if (itemName.contains("Управление игроками")) {
            guiManager.openPlayerManagementCommands(player);
        }
        else if (itemName.contains("Телепортация")) {
            guiManager.openTeleportCommands(player);
        }
        else if (itemName.contains("Модерация")) {
            guiManager.openModerationCommands(player);
        }
        else if (itemName.contains("Развлечения")) {
            guiManager.openFunCommands(player);
        }
        else if (itemName.contains("Эффекты")) {
            guiManager.openEffectCommands(player);
        }
        else if (itemName.contains("Изменение")) {
            guiManager.openModifyCommands(player);
        }
        else if (itemName.contains("Просмотр")) {
            guiManager.openViewCommands(player);
        }
        else if (itemName.contains("Быстрые действия")) {
            guiManager.openQuickActionsMenu(player);
        }
        else if (itemName.contains("Список игроков")) {
            guiManager.openPlayerManagementMenu(player);
        }
        else if (itemName.contains("Статистика")) {
            player.closeInventory();
            player.performCommand("fx serverstats");
        }
    }

    private void handleCategoryClick(Player player, String itemName, String category) {
        String command = extractCommand(itemName);
        if (command != null) {
            selectedCommands.put(player.getUniqueId(), command);
            guiManager.openPlayerSelector(player, command);
        }
    }

    private void handleQuickActionsClick(Player player, String itemName) {
        player.closeInventory();
        
        if (itemName.contains("HealAll")) {
            player.performCommand("fx qa healall");
        }
        else if (itemName.contains("FeedAll")) {
            player.performCommand("fx qa feedall");
        }
        else if (itemName.contains("ClearLag")) {
            player.performCommand("fx qa clearlag");
        }
        else if (itemName.contains("TPAll")) {
            player.performCommand("fx qa tpall");
        }
        else if (itemName.contains("Day")) {
            player.performCommand("fx qa day");
        }
        else if (itemName.contains("Sun")) {
            player.performCommand("fx qa sun");
        }
    }

    private void handlePlayerSelection(Player admin, ItemStack clicked) {
        if (clicked.getType() != Material.PLAYER_HEAD) return;
        
        String targetName = clicked.getItemMeta().getDisplayName().replace("§6", "");
        Player target = Bukkit.getPlayer(targetName);
        
        if (target == null) {
            MessageUtil.sendError(admin, "Игрок не найден!");
            admin.closeInventory();
            return;
        }
        
        String command = selectedCommands.get(admin.getUniqueId());
        if (command == null) {
            MessageUtil.sendError(admin, "Команда не выбрана!");
            admin.closeInventory();
            return;
        }
        
        admin.closeInventory();
        executeCommand(admin, target, command);
        selectedCommands.remove(admin.getUniqueId());
    }

    private String extractCommand(String itemName) {
        // Извлекаем название команды из имени предмета
        if (itemName.contains("Heal")) return "heal";
        if (itemName.contains("Feed")) return "feed";
        if (itemName.contains("Fly")) return "fly";
        if (itemName.contains("God")) return "god";
        if (itemName.contains("Vanish")) return "vanish";
        if (itemName.contains("Clear")) return "clear";
        if (itemName.contains("Exp")) return "exp";
        
        if (itemName.contains("TP") && !itemName.contains("Here")) return "tp";
        if (itemName.contains("TPHere")) return "tphere";
        if (itemName.contains("Bring")) return "tphere";
        
        if (itemName.contains("Kick")) return "kick";
        if (itemName.contains("Ban")) return "ban";
        if (itemName.contains("Mute")) return "mute";
        if (itemName.contains("Warn")) return "warn";
        if (itemName.contains("Freeze")) return "freeze";
        
        if (itemName.contains("Launch")) return "launch";
        if (itemName.contains("Explode")) return "explode";
        if (itemName.contains("Slap")) return "slap";
        if (itemName.contains("Rocket")) return "rocket";
        
        if (itemName.contains("Glow")) return "glow";
        if (itemName.contains("Trail")) return "trail";
        if (itemName.contains("Particle")) return "particle";
        if (itemName.contains("Rainbow")) return "rainbow";
        if (itemName.contains("NightVision")) return "nightvision";
        
        if (itemName.contains("Size")) return "size";
        if (itemName.contains("Nick")) return "nick";
        if (itemName.contains("Skull")) return "skull";
        
        if (itemName.contains("InvSee")) return "invsee";
        if (itemName.contains("EnderChest")) return "enderchest";
        if (itemName.contains("PlayerInfo")) return "pinfo";
        
        return null;
    }

    private void executeCommand(Player admin, Player target, String command) {
        String targetName = target.getName();
        
        switch (command.toLowerCase()) {
            case "heal":
                admin.performCommand("fx heal " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aвылечен!");
                break;
            case "feed":
                admin.performCommand("fx feed " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aнакормлен!");
                break;
            case "fly":
                admin.performCommand("fx fly " + targetName);
                MessageUtil.sendSuccess(admin, "Полет переключен для §e" + targetName);
                break;
            case "god":
                admin.performCommand("fx god " + targetName);
                MessageUtil.sendSuccess(admin, "Режим бога переключен для §e" + targetName);
                break;
            case "vanish":
                admin.performCommand("fx vanish " + targetName);
                MessageUtil.sendSuccess(admin, "Невидимость переключена для §e" + targetName);
                break;
            case "clear":
                admin.performCommand("fx clear " + targetName);
                MessageUtil.sendSuccess(admin, "Инвентарь §e" + targetName + " §aочищен!");
                break;
            case "tp":
                admin.performCommand("fx tp " + targetName);
                MessageUtil.sendSuccess(admin, "Телепортация к §e" + targetName);
                break;
            case "tphere":
                admin.performCommand("fx tphere " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aтелепортирован к вам!");
                break;
            case "kick":
                admin.performCommand("fx kick " + targetName + " Кикнут через GUI");
                break;
            case "ban":
                admin.performCommand("fx ban " + targetName + " Забанен через GUI");
                break;
            case "mute":
                admin.performCommand("fx mute " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aзамучен!");
                break;
            case "warn":
                admin.performCommand("fx warn " + targetName + " Предупреждение через GUI");
                break;
            case "freeze":
                admin.performCommand("fx freeze " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aзаморожен!");
                break;
            case "launch":
                admin.performCommand("fx launch " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aзапущен в воздух!");
                break;
            case "explode":
                admin.performCommand("fx explode " + targetName);
                MessageUtil.sendSuccess(admin, "Взрыв у §e" + targetName + "§a!");
                break;
            case "slap":
                admin.performCommand("fx slap " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aполучил шлепок!");
                break;
            case "rocket":
                admin.performCommand("fx rocket " + targetName);
                MessageUtil.sendSuccess(admin, "§e" + targetName + " §aзапущен как ракета!");
                break;
            case "glow":
                admin.performCommand("fx glow " + targetName);
                MessageUtil.sendSuccess(admin, "Свечение переключено для §e" + targetName);
                break;
            case "trail":
                admin.performCommand("fx trail firework " + targetName);
                MessageUtil.sendSuccess(admin, "След активирован для §e" + targetName);
                break;
            case "particle":
                admin.performCommand("fx particle heart " + targetName);
                MessageUtil.sendSuccess(admin, "Частицы активированы для §e" + targetName);
                break;
            case "rainbow":
                admin.performCommand("fx rainbow " + targetName);
                MessageUtil.sendSuccess(admin, "Радуга активирована для §e" + targetName);
                break;
            case "nightvision":
                admin.performCommand("fx nightvision " + targetName);
                MessageUtil.sendSuccess(admin, "Ночное зрение переключено для §e" + targetName);
                break;
            case "size":
                admin.performCommand("fx size giant " + targetName);
                MessageUtil.sendSuccess(admin, "Размер изменен для §e" + targetName);
                break;
            case "nick":
                MessageUtil.sendInfo(admin, "Используйте: /fx nick <ник> " + targetName);
                break;
            case "skull":
                admin.performCommand("fx skull " + targetName);
                MessageUtil.sendSuccess(admin, "Получена голова §e" + targetName);
                break;
            case "invsee":
                admin.performCommand("fx invsee " + targetName);
                break;
            case "enderchest":
                admin.performCommand("fx enderchest " + targetName);
                break;
            case "pinfo":
                admin.performCommand("fx pinfo " + targetName);
                break;
            default:
                MessageUtil.sendError(admin, "Неизвестная команда!");
                break;
        }
    }
}
