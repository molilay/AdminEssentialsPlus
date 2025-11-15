package fx_moli.adminessentialsplus.core.commands.utility;

import fx_moli.adminessentialsplus.AdminEssentialsPlus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {
    private final AdminEssentialsPlus plugin;
    
    public DiscordCommand(AdminEssentialsPlus plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("§d§l  Discord сервера Kekrix");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        sender.sendMessage("");
        sender.sendMessage("§7Присоединяйтесь к нашему Discord!");
        sender.sendMessage("");
        sender.sendMessage("§b§nhttps://kek.team/discord");
        sender.sendMessage("");
        sender.sendMessage("§7Оставьте заявку и начните играть!");
        sender.sendMessage("§8§m━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        return true;
    }
}
