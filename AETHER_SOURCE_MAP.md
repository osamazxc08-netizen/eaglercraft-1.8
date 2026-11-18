# Aether Porting: Source and Extension Map

This document records a repository inspection for the Eaglercraft 1.8 browser port. It is an implementation map, not a claim that Aether gameplay has been integrated.

## Confirmed source layout

- `sources/main/java/net/lax1dude/eaglercraft/v1_8` contains Eaglercraft platform/runtime support.
- The inspected package tree includes `minecraft`, `opengl`, `sp`, and `internal` support packages. The presence of these packages does not mean the complete decompiled vanilla game source is present.
- The inspected Eaglercraft tree contains 491 Java files under `net/lax1dude/eaglercraft`; these are platform/fork support classes, not a replacement for the missing MCP game workspace.
- `sources/resources` contains Eaglercraft packaging/runtime resources (including EPK metadata, signed-client template, plugin/relay downloads, and a resource-pack metadata file). It is not a complete vanilla Minecraft asset bundle.
- `sources/setup/eclipseProjectFiles/.project` defines separate source folders for main, game, prototype game, prototype relay, and LWJGL code.
- `sources/setup/workspace_template` supplies the generated workspace build wrapper and scripts such as `CompileJS.sh` and `CompileEPK.sh`.

## Porting implications

1. Do not try to load the Forge Aether mod jar into Eaglercraft. Forge's runtime and mod-loading hooks are not available in this browser target.
2. Implement Aether gameplay as native additions in the generated game workspace: dimension/world provider, biome and terrain generation, blocks/items, entities, recipes, and client rendering/audio as needed.
3. Keep browser-specific integrations behind the existing Eaglercraft platform abstractions. Avoid modifying WebGL/input/network compatibility code unless a concrete gameplay feature requires it.
4. Treat `sources/main/java/net/lax1dude/eaglercraft/v1_8/minecraft` as browser/fork support, not as proof that the vanilla game classes such as `World`, `WorldProvider`, `ChunkProviderGenerate`, `Block`, or `Entity` are available there.
5. The exact class-level patch points must be selected only after a local MCP 9.18 workspace is generated from files the developer is authorized to use.

## Workspace prerequisites

The repository's MCP setup notes identify these local inputs:

- `mcp918/mcp918.zip`
- `mcp918/1.8.8.jar`
- `mcp918/1.8.json`

The repository scripts `build_init.sh` and `build_make_workspace.sh` then initialize and generate the workspace. Keep those inputs local; do not commit Minecraft/MCP binaries, extracted game sources, or game assets to this public repository.

## Recommended implementation sequence

1. Generate and verify the local MCP workspace.
2. Map the actual game classes and Eaglercraft patches for world providers, chunk providers, biome registration, block/item registration, entity registration, and client renderers.
3. Add a minimal Aether dimension with a safe return route and deterministic test seed.
4. Add terrain/biomes and basic blocks before mobs, loot, dungeons, and progression.
5. Add client-side textures/models/audio through the target's supported resource pipeline.
6. Run desktop-runtime smoke tests and browser/TeaVM compilation after each milestone.

## Current status

- Repository inspection and prerequisite tooling: prepared.
- Native Aether gameplay implementation: not started; blocked on the generated MCP game workspace.
- Full game build or playable artifact: not yet produced.
