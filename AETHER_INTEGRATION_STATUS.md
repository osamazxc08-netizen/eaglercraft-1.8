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
