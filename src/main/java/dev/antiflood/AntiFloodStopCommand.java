package dev.antiflood;

import org.bukkit.command.*;

public class AntiFloodStopCommand implements CommandExecutor {

    private final AntiFloodPlugin plugin;

    public AntiFloodStopCommand(AntiFloodPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (plugin.getActiveTask() == null) {
            sender.sendMessage("§cNo antiflood operation is running.");
            return true;
        }
        plugin.getActiveTask().cancel();
        plugin.setActiveTask(null);
        sender.sendMessage("§aAntiflood operation stopped.");
        return true;
    }
}