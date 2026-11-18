# Aether Edition — Porting Plan

This repository is the EaglercraftX 1.8 browser-client source/patch tooling. The Aether Edition is a compatibility port, not a drop-in Forge mod install.

## Source tree review (upstream `main`)

The supplied `sources/` directory is present and was inspected. It contains Eaglercraft-specific Java packages under `sources/main/java` (including the `net/lax1dude` namespace), LWJGL/browser compatibility code, protocol modules, TeaVM/WASM modules, setup templates, and client resources. This is useful project source, but it does **not** contradict the upstream README: the decompiled vanilla Minecraft 1.8 game source and its required MCP configuration are still excluded. The visible `sources/resources` directory contains Eaglercraft client/resource files; it is not a replacement for the missing MCP workspace inputs.

## Current repository constraints

- The repository README states that the decompiled Minecraft 1.8 source and game resources are not included.
- A development workspace must be prepared from the documented MCP 9.18 inputs before Java-side gameplay changes can be compiled.
- A Java Forge Aether mod cannot be loaded directly into Eaglercraft's browser runtime. Its gameplay systems must be reimplemented against the browser-compatible client/server code.
- Only code and assets that the project owner has permission to use may be added. Use original or appropriately licensed textures, sounds, models, and other assets.

## Local prerequisite checker

A regression test for the checker is available at `scripts/test-aether-prereqs.sh`. Run it with `sh scripts/test-aether-prereqs.sh`; it exercises missing-input detection, a Java 17 ready-path using temporary placeholder files, and rejection of Java 8. It creates no real MCP/Minecraft data. CircleCI is configured to run this test, but the hosted pipeline has not yet been triggered or verified.

Run `sh scripts/check-aether-prereqs.sh` from the repository root before workspace setup. It checks the Java version, the three locally supplied MCP inputs, and the two setup scripts. It does not download files or modify the workspace. The checker is expected to report missing inputs until the authorized local setup files are present.

## Phase 0 — Exact workspace prerequisites

The repository's `mcp918/readme.txt` names these three files as required inputs in the `mcp918/` directory:

- `mcp918.zip` — MCP 9.18 mod coder pack for Minecraft 1.8.8;
- `1.8.8.jar` — Minecraft 1.8.8 client JAR;
- `1.8.json` — Minecraft 1.8 asset index.

The root README says Java 11 or newer is required for client compilation and recommends Java 17. It also documents `build_init` followed by `build_make_workspace` to create the development workspace. The Linux/macOS client build launcher is `CompileLatestClient.sh`; it launches the BuildTools GUI and is not itself proof that the Java gameplay workspace has been generated.

**Important:** these inputs are not included in this repository. Do not commit proprietary game files or redistribute files unless you have the rights to do so. Once the workspace owner has supplied the required inputs through an authorized local setup, capture the command output and generated workspace details before proceeding.

**Phase 0 completion evidence to record:**
1. Java version;
2. confirmation that all three required MCP inputs are present locally;
3. successful completion of `build_init`;
4. successful completion of `build_make_workspace`;
5. successful baseline client build and launch;
6. browser smoke test reaching the title screen and entering a single-player world.

## Implementation sequence

### Phase 1 — Map extension points
Document the relevant existing classes and patch locations for:
- dimension registration and world providers;
- world/chunk generation;
- blocks, items, recipes, and creative tabs;
- entities, AI, and spawning;
- portals and dimension travel;
- rendering, particles, sounds, and resource-pack loading;
- save compatibility and integrated-server behavior.

Do not guess class names before the workspace exists; record exact names from the generated source.

### Phase 2 — Minimal Aether vertical slice
Implement a small original-content prototype in this order:
1. Register a separate test dimension with safe entry and return.
2. Add a simple floating-island terrain generator.
3. Add a clearly identifiable portal block and activation rules.
4. Persist dimension state and verify save/reload.
5. Add a small set of original test blocks/items.

**Exit criteria:** enter the test dimension, explore generated terrain, return to the overworld, save, reload, and repeat without crashes or lost world data.

### Phase 3 — Expand gameplay
Add original or properly licensed blocks, items, creatures, structures, ambient effects, and progression systems in isolated increments. Keep each feature behind a small testable change.

### Phase 4 — Performance and mobile validation
- Profile chunk generation, entity ticking, and rendering on low-powered mobile hardware.
- Prefer bounded generation work, conservative entity counts, and low-cost effects.
- Validate WebGL 1.0 fallback where supported.
- Test touch controls, world save/load, and browser refresh recovery.
- Add optional 8x/16x assets only when their licenses permit redistribution.

## Test checklist

- [ ] Required MCP inputs are available locally and authorized for use.
- [ ] `build_init` succeeds.
- [ ] `build_make_workspace` succeeds.
- [ ] Baseline build succeeds from a clean workspace.
- [ ] Client starts and enters a single-player world.
- [ ] Test dimension registers and generates terrain.
- [ ] Portal entry and return both work.
- [ ] World saves and reloads across browser refresh.
- [ ] Existing overworld behavior remains unchanged.
- [ ] Mobile touch controls remain usable.
- [ ] No unlicensed third-party code or assets are included.

## Current blocker

The repository intentionally omits the decompiled Minecraft source and required MCP inputs. The exact missing local inputs are listed above. Until those inputs are prepared and a baseline workspace is generated, it is not possible to safely implement or compile Java gameplay changes here. The next concrete step is to prepare Phase 0 locally without adding the game JAR, MCP archive, or asset index to this public repository.


## Verified source-layout findings (upstream `main`)

The following details were checked against files in the supplied `sources/` tree:

- `sources/main/java/net/lax1dude/eaglercraft/v1_8/EaglercraftVersion.java` is the fork identity/version configuration point. It currently identifies the upstream fork as EaglercraftX u44; this is metadata/configuration, not a gameplay extension API.
- `sources/setup/eclipseProjectFiles/.project` defines separate linked source folders for `MAIN_SRC_FOLDER`, `GAME_SRC_FOLDER`, `PROTO_GAME_SRC_FOLDER`, `PROTO_RELAY_SRC_FOLDER`, and `LWJGL_SRC_FOLDER`. This confirms the intended workspace separates browser/client support code from generated game source and protocol code.
- `mcp918/readme.txt` independently confirms the exact local MCP inputs required before `build_init`: `mcp918.zip`, `1.8.8.jar`, and `1.8.json`.

### Consequence for the Aether port

Do not implement dimension, block, item, entity, or world-generation code in the Eaglercraft support packages merely because they are visible in `sources/main/java`. Those features belong in the generated game-source workspace (the Eclipse template's `GAME_SRC_FOLDER`) and must be mapped against the actual 1.8.8 classes after workspace generation. The protocol and LWJGL folders are separate compatibility layers; they are not substitutes for Minecraft gameplay classes.

### Phase 1 status

- [x] Confirm the Eaglercraft fork identity/version configuration file.
- [x] Confirm the Eclipse workspace separates main, game, protocol, and LWJGL source folders.
- [x] Confirm exact MCP input filenames from the repository's own setup readme.
- [ ] Inspect generated game-source classes and identify dimension/world-generation hooks. Blocked until the authorized local MCP workspace exists.
- [ ] Build and launch an unchanged baseline client before gameplay changes.
