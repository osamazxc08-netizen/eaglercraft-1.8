package net.osamazxc08.aether;

import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Adds sparse, seed-deterministic floating islands during chunk population.
 * This is a standalone Spigot/Paper plugin, not an Eaglercraft patch.
 */
public final class AetherSkyIslandsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new IslandWorldListener(this), this);
        getLogger().info("Aether Sky Islands enabled. Islands are generated in newly populated chunks.");
    }

    void installPopulator(World world) {
        if (world.getEnvironment() == World.Environment.NORMAL) {
            world.getPopulators().add(new FloatingIslandPopulator());
        }
    }
}
