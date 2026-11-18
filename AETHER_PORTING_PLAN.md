# Aether Edition — Porting Plan

This repository is the EaglercraftX 1.8 browser-client source/patch tooling. The Aether Edition is a compatibility port, not a drop-in Forge mod install.

## Current repository constraints

- The repository README states that the decompiled Minecraft 1.8 source and game resources are not included.
- A development workspace must be prepared from the documented MCP 9.18 inputs before Java-side gameplay changes can be compiled.
- A Java Forge Aether mod cannot be loaded directly into Eaglercraft's browser runtime. Its gameplay systems must be reimplemented against the browser-compatible client/server code.
- Only code and assets that the project owner has permission to use may be added. Use original or appropriately licensed textures, sounds, models, and other assets.

## Implementation sequence

### Phase 0 — Establish a reproducible baseline
1. Prepare the MCP 9.18 inputs described by `mcp918/readme.txt`.
2. Run `build_init` and `build_make_workspace` as documented by the upstream project.
3. Compile and launch an unmodified client.
4. Record the Java version, build command, output artifact, and browser smoke-test result.

**Exit criteria:** a clean baseline build and a client that reaches the title screen and can enter a single-player world.

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

- [ ] Baseline build succeeds from a clean workspace.
- [ ] Client starts and enters a single-player world.
- [ ] Test dimension registers and generates terrain.
- [ ] Portal entry and return both work.
- [ ] World saves and reloads across browser refresh.
- [ ] Existing overworld behavior remains unchanged.
- [ ] Mobile touch controls remain usable.
- [ ] No unlicensed third-party code or assets are included.

## Current blocker

The repository intentionally omits the decompiled Minecraft source and required MCP inputs. Until those inputs are prepared and a baseline workspace is generated, it is not possible to safely implement or compile Java gameplay changes here. The next concrete engineering step is to complete Phase 0 and capture the build output/log.
