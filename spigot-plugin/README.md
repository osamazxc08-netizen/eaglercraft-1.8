# Aether Sky Islands (Spigot/Paper 1.8.8)

This directory is a standalone Maven plugin project. It is separate from the Eaglercraft patch prototype elsewhere in this repository.

## What it does

When an overworld is initialized, the plugin registers a Bukkit `BlockPopulator`. In roughly one out of every 18 newly populated chunks, it uses the world seed and chunk coordinates to deterministically generate a small grass/dirt/stone island around Y=105–160. Existing chunks are not retroactively changed; create a new world to see islands reliably.

## Build

Use Java 8 and Maven from this directory:

```sh
mvn package
```

The plugin jar will be `target/AetherSkyIslands.jar`. Copy it into the server's `plugins/` directory, then restart a Spigot/Paper 1.8.8 server. Do not copy the jar into an Eaglercraft client; this is a server plugin.

## Notes

- Islands are sparse and use vanilla 1.8 blocks; no client mod or custom assets are required.
- Generation is seed-deterministic and limited to the normal overworld.
- This is an initial implementation. It has not been built or tested against a running server in this environment.
