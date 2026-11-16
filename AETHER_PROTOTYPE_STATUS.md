# Prototype status: Aether-inspired world generation

## Current status

The files named `AetherPrototype*` are exploratory notes/helpers only. They are **not connected to `ChunkProviderGenerate`**, do not generate floating islands in-game, and have not been compiled. Do not treat this branch as a playable build yet.

## Why integration is paused

Eaglercraft applies edits to the Minecraft source through its context-redacted patch format. The upstream repository does not include the decompiled Minecraft source or MCP inputs, so the exact insertion points and generated classes cannot be validated from this checkout alone. Adding a normal Java class-shaped file under `patches/.../*.edit.java` does not by itself register a new class or change world generation.

## Safe next steps

1. Prepare the required local inputs listed in `mcp918/readme.txt` (`mcp918.zip`, `1.8.8.jar`, and `1.8.json`) from sources you are authorized to use.
2. Run `build_init` and `build_make_workspace` as described in the repository README.
3. In the generated workspace, inspect the actual `ChunkProviderGenerate` source and its patch conventions.
4. Implement a small, seed-deterministic chunk-generation change in the existing generator patch, then run the project's patch/build checks.
5. Only after that succeeds, add sky-color or block changes and test a newly created world.

This prototype is inspired by the general idea of floating islands and is not a port of The Aether mod.
