# Aether-Inspired Prototype

This branch is a lightweight prototype for a sky-island style experience inside EaglercraftX 1.8.8.

It is intentionally not a direct port of The Aether mod. Instead, it aims to emulate the atmosphere of Aether using a smaller subset of world-generation and visual changes.

## Prototype goals
- Floating-island tone and elevated world feel
- Bright, pale sky palette
- Aether-like terrain style using light stone and grass variants
- Singleplayer-friendly world generation prototype

## Planned patch areas
- `patches/minecraft/net/minecraft/world`
- `patches/minecraft/net/minecraft/world/gen`
- `patches/minecraft/net/minecraft/init`
- `patches/minecraft/net/minecraft/block`
- `patches/minecraft/net/minecraft/item`

## Implementation notes
This is the first prototype layer. It focuses on the world-generation and atmosphere hooks, not on full entity or item duplication.

The patch file `patches/minecraft/net/minecraft/world/AetherPrototypeWorldProvider.edit.java` contains the initial Aether-inspired prototype logic and can be expanded into a full sky-island generator.
