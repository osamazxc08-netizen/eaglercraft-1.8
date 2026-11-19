# Aether Edition — Development Progress

Last reviewed: 2026-11-19
Working branch: `aether-porting-foundation`

## Verified repository state

- The repository is an EaglercraftX 1.8 browser-client source/patch project.
- The browser build template uses Gradle with TeaVM 0.9.2 and the entry point `net.lax1dude.eaglercraft.v1_8.internal.teavm.MainClass`.
- The JavaScript build task is `generateJavaScript`; EPK packaging is a separate operation.
- The generated workspace expects separate source roots, including `src/game/java`.
- The visible checked-in source tree does not contain the generated vanilla Minecraft game workspace.
- The Aether port must be implemented natively; a Forge Aether JAR is not a drop-in browser mod.

## Current milestone

**Milestone 0 — repository and build-path reconnaissance: documented.**

Evidence:
- `AETHER_PORTING_PLAN.md`
- `AETHER_SOURCE_MAP.md`
- `AETHER_IMPLEMENTATION_BLUEPRINT.md`
- `sources/setup/workspace_template/build.gradle`
- `sources/setup/workspace_template/CompileJS.sh`
- `mcp918/readme.txt`

## Not yet completed

- No generated MCP game workspace has been inspected.
- No Aether gameplay classes have been added.
- No JavaScript client build has been run in a real build environment.
- No browser-playable Aether artifact exists yet.
- The scaffold CI workflow has not been confirmed green.

## Required next milestone — workspace verification

The repository setup notes require these local inputs under `mcp918/`:
- `mcp918.zip`
- `1.8.8.jar`
- `1.8.json`

Then, in an authorized local/cloud build workspace:
1. Verify Java 11+ (Java 17 recommended).
2. Run `sh build_init.sh`.
3. Run `sh build_make_workspace.sh`.
4. Inspect the generated game-source tree and record exact 1.8.8 extension points.
5. Build the unchanged browser client before adding gameplay changes.

Do not upload or commit proprietary Minecraft/MCP inputs, extracted proprietary game source, or unlicensed assets to this public repository. The build and test must be performed only with files the project owner is authorized to use.

## Aether implementation order after workspace verification

1. Identify actual dimension/provider, world/chunk generation, block/item registration, entity, save, and client-rendering hooks in the generated source.
2. Add a minimal test dimension with safe return to the overworld.
3. Add bounded floating-island terrain generation.
4. Verify enter/exit and save/reload.
5. Add original or properly licensed blocks, items, entities, visuals, and sounds in small tested increments.
6. Compile with TeaVM, package the client, and test in a browser including touch controls.

## Evidence policy

A documentation check or shell syntax test is not a game build. Mark a milestone complete only when its command output, build artifact, or browser test evidence has been captured. Never describe the project as playable until a browser smoke test confirms it.
