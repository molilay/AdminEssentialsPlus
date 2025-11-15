package fx_moli.adminessentialsplus.core.listeners;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import fx_moli.adminessentialsplus.core.commands.chat.MuteCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final AdminEssentialsPlus plugin;
    private final MuteCommand muteCommand;

    public ChatListener(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
        this.muteCommand = new MuteCommand(plugin);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        // Проверка мута
        if (muteCommand.isMuted(player)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Вы заглушены и не можете писать в чат!");
            return;
        }

        // AI модерация отключена
    }
}