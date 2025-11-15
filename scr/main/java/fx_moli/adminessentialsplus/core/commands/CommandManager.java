package fx_moli.adminessentialsplus.core.commands;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.commands.inspector.InspectCommand;
import fx_moli.adminessentialsplus.core.commands.inspector.InvSeeCommand;
import fx_moli.adminessentialsplus.core.commands.inspector.EnderChestCommand;
import fx_moli.adminessentialsplus.core.commands.chat.StaffChatCommand;
import fx_moli.adminessentialsplus.core.commands.chat.MuteCommand;
import fx_moli.adminessentialsplus.core.commands.justice.BanItemCommand;
import fx_moli.adminessentialsplus.core.commands.justice.LookupCommand;
import fx_moli.adminessentialsplus.core.commands.justice.RestoreInvCommand;
import fx_moli.adminessentialsplus.core.commands.builder.PositionCommand;
import fx_moli.adminessentialsplus.core.commands.builder.CopyPasteCommand;
import fx_moli.adminessentialsplus.core.commands.builder.ReplaceAreaCommand;
import fx_moli.adminessentialsplus.core.commands.builder.ForestGenCommand;
import fx_moli.adminessentialsplus.core.commands.gameplay.EventCommand;
import fx_moli.adminessentialsplus.core.commands.gameplay.CustomRecipeCommand;
import fx_moli.adminessentialsplus.core.commands.gameplay.ServerStatsCommand;
import fx_moli.adminessentialsplus.core.commands.player.FlyCommand;
import fx_moli.adminessentialsplus.core.commands.player.VanishCommand;
import fx_moli.adminessentialsplus.core.commands.player.HealCommand;
import fx_moli.adminessentialsplus.core.commands.player.FeedCommand;
import fx_moli.adminessentialsplus.core.commands.player.ClearCommand;
import fx_moli.adminessentialsplus.core.commands.player.GodCommand;
import fx_moli.adminessentialsplus.core.commands.teleport.TeleportCommand;
import fx_moli.adminessentialsplus.core.commands.teleport.TeleportHereCommand;
import fx_moli.adminessentialsplus.core.commands.teleport.BackCommand;
import fx_moli.adminessentialsplus.core.commands.teleport.SpawnCommand;
import fx_moli.adminessentialsplus.core.commands.teleport.SetSpawnCommand;
import fx_moli.adminessentialsplus.core.commands.moderation.KickCommand;
import fx_moli.adminessentialsplus.core.commands.moderation.BanCommand;
import fx_moli.adminessentialsplus.core.commands.moderation.UnbanCommand;
import fx_moli.adminessentialsplus.core.commands.moderation.WarnCommand;
import fx_moli.adminessentialsplus.core.commands.server.WeatherCommand;
import fx_moli.adminessentialsplus.core.commands.server.TimeCommand;
import fx_moli.adminessentialsplus.core.commands.server.GamemodeCommand;
import fx_moli.adminessentialsplus.core.commands.player.HomeCommand;
import fx_moli.adminessentialsplus.core.commands.player.SetHomeCommand;
import fx_moli.adminessentialsplus.core.commands.player.DelHomeCommand;
import fx_moli.adminessentialsplus.core.commands.player.WorkbenchCommand;
import fx_moli.adminessentialsplus.core.commands.player.EnchantCommand;
import fx_moli.adminessentialsplus.core.commands.player.ItemCommand;
import fx_moli.adminessentialsplus.core.commands.player.KitCommand;
import fx_moli.adminessentialsplus.core.commands.player.TopCommand;
import fx_moli.adminessentialsplus.core.commands.player.JumpCommand;
import fx_moli.adminessentialsplus.core.commands.player.NearCommand;
import fx_moli.adminessentialsplus.core.commands.player.BurnCommand;
import fx_moli.adminessentialsplus.core.commands.player.ExtinguishCommand;
import fx_moli.adminessentialsplus.core.commands.player.SmiteCommand;
import fx_moli.adminessentialsplus.core.commands.player.ExpCommand;
import fx_moli.adminessentialsplus.core.commands.player.FlySpeedCommand;
import fx_moli.adminessentialsplus.core.commands.player.WalkSpeedCommand;
import fx_moli.adminessentialsplus.core.commands.teleport.TeleportPosCommand;
import org.bukkit.command.CommandExecutor;

public class CommandManager {
    private final AdminEssentialsPlus plugin;
    private FlyCommand flyCommand;
    private VanishCommand vanishCommand;
    private GodCommand godCommand;
    private BackCommand backCommand;

