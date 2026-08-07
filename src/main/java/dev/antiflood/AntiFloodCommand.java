package dev.antiflood;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AntiFloodCommand implements CommandExecutor {

    private final AntiFloodPlugin plugin;

    public AntiFloodCommand(AntiFloodPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /antiflood <y> [water|lava|both]");
            return true;
        }

        int y;
        try {
            y = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cInvalid Y level.");
            return true;
        }

        String mode = args.length >= 2 ? args[1].toLowerCase() : "both";
        if (!mode.equals("water") && !mode.equals("lava") && !mode.equals("both")) {
            sender.sendMessage("§cMode must be: water, lava, or both");
            return true;
        }

        List<Material> targets = new ArrayList<>();
        if (mode.equals("water") || mode.equals("both")) {
            targets.add(Material.WATER);
        }
        if (mode.equals("lava") || mode.equals("both")) {
            targets.add(Material.LAVA);
        }

        World world = (sender instanceof Player p) ? p.getWorld() : Bukkit.getWorlds().get(0);

        if (plugin.getActiveTask() != null) {
            sender.sendMessage("§cAn antiflood operation is already running. Use /antifloodstop first.");
            return true;
        }

        sender.sendMessage("§aStarting antiflood scan above Y=" + y + " (" + mode + ") in world: " + world.getName());

        AntiFloodTask task = new AntiFloodTask(plugin, sender, world, y, targets);
        plugin.setActiveTask(task);
        task.runTaskTimer(plugin, 0L, 1L);

        return true;
    }
}