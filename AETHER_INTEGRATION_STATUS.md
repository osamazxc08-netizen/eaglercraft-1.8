# Aether integration — engineering status

Date: 2026-11-20
Target: EaglercraftX 1.8.8, offline singleplayer

## Verified repository facts

- The Eaglercraft repository contains the browser-compatibility source and patch tooling, but its README explicitly says it does not contain the decompiled Minecraft 1.8 source/resources or MCP configuration.
- The development setup requires three inputs in `mcp918/`: `mcp918.zip`, `1.8.8.jar`, and the Minecraft 1.8 asset index `1.8.json`.
- The setup scripts only invoke `java -jar buildtools/BuildTools.jar init` and `java -jar buildtools/BuildTools.jar workspace`.
- EaglercraftX 1.8 already supports offline singleplayer and saves worlds in browser local storage. This is the intended base for offline play.
- The separate `eaglercraftX188-aether-custom` repository describes itself as an original Three.js browser prototype. Its README explicitly says it is not a Minecraft/Eaglercraft client and not a port of the Java Aether mod.

## Preparation already present on this branch

- `mcp918/1.8.json` was added to this branch. Its SHA-1 (after excluding the trailing newline) matches the asset-index hash referenced by the Minecraft 1.8.8 version metadata: `f6ad102bcaa53b1a58358f16e376d548d44933ec`.

## Current blockers — no build claimed

- The required MCP archive, client JAR, and BuildTools JAR have not been installed into a runnable build workspace in this session.
- The GitHub connector can read and edit repository files, but it cannot execute the repository's Java build scripts or launch a Codespace terminal.
- The current Aether prototype is not reusable as a Java dimension/mod implementation. Its gameplay code would need a deliberate port into the Minecraft 1.8.8 source after the workspace is generated.
- No Eaglercraft client has been compiled or runtime-tested yet.

## Safe, staged implementation plan

1. Generate the decompiled 1.8.8 workspace using the repository's documented setup process.
2. Add a non-combat Aether dimension/provider, terrain generation, sky rendering, portal frame/activation, blocks, structures, and world persistence.
3. Connect the dimension travel flow to the existing singleplayer integrated server and save system.
4. Compile the browser client, then test world creation, portal travel, returning to the Overworld, save/reload, and offline startup.

## Acceptance checks

- New worlds start in the normal Overworld.
- Players construct and activate the Aether portal themselves.
- Entering and leaving the portal moves between dimensions without losing player/world state.
- Aether terrain and structures generate consistently.
- Saved worlds survive reload and work offline.
- No release is described as complete until a built client passes these checks.

## Follow-up repository inspection (2026-11-20)

- The branch's `mcp918/` directory currently contains only `1.8.json`, `assetsIndexTransformer.json`, and `readme.txt`. It does not contain the required `mcp918.zip` or `1.8.8.jar` inputs.
- The `sources/main/java/` tree contains Eaglercraft-specific namespaces (for example `net/lax1dude/eaglercraft`); it is not the generated/decompiled Minecraft source workspace.
- The Aether prototype repository has a prebuilt standalone `dist/aether-skies-offline.html`, but it remains a separate browser game and cannot be dropped into the Eaglercraft client as a dimension.
- GitHub code search did not return source matches for Minecraft dimension classes on these repositories. This is not proof those classes do not exist elsewhere; it confirms the integration targets cannot currently be inspected through the available repository search results.

## Next concrete dependency

The next implementation step is blocked until a build environment can run the documented `BuildTools.jar init` and `workspace` tasks against the already supplied MCP archive and Minecraft 1.8.8 client JAR. Once that generated workspace exists, the dimension and portal changes can be made against real class names and verified by compilation rather than guessed.


## Newly found build-workspace candidate (2026-11-20)

- Located a public pre-generated EaglercraftX 1.8 workspace: `Eaglercraft-Archive/EaglercraftX-1_8-workspace`.
- Its repository tree contains a Gradle project (`build.gradle.kts`, `gradlew`) and a large Java source tree (8,216 tree entries inspected). Its README says Java 17+ is required and documents building the JavaScript offline client with the `MakeOfflineDownload` script / `makeMainOfflineDownload` Gradle task.
- This is a more direct starting point than rerunning MCP decompilation from scratch. However, it is a third-party read-only repository in the connected GitHub account, and the available tools still cannot clone/download its full source into a runnable Java environment or execute Gradle.
- No Aether code has been merged into this workspace, and no compiled output or gameplay test has been produced. Treat it only as a candidate base, not as a finished client.


## Source-level integration map from the pre-generated workspace (2026-11-20)

The public candidate workspace was inspected directly and contains actual deobfuscated Minecraft source, so the earlier assumption that no usable source could be found is superseded. Relevant confirmed source locations:

- `src/game/java/net/minecraft/world/WorldProvider.java`: provider factory currently recognizes only dimension IDs -1, 0, and 1; this is the registration point for an Aether provider.
- `src/game/java/net/minecraft/world/WorldServer.java`: creates each dimension's chunk provider from its WorldProvider and save handler, so a provider-specific chunk generator can plug into the existing chunk/save pipeline.
- `src/game/java/net/minecraft/server/MinecraftServer.java`: currently allocates exactly three WorldServer entries and maps only Nether (-1), Overworld (0), and End (1). A fourth dimension needs coordinated server lifecycle, tick, save, and lookup changes.
- `src/game/java/net/minecraft/entity/player/EntityPlayerMP.java`: contains `travelToDimension`; the existing player-transfer path can be extended for Aether travel.
- `src/game/java/net/minecraft/block/BlockPortal.java` and `src/game/java/net/minecraft/world/Teleporter.java`: contain the vanilla portal behavior. A Glowstone + water Aether portal requires a separate activation/teleport implementation rather than changing the vanilla Nether portal semantics.
- `src/game/java/net/minecraft/world/gen/ChunkProviderGenerate.java`: reference implementation for chunk generation; Aether floating-island generation needs a dedicated provider or a carefully scoped generator.

The workspace README confirms Java 17+ and documents the offline JavaScript build task `makeMainOfflineDownload` (or `MakeOfflineDownload`). Its Gradle project is a plausible base for implementation, but it is owned by a third party and the connected account has read-only access.

## Implementation state after source inspection

- Confirmed: the candidate workspace has the actual dimension, portal, server, player-transfer, and chunk-generation classes needed to begin a real port.
- Not yet changed: no source files in that third-party workspace have been modified; the connected GitHub account has no push permission there.
- Not built: the Gradle task has not run, so no offline client artifact exists and no gameplay behavior has been tested.
- Required next engineering work: create a writable fork/copy of the source workspace, add a fourth WorldServer dimension consistently across server arrays and lifecycle code, implement the Aether provider and floating-island chunk generator, add the Glowstone/water portal block and travel flow, then build and test the offline JavaScript client.
