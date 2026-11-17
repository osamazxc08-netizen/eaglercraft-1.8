package net.osamazxc08.aether;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

final class IslandRegenerator {
    private final AetherSkyIslandsPlugin plugin;

    IslandRegenerator(AetherSkyIslandsPlugin plugin) {
        this.plugin = plugin;
    }

    void regenerateAt(Location center, int radius, int lift) {
        World world = center.getWorld();
        int baseX = center.getBlockX();
        int baseY = center.getBlockY();
        int baseZ = center.getBlockZ();
        Random random = new Random(baseX ^ baseZ ^ world.getSeed());

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist > radius) {
                    continue;
                }

                int topY = baseY + lift + (int) Math.round((1.0D - (dist / radius)) * 3.0D) + random.nextInt(2);
                for (int y = topY - 3; y <= topY; y++) {
                    Material mat;
                    if (y == topY) {
                        mat = Material.GRASS;
                    } else if (y >= topY - 2) {
                        mat = Material.DIRT;
                    } else {
                        mat = Material.STONE;
                    }
                    world.getBlockAt(baseX + dx, y, baseZ + dz).setType(mat, false);
                }

                if (random.nextBoolean() && Math.abs(dx) <= 1 && Math.abs(dz) <= 1) {
                    world.getBlockAt(baseX + dx, topY + 1, baseZ + dz).setType(Material.WOOL, false);
                }
            }
        }
    }
}
