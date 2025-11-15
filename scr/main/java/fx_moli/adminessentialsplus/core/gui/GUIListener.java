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

public class GUIListener implements Listener {
    private final AdminEssentialsPlus plugin;
    private final GUIManager guiManager;

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
        
        // Главное меню
        if (title.contains("Admin Menu")) {
            event.setCancelled(true);
            handleMainMenuClick(player, clicked);
        }
        // Управление игроками
        else if (title.contains("Управление игроками")) {
            event.setCancelled(true);
            handlePlayerManagementClick(player, clicked, event.isLeftClick(), event.isShiftClick());
        }
        // Действия над игроком
        else if (title.contains("Действия:")) {
            event.setCancelled(true);
            String targetName = title.split("§e")[1];
            Player target = Bukkit.getPlayer(targetName);
            if (target != null) {
                handlePlayerActionsClick(player, target, clicked);
            }
        }
        // Модерация
        else if (title.contains("Модерация")) {
            event.setCancelled(true);
            handleModerationClick(player, clicked);
        }
        // Предметы
        else if (title.contains("Предметы")) {
            event.setCancelled(true);
            handleItemsClick(player, clicked);
        }
    }

    private void handleMainMenuClick(Player player, ItemStack clicked) {
        String name = clicked.getItemMeta().getDisplayName();
        
        if (name.contains("Управление игроками")) {
            guiManager.openPlayerManagementMenu(player);
        }
        else if (name.contains("Модерация")) {
            guiManager.openModerationMenu(player);
        }
        else if (name.contains("Управление миром")) {
            MessageUtil.sendInfo(player, "Меню управления миром в разработке!");
            player.closeInventory();
        }
        else if (name.contains("Предметы")) {
            guiManager.openItemsMenu(player);
        }
        else if (name.contains("Развлечения")) {
            MessageUtil.sendInfo(player, "Меню развлечений в разработке!");
            player.closeInventory();
        }
        else if (name.contains("Телепортация")) {
            MessageUtil.sendInfo(player, "Меню телепортации в разработке!");
            player.closeInventory();
        }
        else if (name.contains("AI Помощник")) {
            MessageUtil.sendInfo(player, "Меню AI помощника в разработке!");
            player.closeInventory();
        }
        else if (name.contains("Настройки")) {
            MessageUtil.sendInfo(player, "Меню настроек в разработке!");
            player.closeInventory();
        }
        else if (name.contains("Статистика сервера")) {
            player.performCommand("serverstats");
            player.closeInventory();
        }
    }

    private void handlePlayerManagementClick(Player player, ItemStack clicked, boolean leftClick, boolean shiftClick) {
        String name = clicked.getItemMeta().getDisplayName();
        
        if (name.contains("Назад")) {
            guiManager.openMainMenu(player);
            return;
        }
        
        // Клик по голове игрока
        if (clicked.getType() == Material.PLAYER_HEAD) {
            String targetName = name.replace("§6", "");
            Player target = Bukkit.getPlayer(targetName);
            
            if (target != null) {
                if (shiftClick && leftClick) {
                    // Shift+ЛКМ - открыть инвентарь
                    player.performCommand("invsee " + targetName);
                    player.closeInventory();
                } else if (leftClick) {
                    // ЛКМ - телепорт
                    player.performCommand("tp " + targetName);
                    MessageUtil.sendSuccess(player, "Телепортация к §e" + targetName);
                    player.closeInventory();
                } else {
                    // ПКМ - меню действий
                    guiManager.openPlayerActionsMenu(player, target);
                }
            }
        }
    }

    private void handlePlayerActionsClick(Player admin, Player target, ItemStack clicked) {
        String name = clicked.getItemMeta().getDisplayName();
        
        if (name.contains("Назад")) {
            guiManager.openPlayerManagementMenu(admin);
            return;
        }
        
        admin.closeInventory();
        
        if (name.contains("Телепортация")) {
            admin.performCommand("tp " + target.getName());
        }
        else if (name.contains("Инвентарь")) {
            admin.performCommand("invsee " + target.getName());
        }
        else if (name.contains("Вылечить")) {
            admin.performCommand("heal " + target.getName());
        }
        else if (name.contains("Накормить")) {
            admin.performCommand("feed " + target.getName());
        }
        else if (name.contains("Полет")) {
            admin.performCommand("fly " + target.getName());
        }
        else if (name.contains("Режим бога")) {
            admin.performCommand("god " + target.getName());
        }
        else if (name.contains("Заморозить")) {
            admin.performCommand("freeze " + target.getName());
        }
        else if (name.contains("Кикнуть")) {
            admin.performCommand("kick " + target.getName() + " Кикнут через GUI");
        }
        else if (name.contains("Забанить")) {
            admin.performCommand("ban " + target.getName() + " Забанен через GUI");
        }
        else if (name.contains("Замутить")) {
            admin.performCommand("mute " + target.getName() + " 10 Замучен через GUI");
        }
        else if (name.contains("Предупредить")) {
            admin.performCommand("warn " + target.getName() + " Предупреждение через GUI");
        }
        else if (name.contains("Запустить")) {
            admin.performCommand("launch " + target.getName());
        }
        else if (name.contains("Поджечь")) {
            admin.performCommand("burn " + target.getName());
        }
        else if (name.contains("Молния")) {
            admin.performCommand("smite " + target.getName());
        }
        else if (name.contains("Очистить инвентарь")) {
            admin.performCommand("clear " + target.getName());
        }
    }

    private void handleModerationClick(Player player, ItemStack clicked) {
        String name = clicked.getItemMeta().getDisplayName();
        
        if (name.contains("Назад")) {
            guiManager.openMainMenu(player);
            return;
        }
        
        player.closeInventory();
        MessageUtil.sendInfo(player, "Эта функция в разработке!");
    }

    private void handleItemsClick(Player player, ItemStack clicked) {
        String name = clicked.getItemMeta().getDisplayName();
        
        if (name.contains("Назад")) {
            guiManager.openMainMenu(player);
            return;
        }
        
        if (name.contains("Наборы предметов")) {
            MessageUtil.sendInfo(player, "Меню наборов в разработке!");
            return;
        }
        
        // Выдача предметов
        Material material = clicked.getType();
        int amount = clicked.getAmount();
        
        ItemStack item = new ItemStack(material, amount);
        player.getInventory().addItem(item);
        
        MessageUtil.sendSuccess(player, "Вы получили §e" + amount + "x " + material.name());
        MessageUtil.sendActionBar(player, "§a✓ Предмет добавлен в инвентарь");
    }
}
