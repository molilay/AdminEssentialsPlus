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

    // Главное меню - выбор команды
    public void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 54, "§8§l⚡ §6§lВыбор команды §8§l⚡");

        createBorder(inv, Material.BLACK_STAINED_GLASS_PANE);

        // Категория: Управление игроками
        inv.setItem(10, createItem(Material.PLAYER_HEAD, "§6§lУправление игроками",
                "§7Команды управления",
                "",
                "§e▸ Heal - Вылечить",
                "§e▸ Feed - Накормить",
                "§e▸ Fly - Полет",
                "§e▸ God - Режим бога",
                "",
                "§aНажмите для выбора"));

        // Категория: Телепортация
        inv.setItem(11, createItem(Material.ENDER_PEARL, "§b§lТелепортация",
                "§7Команды телепортации",
                "",
                "§e▸ TP - Телепорт к игроку",
                "§e▸ TPHere - ТП к себе",
                "§e▸ Bring - Притянуть",
                "",
                "§aНажмите для выбора"));

        // Категория: Модерация
        inv.setItem(12, createItem(Material.DIAMOND_SWORD, "§c§lМодерация",
                "§7Команды модерации",
                "",
                "§e▸ Kick - Кикнуть",
                "§e▸ Ban - Забанить",
                "§e▸ Mute - Замутить",
                "§e▸ Warn - Предупредить",
                "",
                "§aНажмите для выбора"));

        // Категория: Развлечения
        inv.setItem(13, createItem(Material.FIREWORK_ROCKET, "§d§lРазвлечения",
                "§7Веселые команды",
                "",
                "§e▸ Launch - Запустить",
                "§e▸ Freeze - Заморозить",
                "§e▸ Explode - Взрыв",
                "§e▸ Slap - Шлепнуть",
                "",
                "§aНажмите для выбора"));

        // Категория: Эффекты
        inv.setItem(14, createItem(Material.POTION, "§5§lЭффекты",
                "§7Команды эффектов",
                "",
                "§e▸ Glow - Свечение",
                "§e▸ Trail - След",
                "§e▸ Particle - Частицы",
                "§e▸ Rainbow - Радуга",
                "",
                "§aНажмите для выбора"));

        // Категория: Изменение
        inv.setItem(15, createItem(Material.GOLDEN_APPLE, "§e§lИзменение",
                "§7Команды изменения",
                "",
                "§e▸ Size - Размер",
                "§e▸ Nick - Никнейм",
                "§e▸ Skull - Голова",
                "",
                "§aНажмите для выбора"));

        // Категория: Просмотр
        inv.setItem(16, createItem(Material.SPYGLASS, "§3§lПросмотр",
                "§7Команды просмотра",
                "",
                "§e▸ InvSee - Инвентарь",
                "§e▸ EnderChest - Эндер-сундук",
                "§e▸ PlayerInfo - Информация",
                "",
                "§aНажмите для выбора"));

        // Быстрые действия
        inv.setItem(28, createItem(Material.NETHER_STAR, "§a§lБыстрые действия",
                "§7Массовые команды",
                "",
                "§e▸ HealAll - Вылечить всех",
                "§e▸ FeedAll - Накормить всех",
                "§e▸ ClearLag - Очистить лаги",
                "",
                "§aНажмите для выбора"));

        // Список игроков
        inv.setItem(31, createItem(Material.COMPASS, "§6§lСписок игроков",
                "§7Выбрать игрока напрямую",
                "",
                "§7Игроков онлайн: §e" + Bukkit.getOnlinePlayers().size(),
                "",
                "§aНажмите для просмотра"));

        // Статистика
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

    // Меню выбора команды управления игроками
    public void openPlayerManagementCommands(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6§lУправление игроками");
        
        createBorder(inv, Material.ORANGE_STAINED_GLASS_PANE);
        
        inv.setItem(10, createItem(Material.GOLDEN_APPLE, "§c§lHeal",
                "§7Вылечить игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(11, createItem(Material.COOKED_BEEF, "§6§lFeed",
                "§7Накормить игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(12, createItem(Material.FEATHER, "§f§lFly",
                "§7Включить/выключить полет",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(13, createItem(Material.TOTEM_OF_UNDYING, "§e§lGod",
                "§7Режим бога",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(14, createItem(Material.ENDER_EYE, "§5§lVanish",
                "§7Невидимость",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(15, createItem(Material.LAVA_BUCKET, "§c§lClear",
                "§7Очистить инвентарь",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(16, createItem(Material.EXPERIENCE_BOTTLE, "§a§lExp",
                "§7Выдать опыт",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню выбора команды телепортации
    public void openTeleportCommands(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§b§lТелепортация");
        
        createBorder(inv, Material.CYAN_STAINED_GLASS_PANE);
        
        inv.setItem(11, createItem(Material.ENDER_PEARL, "§b§lTP",
                "§7Телепортироваться к игроку",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(13, createItem(Material.ENDER_EYE, "§b§lTPHere",
                "§7Телепортировать игрока к себе",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(15, createItem(Material.FISHING_ROD, "§b§lBring",
                "§7Притянуть игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню выбора команды модерации
    public void openModerationCommands(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§c§lМодерация");
        
        createBorder(inv, Material.RED_STAINED_GLASS_PANE);
        
        inv.setItem(10, createItem(Material.IRON_DOOR, "§6§lKick",
                "§7Кикнуть игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(11, createItem(Material.BARRIER, "§c§lBan",
                "§7Забанить игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(12, createItem(Material.PAPER, "§7§lMute",
                "§7Замутить игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(13, createItem(Material.WRITABLE_BOOK, "§e§lWarn",
                "§7Предупредить игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(14, createItem(Material.ICE, "§b§lFreeze",
                "§7Заморозить игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню выбора команды развлечений
    public void openFunCommands(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§d§lРазвлечения");
        
        createBorder(inv, Material.PINK_STAINED_GLASS_PANE);
        
        inv.setItem(10, createItem(Material.FIREWORK_ROCKET, "§d§lLaunch",
                "§7Запустить игрока в воздух",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(11, createItem(Material.ICE, "§b§lFreeze",
                "§7Заморозить игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(12, createItem(Material.TNT, "§c§lExplode",
                "§7Создать взрыв",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(13, createItem(Material.STICK, "§6§lSlap",
                "§7Шлепнуть игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(14, createItem(Material.ELYTRA, "§f§lRocket",
                "§7Запустить как ракету",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню выбора команды эффектов
    public void openEffectCommands(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§5§lЭффекты");
        
        createBorder(inv, Material.PURPLE_STAINED_GLASS_PANE);
        
        inv.setItem(10, createItem(Material.GLOWSTONE, "§e§lGlow",
                "§7Эффект свечения",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(11, createItem(Material.BLAZE_POWDER, "§6§lTrail",
                "§7След из частиц",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(12, createItem(Material.DRAGON_BREATH, "§d§lParticle",
                "§7Частицы вокруг",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(13, createItem(Material.LEATHER_CHESTPLATE, "§c§lRainbow",
                "§7Радужная броня",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(14, createItem(Material.ENDER_EYE, "§5§lNightVision",
                "§7Ночное зрение",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню выбора команды изменения
    public void openModifyCommands(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§e§lИзменение");
        
        createBorder(inv, Material.YELLOW_STAINED_GLASS_PANE);
        
        inv.setItem(11, createItem(Material.SLIME_BALL, "§a§lSize",
                "§7Изменить размер",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(13, createItem(Material.NAME_TAG, "§6§lNick",
                "§7Изменить никнейм",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(15, createItem(Material.PLAYER_HEAD, "§e§lSkull",
                "§7Получить голову игрока",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню выбора команды просмотра
    public void openViewCommands(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§3§lПросмотр");
        
        createBorder(inv, Material.LIGHT_BLUE_STAINED_GLASS_PANE);
        
        inv.setItem(11, createItem(Material.CHEST, "§e§lInvSee",
                "§7Просмотр инвентаря",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(13, createItem(Material.ENDER_CHEST, "§5§lEnderChest",
                "§7Просмотр эндер-сундука",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(15, createItem(Material.BOOK, "§6§lPlayerInfo",
                "§7Информация о игроке",
                "",
                "§aНажмите для выбора игрока"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню быстрых действий
    public void openQuickActionsMenu(Player admin) {
        Inventory inv = Bukkit.createInventory(null, 54, "§a§lБыстрые действия");
        
        createBorder(inv, Material.LIME_STAINED_GLASS_PANE);
        
        inv.setItem(10, createItem(Material.GOLDEN_APPLE, "§c§lHealAll",
                "§7Вылечить всех игроков",
                "",
                "§aНажмите для выполнения"));
        
        inv.setItem(11, createItem(Material.COOKED_BEEF, "§6§lFeedAll",
                "§7Накормить всех игроков",
                "",
                "§aНажмите для выполнения"));
        
        inv.setItem(12, createItem(Material.HOPPER, "§e§lClearLag",
                "§7Очистить лаги",
                "",
                "§aНажмите для выполнения"));
        
        inv.setItem(13, createItem(Material.ENDER_PEARL, "§b§lTPAll",
                "§7Телепортировать всех к себе",
                "",
                "§aНажмите для выполнения"));
        
        inv.setItem(14, createItem(Material.CLOCK, "§e§lDay",
                "§7Установить день",
                "",
                "§aНажмите для выполнения"));
        
        inv.setItem(15, createItem(Material.SUNFLOWER, "§6§lSun",
                "§7Убрать дождь",
                "",
                "§aНажмите для выполнения"));
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться в главное меню"));
        
        admin.openInventory(inv);
    }

    // Меню выбора игрока для команды
    public void openPlayerSelector(Player admin, String command) {
        Inventory inv = Bukkit.createInventory(null, 54, "§6§lВыбор игрока: §e" + command);
        
        createBorder(inv, Material.GRAY_STAINED_GLASS_PANE);
        
        int slot = 10;
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (slot >= 44) break;
            if (slot % 9 == 0 || slot % 9 == 8) slot += 2;
            
            List<String> lore = new ArrayList<>();
            lore.add("§7UUID: §e" + target.getUniqueId().toString().substring(0, 8) + "...");
            lore.add("§7Пинг: §e" + target.getPing() + "ms");
            lore.add("§7Здоровье: §c" + String.format("%.1f", target.getHealth()) + "§7/§c20.0");
            lore.add("");
            lore.add("§aНажмите для выполнения команды");
            
            inv.setItem(slot, createItem(Material.PLAYER_HEAD, "§6" + target.getName(), lore.toArray(new String[0])));
            slot++;
        }
        
        inv.setItem(49, createItem(Material.ARROW, "§cНазад",
                "§7Вернуться к выбору команды"));
        
        admin.openInventory(inv);
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
