package net.osamazxc08.aether;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

final class FloatingIslandPopulator extends BlockPopulator {
    private static final long X_SALT = 341873128712L;
    private static final long Z_SALT = 132897987541L;

    @Override
    public void populate(World world, Random random, Chunk source) {
        int chunkX = source.getX();
        int chunkZ = source.getZ();
        Random islandRandom = AetherSkyIslandsPlugin.worldRandom(world.getSeed(), chunkX, chunkZ);

        if (islandRandom.nextInt(18) != 0) {
            return;
        }

        int centerX = (chunkX << 4) + 5 + islandRandom.nextInt(6);
        int centerZ = (chunkZ << 4) + 5 + islandRandom.nextInt(6);
        int surfaceY = 95 + islandRandom.nextInt(55);
        int radius = 4 + islandRandom.nextInt(3);
        double radiusSquared = radius * radius;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                double normalized = (dx * dx + dz * dz) / radiusSquared;
                if (normalized > 1.0D) {
                    continue;
                }

                int x = centerX + dx;
                int z = centerZ + dz;
                int topY = surfaceY + (int) Math.round((1.0D - normalized) * 2.0D);
                int depth = 2 + (int) Math.round((1.0D - normalized) * 5.0D);

                for (int y = topY - depth; y <= topY; y++) {
                    Material mat;
                    if (y == topY) {
                        mat = Material.GRASS;
                    } else if (y >= topY - 2) {
                        mat = Material.DIRT;
                    } else {
                        mat = Material.STONE;
                    }
                    world.getBlockAt(x, y, z).setType(mat, false);
                }

                if (islandRandom.nextBoolean()) {
                    for (int cloudY = topY + 1; cloudY <= topY + 2; cloudY++) {
                        if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1) {
                            world.getBlockAt(x, cloudY, z).setType(Material.WOOL, false);
                        }
                    }
                }
            }
        }
    }
}
