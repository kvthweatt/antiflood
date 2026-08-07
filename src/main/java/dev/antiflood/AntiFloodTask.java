package dev.antiflood;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class AntiFloodTask extends BukkitRunnable {

    private final AntiFloodPlugin plugin;
    private final CommandSender sender;
    private final World world;
    private final int minY;
    private final List<Material> targets;

    private final List<long[]> chunkCoords;
    private int chunkIndex = 0;
    private int totalRemoved = 0;
    private static final int CHUNKS_PER_TICK = 5;

    public AntiFloodTask(AntiFloodPlugin plugin, CommandSender sender, World world, int minY, List<Material> targets) {
        this.plugin = plugin;
        this.sender = sender;
        this.world = world;
        this.minY = minY;
        this.targets = targets;
        this.chunkCoords = getLoadedChunks();
    }

    private List<long[]> getLoadedChunks() {
        List<long[]> list = new ArrayList<>();
        for (Chunk chunk : world.getLoadedChunks()) {
            list.add(new long[]{chunk.getX(), chunk.getZ()});
        }
        return list;
    }

    @Override
    public void run() {
        int processed = 0;

        while (chunkIndex < chunkCoords.size() && processed < CHUNKS_PER_TICK) {
            long[] coord = chunkCoords.get(chunkIndex++);
            int cx = (int) coord[0];
            int cz = (int) coord[1];

            if (!world.isChunkLoaded(cx, cz)) continue;

            Chunk chunk = world.getChunkAt(cx, cz);
            int worldMaxY = world.getMaxHeight();

            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = minY; y < worldMaxY; y++) {
                        Block block = chunk.getBlock(x, y, z);
                        if (targets.contains(block.getType())) {
                            block.setType(Material.AIR, false);
                            totalRemoved++;
                        }
                    }
                }
            }

            processed++;
        }

        if (chunkIndex >= chunkCoords.size()) {
            sender.sendMessage("§aAntiflood complete. Removed " + totalRemoved + " blocks across " + chunkCoords.size() + " chunks.");
            plugin.setActiveTask(null);
            cancel();
        }
    }
}