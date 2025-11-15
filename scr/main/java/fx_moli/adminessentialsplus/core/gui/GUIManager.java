package fx_moli.adminessentialsplus.core.gui;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {
    private final AdminEssentialsPlus plugin;

    public GUIManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    // Создание красивого предмета для GUI
    public ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            if (lore.length > 0) {
                List<String> loreList = new ArrayList<>();
                for (String line : lore) {
                    loreList.add(line);
                }
                meta.setLore(loreList);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    // Создание предмета с количеством
    public ItemStack createItem(Material material, int amount, String name, String... lore) {
        ItemStack item = createItem(material, name, lore);
        item.setAmount(amount);
        return item;
    }

    // Заполнить пустые слоты стеклом
    public void fillEmptySlots(Inventory inv, Material glass) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, createItem(glass, "§r"));
            }
        }
    }

    // Создать рамку из стекла
    public void createBorder(Inventory inv, Material glass) {
        int size = inv.getSize();
        ItemStack borderItem = createItem(glass, "§r");

        // Верхняя и нижняя строки
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, borderItem);
            if (size > 9) {
                inv.setItem(size - 9 + i, borderItem);
            }
        }

        // Боковые стороны
        for (int i = 9; i < size - 9; i += 9) {
            inv.setItem(i, borderItem);
            inv.setItem(i + 8, borderItem);
        }
    }

    // Главное меню
    public void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§8§l⚡ §6§lAdmin Menu §8§l⚡");

        // Рамка
        createBorder(inv, Material.BLACK_STAINED_GLASS_PANE);

        // Управление игроками
        inv.setItem(10, createItem(Material.PLAYER_HEAD, "§6§lУправление игроками",
                "§7Управляйте игроками на сервере",
                "",
                "§e▸ Телепортация",
                "§e▸ Инвентарь",
                "§e▸ Статистика",
                "",
                "§aНажмите для открытия"));

        // Модерация
        inv.setItem(12, createItem(Material.DIAMOND_SWORD, "§c§lМодерация",
                "§7Инструменты модерации",
                "",
                "§e▸ Бан/Кик/Мут",
                "§e▸ Предупреждения",
                "§e▸ История",
                "",
                "§aНажмите для открытия"));

        // Управление миром
        inv.setItem(14, createItem(Material.GRASS_BLOCK, "§2§lУправление миром",
                "§7Настройки мира",
                "",
                "§e▸ Погода/Время",
                "§e▸ Настройки",
                "§e▸ Информация",
                "",
                "§aНажмите для открытия"));

        // Предметы
        inv.setItem(16, createItem(Material.CHEST, "§e§lПредметы",
                "§7Управление предметами",
                "",
                "§e▸ Выдать предметы",
                "§e▸ Наборы",
                "§e▸ Зачарования",
                "",
                "§aНажмите для открытия"));

        // Развлечения
        inv.setItem(28, createItem(Material.FIREWORK_ROCKET, "§d§lРазвлечения",
                "§7Веселые команды",
                "",
                "§e▸ Запуск игроков",
                "§e▸ Эффекты",
                "§e▸ Фейерверки",
                "",
                "§aНажмите для открытия"));

        // Телепортация
        inv.setItem(30, createItem(Material.ENDER_PEARL, "§b§lТелепортация",
                "§7Быстрая телепортация",
                "",
                "§e▸ К игрокам",
                "§e▸ Варпы",
                "§e▸ Координаты",
                "",
                "§aНажмите для открытия"));

        // AI Помощник
        inv.setItem(32, createItem(Material.ENCHANTED_BOOK, "§5§lAI Помощник",
                "§7Искусственный интеллект",
                "",
                "§e▸ Модерация чата",
                "§e▸ Анализ жалоб",
                "§e▸ Диагностика",
                "",
                "§aНажмите для открытия"));

        // Настройки
        inv.setItem(34, createItem(Material.COMPARATOR, "§7§lНастройки",
                "§7Настройки плагина",
                "",
                "§e▸ Конфигурация",
                "§e▸ Права доступа",
                "§e▸ База данных",
                "",
                "§aНажмите для открытия"));

        // Статистика сервера
        inv.setItem(49, createItem(Material.BOOK, "§a§lСтатистика сервера",
                "§7Информация о сервере",
                "",
                "§e▸ TPS: §a" + String.format("%.2f", Bukkit.getTPS()[0]),
                "§e▸ Игроков: §a" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(),
                "§e▸ Память: §a" + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024 + "MB",
                "",
                "§aНажмите для подробностей"));

        player.openInventory(inv);
    }

    // Меню управления игроками
    public void openPlayerManagementMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6§lУправление игроками");

        createBorder(inv, Material.ORANGE_STAINED_GLASS_PANE);

        int slot = 10;
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (slot >= 44) break;
            if (slot % 9 == 0 || slot % 9 == 8) slot += 2;

            List<String> lore = new ArrayList<>();
            lore.add("§7UUID: §e" + target.getUniqueId().toString().substring(0, 8) + "...");
            lore.add("§7Пинг: §e" + target.getPing() + "ms");
            lore.add("§7Здоровье: §c" + String.format("%.1f", target.getHealth()) + "§7/§c20.0");
            lore.add("§7Режим: §e" + target.getGameMode().name());
            lore.add("");
            lore.add("§aЛКМ §7- Телепорт к игроку");
            lore.add("§aПКМ §7- Открыть меню действий");
            lore.add("§aShift+ЛКМ §7- Инвентарь");

            inv.setItem(slot, createItem(Material.PLAYER_HEAD, "§6" + target.getName(), lore.toArray(new String[0])));
            slot++;
        }

        // Кнопка назад
        inv.setItem(49, createItem(Material.ARROW, "§cНазад", "§7Вернуться в главное меню"));

        player.openInventory(inv);
    }

    // Меню действий над игроком
    public void openPlayerActionsMenu(Player admin, Player target) {
        Inventory inv = Bukkit.createInventory(null, 45, "§6§lДействия: §e" + target.getName());

        createBorder(inv, Material.GRAY_STAINED_GLASS_PANE);

        // Телепортация
        inv.setItem(10, createItem(Material.ENDER_PEARL, "§b§lТелепортация",
                "§7Телепортироваться к игроку",
                "",
                "§aНажмите для телепортации"));

        // Инвентарь
        inv.setItem(11, createItem(Material.CHEST, "§e§lИнвентарь",
                "§7Просмотреть инвентарь",
                "",
                "§aНажмите для просмотра"));

        // Лечение
        inv.setItem(12, createItem(Material.GOLDEN_APPLE, "§c§lВылечить",
                "§7Восстановить здоровье",
                "",
                "§aНажмите для лечения"));

        // Накормить
        inv.setItem(13, createItem(Material.COOKED_BEEF, "§6§lНакормить",
                "§7Восстановить голод",
                "",
                "§aНажмите для кормления"));

        // Полет
        inv.setItem(14, createItem(Material.FEATHER, "§f§lПолет",
                "§7Включить/выключить полет",
                "",
                "§7Статус: " + (target.getAllowFlight() ? "§aВключен" : "§cВыключен"),
                "",
                "§aНажмите для переключения"));

        // Режим бога
        inv.setItem(15, createItem(Material.TOTEM_OF_UNDYING, "§e§lРежим бога",
                "§7Бессмертие",
                "",
                "§aНажмите для переключения"));

        // Заморозить
        inv.setItem(16, createItem(Material.ICE, "§b§lЗаморозить",
                "§7Заблокировать движение",
                "",
                "§aНажмите для заморозки"));

        // Кик
        inv.setItem(19, createItem(Material.IRON_DOOR, "§6§lКикнуть",
                "§7Выгнать с сервера",
                "",
                "§cОсторожно!",
                "§aНажмите для кика"));

        // Бан
        inv.setItem(20, createItem(Material.BARRIER, "§c§lЗабанить",
                "§7Заблокировать доступ",
                "",
                "§cОсторожно!",
                "§aНажмите для бана"));

        // Мут
        inv.setItem(21, createItem(Material.PAPER, "§7§lЗамутить",
                "§7Заблокировать чат",
                "",
                "§aНажмите для мута"));

        // Предупреждение
        inv.setItem(22, createItem(Material.WRITABLE_BOOK, "§e§lПредупредить",
                "§7Выдать предупреждение",
                "",
                "§aНажмите для варна"));

        // Запустить
        inv.setItem(28, createItem(Material.FIREWORK_ROCKET, "§d§lЗапустить",
                "§7Подбросить в воздух",
                "",
                "§aНажмите для запуска"));

        // Поджечь
        inv.setItem(29, createItem(Material.FLINT_AND_STEEL, "§6§lПоджечь",
                "§7Поджечь игрока",
                "",
                "§aНажмите для поджога"));

        // Ударить молнией
        inv.setItem(30, createItem(Material.LIGHTNING_ROD, "§e§lМолния",
                "§7Ударить молнией",
                "",
                "§aНажмите для удара"));

        // Очистить инвентарь
        inv.setItem(31, createItem(Material.LAVA_BUCKET, "§c§lОчистить инвентарь",
                "§7Удалить все предметы",
                "",
                "§cОсторожно!",
                "§aНажмите для очистки"));

        // Назад
        inv.setItem(40, createItem(Material.ARROW, "§cНазад", "§7Вернуться назад"));

        admin.openInventory(inv);
    }

    // Меню модерации
    public void openModerationMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "§c§lМодерация");

        createBorder(inv, Material.RED_STAINED_GLASS_PANE);

        // Список банов
        inv.setItem(11, createItem(Material.BARRIER, "§c§lСписок банов",
                "§7Просмотр забаненных игроков",
                "",
                "§e▸ Активные баны",
                "§e▸ История банов",
                "",
                "§aНажмите для просмотра"));

        // Список мутов
        inv.setItem(13, createItem(Material.PAPER, "§7§lСписок мутов",
                "§7Просмотр замученных игроков",
                "",
                "§e▸ Активные муты",
                "§e▸ История мутов",
                "",
                "§aНажмите для просмотра"));

        // Предупреждения
        inv.setItem(15, createItem(Material.WRITABLE_BOOK, "§e§lПредупреждения",
                "§7Система предупреждений",
                "",
                "§e▸ Активные варны",
                "§e▸ История варнов",
                "",
                "§aНажмите для просмотра"));

        // Логи чата
        inv.setItem(20, createItem(Material.BOOK, "§b§lЛоги чата",
                "§7История сообщений",
                "",
                "§e▸ Последние сообщения",
                "§e▸ Поиск по игроку",
                "",
                "§aНажмите для просмотра"));

        // Запрещенные предметы
        inv.setItem(22, createItem(Material.TNT, "§c§lЗапрещенные предметы",
                "§7Управление баном предметов",
                "",
                "§e▸ Список предметов",
                "§e▸ Добавить/Удалить",
                "",
                "§aНажмите для управления"));

        // AI Модерация
        inv.setItem(24, createItem(Material.ENCHANTED_BOOK, "§5§lAI Модерация",
                "§7Автоматическая модерация",
                "",
                "§e▸ Настройки фильтра",
                "§e▸ Статистика",
                "",
                "§aНажмите для настройки"));

        // Назад
        inv.setItem(40, createItem(Material.ARROW, "§cНазад", "§7Вернуться в главное меню"));

        player.openInventory(inv);
    }

    // Меню предметов
    public void openItemsMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§e§lПредметы");

        createBorder(inv, Material.YELLOW_STAINED_GLASS_PANE);

        // Популярные предметы
        inv.setItem(10, createItem(Material.DIAMOND, 64, "§b§lАлмазы x64",
                "§7Выдать алмазы",
                "",
                "§aНажмите для получения"));

        inv.setItem(11, createItem(Material.EMERALD, 64, "§a§lИзумруды x64",
                "§7Выдать изумруды",
                "",
                "§aНажмите для получения"));

        inv.setItem(12, createItem(Material.GOLD_INGOT, 64, "§6§lЗолото x64",
                "§7Выдать золото",
                "",
                "§aНажмите для получения"));

        inv.setItem(13, createItem(Material.IRON_INGOT, 64, "§7§lЖелезо x64",
                "§7Выдать железо",
                "",
                "§aНажмите для получения"));

        inv.setItem(14, createItem(Material.NETHERITE_INGOT, 64, "§4§lНезерит x64",
                "§7Выдать незерит",
                "",
                "§aНажмите для получения"));

        // Инструменты
        inv.setItem(19, createItem(Material.DIAMOND_PICKAXE, "§b§lАлмазная кирка",
                "§7Выдать кирку",
                "",
                "§aНажмите для получения"));

        inv.setItem(20, createItem(Material.DIAMOND_AXE, "§b§lАлмазный топор",
                "§7Выдать топор",
                "",
                "§aНажмите для получения"));

        inv.setItem(21, createItem(Material.DIAMOND_SWORD, "§b§lАлмазный меч",
                "§7Выдать меч",
                "",
                "§aНажмите для получения"));

        inv.setItem(22, createItem(Material.BOW, "§6§lЛук",
                "§7Выдать лук",
                "",
                "§aНажмите для получения"));

        // Броня
        inv.setItem(28, createItem(Material.DIAMOND_HELMET, "§b§lАлмазный шлем",
                "§7Выдать шлем",
                "",
                "§aНажмите для получения"));

        inv.setItem(29, createItem(Material.DIAMOND_CHESTPLATE, "§b§lАлмазный нагрудник",
                "§7Выдать нагрудник",
                "",
                "§aНажмите для получения"));

        inv.setItem(30, createItem(Material.DIAMOND_LEGGINGS, "§b§lАлмазные поножи",
                "§7Выдать поножи",
                "",
                "§aНажмите для получения"));

        inv.setItem(31, createItem(Material.DIAMOND_BOOTS, "§b§lАлмазные ботинки",
                "§7Выдать ботинки",
                "",
                "§aНажмите для получения"));

        // Еда
        inv.setItem(37, createItem(Material.COOKED_BEEF, 64, "§6§lЖареная говядина x64",
                "§7Выдать еду",
                "",
                "§aНажмите для получения"));

        inv.setItem(38, createItem(Material.GOLDEN_APPLE, 16, "§6§lЗолотые яблоки x16",
                "§7Выдать золотые яблоки",
                "",
                "§aНажмите для получения"));

        inv.setItem(39, createItem(Material.ENCHANTED_GOLDEN_APPLE, 4, "§5§lЗачарованные яблоки x4",
                "§7Выдать зачарованные яблоки",
                "",
                "§aНажмите для получения"));

        // Наборы
        inv.setItem(43, createItem(Material.CHEST, "§e§lНаборы предметов",
                "§7Готовые наборы",
                "",
                "§e▸ Стартовый набор",
                "§e▸ Набор инструментов",
                "§e▸ Набор брони",
                "",
                "§aНажмите для просмотра"));

        // Назад
        inv.setItem(49, createItem(Material.ARROW, "§cНазад", "§7Вернуться в главное меню"));

        player.openInventory(inv);
    }
}
