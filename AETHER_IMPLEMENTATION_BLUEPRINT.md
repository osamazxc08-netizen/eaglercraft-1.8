# Aether Port Implementation Blueprint

This document turns the source-layout investigation into an implementation sequence. It is deliberately an implementation blueprint, not a claim that Aether gameplay has already been ported.

## 1. Confirmed repository boundaries

- The checked-in `sources/main/java` tree contains Eaglercraft platform support and compatibility classes.
- The checked-in `sources/main/java/net/lax1dude/eaglercraft/v1_8/minecraft` directory contains a small set of Eaglercraft-specific rendering, GUI, texture, and entity-construction helpers. It is not the vanilla Minecraft game source tree.
- The workspace template's Gradle source set expects generated workspace roots including `src/game/java`, `src/main/java`, `src/protocol-game/java`, `src/protocol-relay/java`, `src/teavm/java`, and `src/teavm-boot-menu/java`.
- The TeaVM entry point is `net.lax1dude.eaglercraft.v1_8.internal.teavm.MainClass`.
- The browser client build uses the workspace's `generateJavaScript` task. EPK packaging is a separate step.
- The repository does not include the MCP inputs needed to generate the game workspace. Do not commit Minecraft jars, MCP archives, generated proprietary game sources, or extracted game assets to this public repository.

## 2. Integration rule

Do not try to load a Forge Aether mod jar into the browser client. Forge's runtime, class transformation, and mod-loading assumptions do not match this TeaVM browser target. Port gameplay systems as ordinary Java code against the generated 1.8.8 game workspace, then adapt rendering and input only where the browser platform requires it.

Keep platform-specific code out of the gameplay model. Prefer deterministic, serializable state and avoid reflection, dynamic class loading, native APIs, and thread assumptions that TeaVM cannot support.

## 3. Proposed package layout (inside the generated game workspace)

Create these packages only after the MCP workspace has been generated:

- `net.minecraft.world.gen.dimension.aether`: dimension identity, provider/world rules, and dimension travel integration.
- `net.minecraft.world.gen.feature.aether`: island/terrain and feature generation.
- `net.minecraft.block.aether` and `net.minecraft.item.aether`: Aether blocks and items.
- `net.minecraft.entity.aether`: Aether creatures and entity behavior.
- `net.minecraft.client.renderer.aether`: client-only sky, clouds, fog, and visual effects.
- `net.minecraft.client.gui.aether`: portal/travel and Aether-specific screens, if required.

These are proposed package names, not existing directories or classes in the repository. Match the generated source's actual 1.8.8 package conventions before implementation.

## 4. Porting order and acceptance checks

### Phase A — minimal playable dimension
1. Add a dimension/provider registration path that does not depend on Forge.
2. Implement dimension travel and safe return to the overworld.
3. Add a minimal Aether terrain generator with bounded generation cost.
4. Add a basic spawn/respawn rule and a small test world.

Acceptance: enter and leave the dimension in single-player; save, quit, reload, and return without losing the player or world state.

### Phase B — core content
1. Add the initial block and item registry entries using the target workspace's existing registration conventions.
2. Add recipes and loot only through supported game registries.
3. Add the first Aether creature with server-authoritative movement and interaction.
4. Keep all gameplay state synchronized through the existing single-player/network abstractions.

Acceptance: blocks/items persist in saves; recipes resolve; entity state remains consistent after save/reload.

### Phase C — visuals and polish
1. Add sky/fog/cloud rendering through existing renderer hooks.
2. Add textures and sounds only from assets that the project has permission to use.
3. Verify rendering on both WebGL and supported fallback paths.
4. Measure world-generation and rendering costs on a mobile browser.

Acceptance: no unsupported OpenGL calls, missing required assets, or severe frame-time spikes on the target browser.

### Phase D — packaging and regression
1. Run the Java/TeaVM compile from the generated workspace.
2. Build the EPK separately.
3. Run the client in a browser and exercise dimension entry/exit, save/reload, and mobile controls.
4. Record exact toolchain versions and build results in the project log.

Acceptance: reproducible browser build and a tested playable Aether slice. A successful shell syntax check alone is not a game build.

## 5. Required implementation notes

For each feature, record:
- target vanilla 1.8.8 classes and hooks after inspecting the generated workspace;
- any Eaglercraft-specific adaptation;
- whether the code runs in common/game or client-only source;
- TeaVM compatibility risks;
- save/network implications;
- a small test or manual verification procedure.

## 6. Current blocker

The next real gameplay change depends on the generated MCP workspace. Until `mcp918/mcp918.zip`, `mcp918/1.8.8.jar`, and `mcp918/1.8.json` are supplied locally from sources the developer is authorized to use, the project can safely improve tooling and documentation but cannot compile or validate Aether gameplay against the actual game classes.
