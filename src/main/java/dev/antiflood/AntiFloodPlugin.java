package dev.antiflood;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiFloodPlugin extends JavaPlugin {

    private AntiFloodTask activeTask = null;

    @Override
    public void onEnable() {
        getCommand("antiflood").setExecutor(new AntiFloodCommand(this));
        getCommand("antifloodstop").setExecutor(new AntiFloodStopCommand(this));
        getLogger().info("AntiFlood enabled.");
    }

    @Override
    public void onDisable() {
        if (activeTask != null) {
            activeTask.cancel();
            activeTask = null;
        }
    }

    public AntiFloodTask getActiveTask() { return activeTask; }
    public void setActiveTask(AntiFloodTask task) { this.activeTask = task; }
}