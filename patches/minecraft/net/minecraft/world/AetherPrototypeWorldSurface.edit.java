# Eagler Context Redacted Diff
# Copyright (c) 2025
#
# Prototype sky-generation tune for a brighter floating-island feel.
# This is intentionally compact and only meant to demonstrate the Aether-inspired direction.
#
> INSERT 0 : 0 @ 0
+
+ package net.minecraft.world;
+
+ import net.minecraft.world.gen.AetherPrototypeChunkProviderGenerate;
+
+ /**
+  * Prototype helper for world-surface tuning.
+  */
+ public final class AetherPrototypeWorldSurface {
+
+     private AetherPrototypeWorldSurface() {
+     }
+
+     public static int getPrototypeSurfaceHeight(int chunkX, int chunkZ, int baseHeight, int localX, int localZ) {
+         if (AetherPrototypeChunkProviderGenerate.shouldUseSkyIslandProfile(chunkX, chunkZ, 0x5EEDL)) {
+             return AetherPrototypeChunkProviderGenerate.getPrototypeSurfaceY(localX, localZ, baseHeight);
+         }
+         return baseHeight;
+     }
+ }
+
> EOF
