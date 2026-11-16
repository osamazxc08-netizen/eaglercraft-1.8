# Eagler Context Redacted Diff
# Copyright (c) 2025
#
# Prototype block palette used as the base for a light Aether-inspired surface palette.
# This is not a full blockset; it is a starting palette for world-surface tuning.
#
> INSERT 0 : 0 @ 0
+
+ package net.minecraft.block;
+
+ /**
+  * Lightweight local palette prototype for sky-island surface materials.
+  */
+ public final class AetherPrototypeBlockPalette {
+
+     private AetherPrototypeBlockPalette() {
+     }
+
+     public static final int LIGHT_STONE = 0xD9E7D0;
+     public static final int CLOUDY_GRASS = 0xB7E9B0;
+     public static final int AETHER_SAND = 0xE9E6CC;
+
+     public static int getLightStoneColor() {
+         return LIGHT_STONE;
+     }
+
+     public static int getCloudyGrassColor() {
+         return CLOUDY_GRASS;
+     }
+
+     public static int getAetherSandColor() {
+         return AETHER_SAND;
+     }
+ }
+
> EOF
