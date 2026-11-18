#!/bin/sh
set -eu

required_files="
sources/main/java/net/lax1dude/eaglercraft/v1_8/EaglercraftVersion.java
sources/setup/eclipseProjectFiles/.project
sources/setup/workspace_template/build.gradle
sources/setup/workspace_template/CompileJS.sh
sources/setup/workspace_template/CompileEPK.sh
sources/setup/workspace_template/wasm_gc_teavm/README.md
sources/resources/EPKVersionIdentifier.txt
AETHER_PORTING_PLAN.md
AETHER_SOURCE_MAP.md
"

for path in $required_files; do
  if [ ! -s "$path" ]; then
    echo "ERROR: required source/build-map file missing or empty: $path"
    exit 1
  fi
done

grep -q 'src/game/java' sources/setup/workspace_template/build.gradle
grep -q 'net.lax1dude.eaglercraft.v1_8.internal.teavm.MainClass' sources/setup/workspace_template/build.gradle
grep -q 'CompileWASM' sources/setup/workspace_template/wasm_gc_teavm/README.md
grep -q 'MakeWASMClientBundle' sources/setup/workspace_template/wasm_gc_teavm/README.md
grep -q 'MCP' AETHER_SOURCE_MAP.md

echo "PASS: Eaglercraft source layout, browser build pipeline, and Aether porting map are present."