    public CommandManager(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        // Существующие команды
        registerCommand("inspect", new InspectCommand(plugin));
        registerCommand("watch", new InspectCommand(plugin));
        registerCommand("banitem", new BanItemCommand(plugin));
        registerCommand("lookup", new LookupCommand(plugin));
        registerCommand("restoreinv", new RestoreInvCommand(plugin));
        registerCommand("pos1", new PositionCommand(plugin));
        registerCommand("pos2", new PositionCommand(plugin));
        registerCommand("copy", new CopyPasteCommand(plugin));
        registerCommand("paste", new CopyPasteCommand(plugin));
        registerCommand("replacearea", new ReplaceAreaCommand(plugin));
        registerCommand("forestgen", new ForestGenCommand(plugin));
        registerCommand("event", new EventCommand(plugin));
        registerCommand("customrecipe", new CustomRecipeCommand(plugin));
        registerCommand("serverstats", new ServerStatsCommand(plugin));
        registerCommand("staffchat", new StaffChatCommand(plugin));
        registerCommand("sc", new StaffChatCommand(plugin));
        registerCommand("mute", new MuteCommand(plugin));

        // Новые команды управления игроками
        flyCommand = new FlyCommand(plugin);
        registerCommand("fly", flyCommand);
        vanishCommand = new VanishCommand(plugin);
        registerCommand("vanish", vanishCommand);
        registerCommand("v", vanishCommand);

        registerCommand("heal", new HealCommand(plugin));
        registerCommand("feed", new FeedCommand(plugin));
        registerCommand("clear", new ClearCommand(plugin));
        godCommand = new GodCommand(plugin);
        registerCommand("god", godCommand);

        // Команды телепортации
        registerCommand("tp", new TeleportCommand(plugin));
        registerCommand("teleport", new TeleportCommand(plugin));
        registerCommand("tphere", new TeleportHereCommand(plugin));
        backCommand = new BackCommand(plugin);
        registerCommand("back", backCommand);
        registerCommand("spawn", new SpawnCommand(plugin));
        registerCommand("setspawn", new SetSpawnCommand(plugin));

        // Команды модерации
        registerCommand("kick", new KickCommand(plugin));
        registerCommand("ban", new BanCommand(plugin));
        registerCommand("unban", new UnbanCommand(plugin));
        registerCommand("warn", new WarnCommand(plugin));

        // Команды управления сервером
        registerCommand("weather", new WeatherCommand(plugin));
        registerCommand("time", new TimeCommand(plugin));
        registerCommand("gamemode", new GamemodeCommand(plugin));
        registerCommand("gm", new GamemodeCommand(plugin));

        // Команды просмотра
        registerCommand("invsee", new InvSeeCommand(plugin));
        registerCommand("enderchest", new EnderChestCommand(plugin));
        registerCommand("ec", new EnderChestCommand(plugin));

        // Новые команды
        registerCommand("home", new HomeCommand(plugin));
        registerCommand("sethome", new SetHomeCommand(plugin));
        registerCommand("delhome", new DelHomeCommand(plugin));
        registerCommand("workbench", new WorkbenchCommand(plugin));
        registerCommand("wb", new WorkbenchCommand(plugin));
        registerCommand("enchant", new EnchantCommand(plugin));
        registerCommand("item", new ItemCommand(plugin));
        registerCommand("kit", new KitCommand(plugin));
        registerCommand("top", new TopCommand(plugin));
        registerCommand("jump", new JumpCommand(plugin));
        registerCommand("near", new NearCommand(plugin));
        registerCommand("tppos", new TeleportPosCommand(plugin));
        registerCommand("burn", new BurnCommand(plugin));
        registerCommand("extinguish", new ExtinguishCommand(plugin));
        registerCommand("ext", new ExtinguishCommand(plugin));
        registerCommand("smite", new SmiteCommand(plugin));
        registerCommand("exp", new ExpCommand(plugin));
        registerCommand("xp", new ExpCommand(plugin));
        registerCommand("flyspeed", new FlySpeedCommand(plugin));
        registerCommand("walkspeed", new WalkSpeedCommand(plugin));

        // Новые команды
        registerCommand("ping", new fx_moli.adminessentialsplus.core.commands.player.PingCommand(plugin));
        registerCommand("hat", new fx_moli.adminessentialsplus.core.commands.player.HatCommand(plugin));
        registerCommand("nick", new fx_moli.adminessentialsplus.core.commands.player.NickCommand(plugin));
        registerCommand("suicide", new fx_moli.adminessentialsplus.core.commands.player.SuicideCommand(plugin));
        
        // Fun команды
        registerCommand("launch", new fx_moli.adminessentialsplus.core.commands.fun.LaunchCommand(plugin));
        registerCommand("freeze", new fx_moli.adminessentialsplus.core.commands.fun.FreezeCommand(plugin));
        registerCommand("explode", new fx_moli.adminessentialsplus.core.commands.fun.ExplodeCommand(plugin));
        
        // Admin команды
        registerCommand("broadcast", new fx_moli.adminessentialsplus.core.commands.admin.BroadcastCommand(plugin));
        registerCommand("list", new fx_moli.adminessentialsplus.core.commands.admin.ListCommand(plugin));
        registerCommand("sudo", new fx_moli.adminessentialsplus.core.commands.admin.SudoCommand(plugin));
        
        // World команды
        registerCommand("worldinfo", new fx_moli.adminessentialsplus.core.commands.world.WorldInfoCommand(plugin));
        
        // GUI команда
        registerCommand("menu", new fx_moli.adminessentialsplus.core.commands.admin.MenuCommand(plugin));
        registerCommand("gui", new fx_moli.adminessentialsplus.core.commands.admin.MenuCommand(plugin));
        
        // Кастомные команды для Kekrix
        registerCommand("size", new fx_moli.adminessentialsplus.core.commands.custom.SizeCommand(plugin));
        registerCommand("healall", new fx_moli.adminessentialsplus.core.commands.custom.HealAllCommand(plugin));
        registerCommand("glow", new fx_moli.adminessentialsplus.core.commands.custom.GlowCommand(plugin));
        registerCommand("clearlag", new fx_moli.adminessentialsplus.core.commands.custom.ClearLagCommand(plugin));
        registerCommand("trail", new fx_moli.adminessentialsplus.core.commands.custom.TrailCommand(plugin));
        registerCommand("rocket", new fx_moli.adminessentialsplus.core.commands.custom.RocketCommand(plugin));
        registerCommand("stack", new fx_moli.adminessentialsplus.core.commands.custom.StackCommand(plugin));
        registerCommand("nightvision", new fx_moli.adminessentialsplus.core.commands.custom.NightVisionCommand(plugin));
        registerCommand("skull", new fx_moli.adminessentialsplus.core.commands.custom.SkullCommand(plugin));
        registerCommand("rainbow", new fx_moli.adminessentialsplus.core.commands.custom.RainbowCommand(plugin));
        registerCommand("slap", new fx_moli.adminessentialsplus.core.commands.custom.SlapCommand(plugin));
        registerCommand("particle", new fx_moli.adminessentialsplus.core.commands.custom.ParticleCommand(plugin));
        
        // Социальные команды Kekrix
        registerCommand("hug", new fx_moli.adminessentialsplus.core.commands.social.HugCommand(plugin));
        registerCommand("highfive", new fx_moli.adminessentialsplus.core.commands.social.HighFiveCommand(plugin));
        registerCommand("me", new fx_moli.adminessentialsplus.core.commands.social.MeCommand(plugin));
        registerCommand("coinflip", new fx_moli.adminessentialsplus.core.commands.social.CoinFlipCommand(plugin));
        registerCommand("cf", new fx_moli.adminessentialsplus.core.commands.social.CoinFlipCommand(plugin));
        
        // Утилиты Kekrix
        registerCommand("repair", new fx_moli.adminessentialsplus.core.commands.utility.RepairCommand(plugin));
        registerCommand("trash", new fx_moli.adminessentialsplus.core.commands.utility.TrashCommand(plugin));
        registerCommand("discord", new fx_moli.adminessentialsplus.core.commands.utility.DiscordCommand(plugin));
        registerCommand("rules", new fx_moli.adminessentialsplus.core.commands.utility.RulesCommand(plugin));

        // Главная команда /fx
        registerCommand("fx", new FXCommand(plugin));
        
        // Новые команды для упрощения работы админов (v1.2.0)
        registerCommand("quickactions", new fx_moli.adminessentialsplus.core.commands.admin.QuickActionsCommand(plugin));
        registerCommand("qa", new fx_moli.adminessentialsplus.core.commands.admin.QuickActionsCommand(plugin));
        registerCommand("mass", new fx_moli.adminessentialsplus.core.commands.admin.MassCommand(plugin));
        registerCommand("playerinfo", new fx_moli.adminessentialsplus.core.commands.admin.PlayerInfoCommand(plugin));
        registerCommand("pinfo", new fx_moli.adminessentialsplus.core.commands.admin.PlayerInfoCommand(plugin));

        plugin.getLogger().info("Registered " + plugin.getDescription().getCommands().size() + " commands");
    }

    public FlyCommand getFlyCommand() { return flyCommand; }
    public VanishCommand getVanishCommand() { return vanishCommand; }
    public GodCommand getGodCommand() { return godCommand; }
    public BackCommand getBackCommand() { return backCommand; }

    private void registerCommand(String command, CommandExecutor executor) {
        try {
            // Для Paper плагинов используем программную регистрацию
            org.bukkit.command.PluginCommand cmd = plugin.getCommand(command);
            if (cmd != null) {
                cmd.setExecutor(executor);
            } else {
                // Если команда не найдена в plugin.yml, регистрируем программно
                plugin.getLogger().warning("Команда " + command + " не найдена в plugin.yml, пропускаем регистрацию");
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Не удалось зарегистрировать команду: " + command);
        }
    }
}