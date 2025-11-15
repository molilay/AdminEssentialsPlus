package fx_moli.adminessentialsplus.core.commands;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FXCommand implements CommandExecutor, TabCompleter {
    private final AdminEssentialsPlus plugin;

    public FXCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        String subCommand = args[0].toLowerCase();
        String[] subArgs = args.length > 1 ? Arrays.copyOfRange(args, 1, args.length) : new String[0];

        // Перенаправление на соответствующие команды
        switch (subCommand) {
            // Управление игроками
            case "fly":
                return plugin.getServer().dispatchCommand(sender, "fly " + String.join(" ", subArgs));
            case "vanish":
            case "v":
                return plugin.getServer().dispatchCommand(sender, "vanish " + String.join(" ", subArgs));
            case "speed":
                return plugin.getServer().dispatchCommand(sender, "speed " + String.join(" ", subArgs));
            case "heal":
                return plugin.getServer().dispatchCommand(sender, "heal " + String.join(" ", subArgs));
            case "feed":
                return plugin.getServer().dispatchCommand(sender, "feed " + String.join(" ", subArgs));
            case "clear":
                return plugin.getServer().dispatchCommand(sender, "clear " + String.join(" ", subArgs));
            case "god":
                return plugin.getServer().dispatchCommand(sender, "god " + String.join(" ", subArgs));
            
            // Телепортация
            case "tp":
            case "teleport":
                return plugin.getServer().dispatchCommand(sender, "tp " + String.join(" ", subArgs));
            case "tphere":
                return plugin.getServer().dispatchCommand(sender, "tphere " + String.join(" ", subArgs));
            case "back":
                return plugin.getServer().dispatchCommand(sender, "back " + String.join(" ", subArgs));
            case "spawn":
                return plugin.getServer().dispatchCommand(sender, "spawn " + String.join(" ", subArgs));
            case "setspawn":
                return plugin.getServer().dispatchCommand(sender, "setspawn " + String.join(" ", subArgs));
            
            // Модерация
            case "kick":
                return plugin.getServer().dispatchCommand(sender, "kick " + String.join(" ", subArgs));
            case "ban":
                return plugin.getServer().dispatchCommand(sender, "ban " + String.join(" ", subArgs));
            case "unban":
                return plugin.getServer().dispatchCommand(sender, "unban " + String.join(" ", subArgs));
            case "warn":
                return plugin.getServer().dispatchCommand(sender, "warn " + String.join(" ", subArgs));
            case "mute":
                return plugin.getServer().dispatchCommand(sender, "mute " + String.join(" ", subArgs));
            
            // Управление сервером
            case "weather":
                return plugin.getServer().dispatchCommand(sender, "weather " + String.join(" ", subArgs));
            case "time":
                return plugin.getServer().dispatchCommand(sender, "time " + String.join(" ", subArgs));
            case "gamemode":
            case "gm":
                return plugin.getServer().dispatchCommand(sender, "gamemode " + String.join(" ", subArgs));
            
            // Просмотр
            case "invsee":
                return plugin.getServer().dispatchCommand(sender, "invsee " + String.join(" ", subArgs));
            case "enderchest":
            case "ec":
                return plugin.getServer().dispatchCommand(sender, "enderchest " + String.join(" ", subArgs));
            case "inspect":
                return plugin.getServer().dispatchCommand(sender, "inspect " + String.join(" ", subArgs));
            case "watch":
                return plugin.getServer().dispatchCommand(sender, "watch " + String.join(" ", subArgs));
            
            // Строительство
            case "pos1":
                return plugin.getServer().dispatchCommand(sender, "pos1 " + String.join(" ", subArgs));
            case "pos2":
                return plugin.getServer().dispatchCommand(sender, "pos2 " + String.join(" ", subArgs));
            case "copy":
                return plugin.getServer().dispatchCommand(sender, "copy " + String.join(" ", subArgs));
            case "paste":
                return plugin.getServer().dispatchCommand(sender, "paste " + String.join(" ", subArgs));
            case "replacearea":
                return plugin.getServer().dispatchCommand(sender, "replacearea " + String.join(" ", subArgs));
            case "forestgen":
                return plugin.getServer().dispatchCommand(sender, "forestgen " + String.join(" ", subArgs));
            
            // Другое
            case "stats":
            case "serverstats":
                return plugin.getServer().dispatchCommand(sender, "serverstats " + String.join(" ", subArgs));
            case "sc":
            case "staffchat":
                return plugin.getServer().dispatchCommand(sender, "staffchat " + String.join(" ", subArgs));
            case "ai":
                return plugin.getServer().dispatchCommand(sender, "ai " + String.join(" ", subArgs));
            case "lookup":
                return plugin.getServer().dispatchCommand(sender, "lookup " + String.join(" ", subArgs));
            case "banitem":
                return plugin.getServer().dispatchCommand(sender, "banitem " + String.join(" ", subArgs));
            case "restoreinv":
                return plugin.getServer().dispatchCommand(sender, "restoreinv " + String.join(" ", subArgs));
            
            // Новые команды
            case "home":
                return plugin.getServer().dispatchCommand(sender, "home " + String.join(" ", subArgs));
            case "sethome":
                return plugin.getServer().dispatchCommand(sender, "sethome " + String.join(" ", subArgs));
            case "delhome":
                return plugin.getServer().dispatchCommand(sender, "delhome " + String.join(" ", subArgs));
            case "warp":
                return plugin.getServer().dispatchCommand(sender, "warp " + String.join(" ", subArgs));
            case "setwarp":
                return plugin.getServer().dispatchCommand(sender, "setwarp " + String.join(" ", subArgs));
            case "delwarp":
                return plugin.getServer().dispatchCommand(sender, "delwarp " + String.join(" ", subArgs));
            case "repair":
                return plugin.getServer().dispatchCommand(sender, "repair " + String.join(" ", subArgs));
            case "workbench":
            case "wb":
                return plugin.getServer().dispatchCommand(sender, "workbench " + String.join(" ", subArgs));
            case "enchant":
                return plugin.getServer().dispatchCommand(sender, "enchant " + String.join(" ", subArgs));
            case "item":
                return plugin.getServer().dispatchCommand(sender, "item " + String.join(" ", subArgs));
            case "kit":
                return plugin.getServer().dispatchCommand(sender, "kit " + String.join(" ", subArgs));
            case "top":
                return plugin.getServer().dispatchCommand(sender, "top " + String.join(" ", subArgs));
            case "jump":
                return plugin.getServer().dispatchCommand(sender, "jump " + String.join(" ", subArgs));
            case "near":
                return plugin.getServer().dispatchCommand(sender, "near " + String.join(" ", subArgs));
            case "tppos":
                return plugin.getServer().dispatchCommand(sender, "tppos " + String.join(" ", subArgs));
            case "burn":
                return plugin.getServer().dispatchCommand(sender, "burn " + String.join(" ", subArgs));
            case "extinguish":
            case "ext":
                return plugin.getServer().dispatchCommand(sender, "extinguish " + String.join(" ", subArgs));
            case "smite":
                return plugin.getServer().dispatchCommand(sender, "smite " + String.join(" ", subArgs));
            case "exp":
            case "xp":
                return plugin.getServer().dispatchCommand(sender, "exp " + String.join(" ", subArgs));
            case "flyspeed":
                return plugin.getServer().dispatchCommand(sender, "flyspeed " + String.join(" ", subArgs));
            case "walkspeed":
                return plugin.getServer().dispatchCommand(sender, "walkspeed " + String.join(" ", subArgs));
            
            // Новые команды
            case "ping":
                return plugin.getServer().dispatchCommand(sender, "ping " + String.join(" ", subArgs));
            case "hat":
                return plugin.getServer().dispatchCommand(sender, "hat " + String.join(" ", subArgs));
            case "nick":
                return plugin.getServer().dispatchCommand(sender, "nick " + String.join(" ", subArgs));
            case "suicide":
                return plugin.getServer().dispatchCommand(sender, "suicide " + String.join(" ", subArgs));
            case "launch":
                return plugin.getServer().dispatchCommand(sender, "launch " + String.join(" ", subArgs));
            case "freeze":
                return plugin.getServer().dispatchCommand(sender, "freeze " + String.join(" ", subArgs));
            case "explode":
                return plugin.getServer().dispatchCommand(sender, "explode " + String.join(" ", subArgs));
            case "broadcast":
            case "bc":
                return plugin.getServer().dispatchCommand(sender, "broadcast " + String.join(" ", subArgs));
            case "list":
                return plugin.getServer().dispatchCommand(sender, "list " + String.join(" ", subArgs));
            case "sudo":
                return plugin.getServer().dispatchCommand(sender, "sudo " + String.join(" ", subArgs));
            case "worldinfo":
            case "wi":
                return plugin.getServer().dispatchCommand(sender, "worldinfo " + String.join(" ", subArgs));
            
            // Кастомные команды
            case "size":
                return plugin.getServer().dispatchCommand(sender, "size " + String.join(" ", subArgs));
            case "healall":
                return plugin.getServer().dispatchCommand(sender, "healall " + String.join(" ", subArgs));
            case "glow":
                return plugin.getServer().dispatchCommand(sender, "glow " + String.join(" ", subArgs));
            case "clearlag":
                return plugin.getServer().dispatchCommand(sender, "clearlag " + String.join(" ", subArgs));
            case "trail":
                return plugin.getServer().dispatchCommand(sender, "trail " + String.join(" ", subArgs));
            case "rocket":
                return plugin.getServer().dispatchCommand(sender, "rocket " + String.join(" ", subArgs));
            case "stack":
                return plugin.getServer().dispatchCommand(sender, "stack " + String.join(" ", subArgs));
            case "nightvision":
            case "nv":
                return plugin.getServer().dispatchCommand(sender, "nightvision " + String.join(" ", subArgs));
            case "skull":
                return plugin.getServer().dispatchCommand(sender, "skull " + String.join(" ", subArgs));
            case "rainbow":
                return plugin.getServer().dispatchCommand(sender, "rainbow " + String.join(" ", subArgs));
            case "slap":
                return plugin.getServer().dispatchCommand(sender, "slap " + String.join(" ", subArgs));
            case "particle":
                return plugin.getServer().dispatchCommand(sender, "particle " + String.join(" ", subArgs));
            
            // Социальные команды
            case "hug":
                return plugin.getServer().dispatchCommand(sender, "hug " + String.join(" ", subArgs));
            case "highfive":
                return plugin.getServer().dispatchCommand(sender, "highfive " + String.join(" ", subArgs));
            case "me":
                return plugin.getServer().dispatchCommand(sender, "me " + String.join(" ", subArgs));
            case "coinflip":
            case "cf":
                return plugin.getServer().dispatchCommand(sender, "coinflip " + String.join(" ", subArgs));
            
            // Утилиты
            case "trash":
                return plugin.getServer().dispatchCommand(sender, "trash " + String.join(" ", subArgs));
            case "discord":
                return plugin.getServer().dispatchCommand(sender, "discord " + String.join(" ", subArgs));
            case "rules":
                return plugin.getServer().dispatchCommand(sender, "rules " + String.join(" ", subArgs));
            
            // Новые команды v1.2.0
            case "qa":
            case "quickactions":
                return plugin.getServer().dispatchCommand(sender, "quickactions " + String.join(" ", subArgs));
            case "mass":
                return plugin.getServer().dispatchCommand(sender, "mass " + String.join(" ", subArgs));
            case "pinfo":
            case "playerinfo":
                return plugin.getServer().dispatchCommand(sender, "playerinfo " + String.join(" ", subArgs));
            case "menu":
            case "gui":
                return plugin.getServer().dispatchCommand(sender, "menu " + String.join(" ", subArgs));
            
            // Новые команды модерации v1.3.0
            case "history":
                return plugin.getServer().dispatchCommand(sender, "history " + String.join(" ", subArgs));
            case "checkip":
                return plugin.getServer().dispatchCommand(sender, "checkip " + String.join(" ", subArgs));
            case "tempban":
                return plugin.getServer().dispatchCommand(sender, "tempban " + String.join(" ", subArgs));
            case "clearchat":
            case "cc":
                return plugin.getServer().dispatchCommand(sender, "clearchat " + String.join(" ", subArgs));
            case "note":
                return plugin.getServer().dispatchCommand(sender, "note " + String.join(" ", subArgs));
            
            // Помощь
            case "help":
            case "?":
                sendHelp(sender);
                return true;
            
            default:
                sender.sendMessage(ChatColor.RED + "Неизвестная команда! Используйте /fx help для списка команд.");
                return true;
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("§6§l    AdminEssentialsPlus v1.2.0");
        sender.sendMessage("§7    Мощный плагин администрирования");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("");
        sender.sendMessage("§e§l⚡ Быстрые команды v1.2.0:");
        sender.sendMessage("§e/fx qa <действие> §7- Быстрые действия");
        sender.sendMessage("§e/fx mass <действие> §7- Массовые операции");
        sender.sendMessage("§e/fx pinfo <игрок> §7- Информация о игроке");
        sender.sendMessage("§e/fx menu §7- Открыть GUI меню");
        sender.sendMessage("");
        
        if (sender.hasPermission("adminessentialsplus.fly")) {
            sender.sendMessage(ChatColor.YELLOW + "/fx fly [игрок] " + ChatColor.WHITE + "- Полет");
            sender.sendMessage(ChatColor.YELLOW + "/fx vanish " + ChatColor.WHITE + "- Невидимость");
            sender.sendMessage(ChatColor.YELLOW + "/fx speed <скорость> [игрок] " + ChatColor.WHITE + "- Скорость");
            sender.sendMessage(ChatColor.YELLOW + "/fx heal [игрок] " + ChatColor.WHITE + "- Лечение");
            sender.sendMessage(ChatColor.YELLOW + "/fx feed [игрок] " + ChatColor.WHITE + "- Насыщение");
            sender.sendMessage(ChatColor.YELLOW + "/fx clear [игрок] " + ChatColor.WHITE + "- Очистка инвентаря");
            sender.sendMessage(ChatColor.YELLOW + "/fx god [игрок] " + ChatColor.WHITE + "- Режим бога");
        }
        
        if (sender.hasPermission("adminessentialsplus.tp")) {
            sender.sendMessage(ChatColor.YELLOW + "/fx tp <игрок|координаты> " + ChatColor.WHITE + "- Телепорт");
            sender.sendMessage(ChatColor.YELLOW + "/fx tphere <игрок> " + ChatColor.WHITE + "- Телепорт к себе");
            sender.sendMessage(ChatColor.YELLOW + "/fx back " + ChatColor.WHITE + "- Вернуться назад");
            sender.sendMessage(ChatColor.YELLOW + "/fx spawn " + ChatColor.WHITE + "- На спавн");
            sender.sendMessage(ChatColor.YELLOW + "/fx home [название] " + ChatColor.WHITE + "- Дом");
            sender.sendMessage(ChatColor.YELLOW + "/fx warp <название> " + ChatColor.WHITE + "- Варп");
        }
        
        if (sender.hasPermission("adminessentialsplus.kick")) {
            sender.sendMessage(ChatColor.YELLOW + "/fx kick <игрок> [причина] " + ChatColor.WHITE + "- Кик");
            sender.sendMessage(ChatColor.YELLOW + "/fx ban <игрок> [причина] " + ChatColor.WHITE + "- Бан");
            sender.sendMessage(ChatColor.YELLOW + "/fx unban <игрок> " + ChatColor.WHITE + "- Разбан");
            sender.sendMessage(ChatColor.YELLOW + "/fx warn <игрок> <причина> " + ChatColor.WHITE + "- Предупреждение");
            sender.sendMessage(ChatColor.YELLOW + "/fx mute <игрок> [время] [причина] " + ChatColor.WHITE + "- Мут");
        }
        
        if (sender.hasPermission("adminessentialsplus.weather")) {
            sender.sendMessage(ChatColor.YELLOW + "/fx weather <clear|rain|storm> " + ChatColor.WHITE + "- Погода");
            sender.sendMessage(ChatColor.YELLOW + "/fx time <set|add> <значение> " + ChatColor.WHITE + "- Время");
            sender.sendMessage(ChatColor.YELLOW + "/fx gamemode <режим> [игрок] " + ChatColor.WHITE + "- Режим игры");
        }
        
        sender.sendMessage(ChatColor.YELLOW + "/fx invsee <игрок> " + ChatColor.WHITE + "- Просмотр инвентаря");
        sender.sendMessage(ChatColor.YELLOW + "/fx repair [all|hand] " + ChatColor.WHITE + "- Починка");
        sender.sendMessage(ChatColor.YELLOW + "/fx workbench " + ChatColor.WHITE + "- Верстак");
        sender.sendMessage(ChatColor.YELLOW + "/fx item <предмет> [количество] " + ChatColor.WHITE + "- Выдать предмет");
        sender.sendMessage(ChatColor.YELLOW + "/fx exp <количество> [игрок] " + ChatColor.WHITE + "- Опыт");
        sender.sendMessage(ChatColor.YELLOW + "/fx top " + ChatColor.WHITE + "- Наверх");
        sender.sendMessage(ChatColor.YELLOW + "/fx jump " + ChatColor.WHITE + "- Прыжок вперед");
        sender.sendMessage(ChatColor.YELLOW + "/fx near " + ChatColor.WHITE + "- Ближайшие игроки");
        
        sender.sendMessage("");
        sender.sendMessage("§6§l▸ Кастомные команды:");
        sender.sendMessage(ChatColor.YELLOW + "/fx size <размер> [игрок] " + ChatColor.WHITE + "- Изменить размер");
        sender.sendMessage(ChatColor.YELLOW + "/fx healall " + ChatColor.WHITE + "- Вылечить всех");
        sender.sendMessage(ChatColor.YELLOW + "/fx glow [игрок] " + ChatColor.WHITE + "- Эффект свечения");
        sender.sendMessage(ChatColor.YELLOW + "/fx clearlag " + ChatColor.WHITE + "- Очистить лаги");
        sender.sendMessage(ChatColor.YELLOW + "/fx trail <тип> " + ChatColor.WHITE + "- След из частиц");
        sender.sendMessage(ChatColor.YELLOW + "/fx rocket [игрок] " + ChatColor.WHITE + "- Запустить ракету");
        sender.sendMessage(ChatColor.YELLOW + "/fx nightvision " + ChatColor.WHITE + "- Ночное зрение");
        sender.sendMessage(ChatColor.YELLOW + "/fx skull <игрок> " + ChatColor.WHITE + "- Голова игрока");
        sender.sendMessage(ChatColor.YELLOW + "/fx rainbow " + ChatColor.WHITE + "- Радужная броня");
        sender.sendMessage(ChatColor.YELLOW + "/fx slap <игрок> " + ChatColor.WHITE + "- Шлепнуть");
        sender.sendMessage(ChatColor.YELLOW + "/fx particle <тип> " + ChatColor.WHITE + "- Частицы");
        
        sender.sendMessage("");
        sender.sendMessage("§6§l▸ Развлечения:");
        sender.sendMessage(ChatColor.YELLOW + "/fx ping [игрок] " + ChatColor.WHITE + "- Проверить пинг");
        sender.sendMessage(ChatColor.YELLOW + "/fx hat " + ChatColor.WHITE + "- Надеть предмет на голову");
        sender.sendMessage(ChatColor.YELLOW + "/fx nick <ник> " + ChatColor.WHITE + "- Изменить ник");
        sender.sendMessage(ChatColor.YELLOW + "/fx launch <игрок> " + ChatColor.WHITE + "- Запустить в воздух");
        sender.sendMessage(ChatColor.YELLOW + "/fx freeze <игрок> " + ChatColor.WHITE + "- Заморозить игрока");
        sender.sendMessage(ChatColor.YELLOW + "/fx hug <игрок> " + ChatColor.WHITE + "- Обнять");
        sender.sendMessage(ChatColor.YELLOW + "/fx coinflip " + ChatColor.WHITE + "- Подбросить монетку");
        
        sender.sendMessage("");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("§7Все команды работают через §e/fx §7префикс");
        sender.sendMessage("§7Используйте §e/fx help §7для полного списка");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            List<String> commands = Arrays.asList(
                // Основные
                "fly", "vanish", "v", "speed", "heal", "feed", "clear", "god",
                "tp", "teleport", "tphere", "back", "spawn", "setspawn",
                "home", "sethome", "delhome", "warp", "setwarp", "delwarp",
                "kick", "ban", "unban", "warn", "mute",
                "weather", "time", "gamemode", "gm",
                "invsee", "enderchest", "ec", "inspect", "watch",
                "repair", "workbench", "wb", "enchant", "item", "kit",
                "top", "jump", "near", "tppos", "burn", "extinguish", "ext", "smite",
                "exp", "xp", "flyspeed", "walkspeed",
                "stats", "serverstats", "sc", "staffchat", "ai", "lookup",
                "pos1", "pos2", "copy", "paste", "replacearea", "forestgen",
                "ping", "hat", "nick", "suicide", "launch", "freeze", "explode",
                "broadcast", "bc", "list", "sudo", "worldinfo", "wi",
                // Кастомные
                "size", "healall", "glow", "clearlag", "trail", "rocket", "stack",
                "nightvision", "nv", "skull", "rainbow", "slap", "particle",
                // Социальные
                "hug", "highfive", "me", "coinflip", "cf",
                // Утилиты
                "trash", "discord", "rules",
                // Новые v1.2.0
                "qa", "quickactions", "mass", "pinfo", "playerinfo", "menu", "gui",
                "help", "?"
            );
            
            String input = args[0].toLowerCase();
            completions.addAll(commands.stream()
                .filter(cmd -> cmd.startsWith(input))
                .collect(Collectors.toList()));
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            switch (subCommand) {
                case "tp":
                case "tphere":
                case "kick":
                case "ban":
                case "warn":
                case "mute":
                case "heal":
                case "feed":
                case "clear":
                case "god":
                case "fly":
                case "invsee":
                case "inspect":
                case "watch":
                case "gamemode":
                case "gm":
                case "exp":
                case "xp":
                    // Автодополнение имен игроков
                    completions.addAll(plugin.getServer().getOnlinePlayers().stream()
                        .map(Player::getName)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList()));
                    break;
                case "weather":
                    completions.addAll(Arrays.asList("clear", "rain", "storm"));
                    break;
                case "time":
                    completions.addAll(Arrays.asList("set", "add", "day", "night", "noon", "midnight"));
                    break;
            }
        }
        
        return completions.isEmpty() ? null : completions;
    }
}

