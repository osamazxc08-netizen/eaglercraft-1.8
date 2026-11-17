# Aether Sky Islands (Spigot/Paper 1.8.8)

This directory is a standalone Maven plugin project. It is separate from the Eaglercraft patch prototype elsewhere in this repository.

## What it does

When an overworld is initialized, the plugin registers a Bukkit `BlockPopulator`. In roughly one out of every 18 newly populated chunks, it uses the world seed and chunk coordinates to deterministically generate a small grass/dirt/stone island around Y=95–150. It also adds occasional cloud-like wool blocks above the island tops for the brighter floating island feel.

## Build

Use Java 8 and Maven from this directory:

```sh
mvn package
```

The plugin jar will be `target/AetherSkyIslands.jar`. Copy it into the server's `plugins/` directory, then restart a Spigot/Paper 1.8.8 server.

## Commands

- `/aether info` — display plugin info
- `/aether regen` — generate a floating island cluster around the block you're looking at

## Notes

- Islands are sparse and use vanilla 1.8 blocks.
- Generation is seed-deterministic and limited to the normal overworld.
- This is an initial implementation and has not been runtime-tested in this environment.
