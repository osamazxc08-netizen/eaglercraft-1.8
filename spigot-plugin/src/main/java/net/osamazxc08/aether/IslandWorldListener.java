package net.osamazxc08.aether;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

final class IslandWorldListener implements Listener {
    private final AetherSkyIslandsPlugin plugin;

    IslandWorldListener(AetherSkyIslandsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {
        plugin.installPopulator(event.getWorld());
    }
}
