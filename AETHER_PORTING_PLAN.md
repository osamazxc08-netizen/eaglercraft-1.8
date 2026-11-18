# Aether Edition — Porting Plan

This repository is the EaglercraftX 1.8 browser-client source/patch tooling. The Aether Edition is a compatibility port, not a drop-in Forge mod install.

## Current repository constraints

- The repository README states that the decompiled Minecraft 1.8 source and game resources are not included.
- A development workspace must be prepared from the documented MCP 9.18 inputs before Java-side gameplay changes can be compiled.
- A Java Forge Aether mod cannot be loaded directly into Eaglercraft's browser runtime. Its gameplay systems must be reimplemented against the browser-compatible client/server code.
- Only code and assets that the project owner has permission to use may be added. Use original or appropriately licensed textures, sounds, models, and other assets.

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
