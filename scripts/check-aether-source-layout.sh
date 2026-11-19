#!/bin/sh
set -eu

required_files="
sources/main/java/net/lax1dude/eaglercraft/v1_8/EaglercraftVersion.java
sources/setup/eclipseProjectFiles/.project
sources/setup/workspace_template/build.gradle
sources/setup/workspace_template/CompileJS.sh
sources/setup/workspace_template/CompileEPK.sh
sources/setup/workspace_template/wasm_gc_teavm/README.md
build_init.sh
build_make_workspace.sh
buildtools/BuildTools.jar
mcp918/readme.txt
sources/resources/EPKVersionIdentifier.txt
AETHER_PORTING_PLAN.md
AETHER_SOURCE_MAP.md
AETHER_IMPLEMENTATION_BLUEPRINT.md
"

for path in $required_files; do
  if [ ! -s "$path" ]; then
    echo "ERROR: required source/build-map file missing or empty: $path"
    exit 1
  fi
done

# Verify every source root used by the JavaScript workspace template.
for source_root in \
  src/main/java \
  src/game/java \
  src/protocol-game/java \
  src/protocol-relay/java \
  src/teavm/java \
  src/teavm-boot-menu/java; do
  if ! grep -Fq "\"$source_root\"" sources/setup/workspace_template/build.gradle; then
    echo "ERROR: expected Gradle source root is missing: $source_root"
    exit 1
  fi
done

# Verify the actual TeaVM plugin, task, entry point, and generated output.
grep -Fq 'id "org.teavm" version "0.9.2"' sources/setup/workspace_template/build.gradle
grep -Fq 'tasks.named("generateJavaScript")' sources/setup/workspace_template/build.gradle
grep -Fq 'mainClass = "net.lax1dude.eaglercraft.v1_8.internal.teavm.MainClass"' sources/setup/workspace_template/build.gradle
grep -Fq 'targetFileName = "../" + name' sources/setup/workspace_template/build.gradle
grep -q 'CompileWASM' sources/setup/workspace_template/wasm_gc_teavm/README.md
grep -q 'MakeWASMClientBundle' sources/setup/workspace_template/wasm_gc_teavm/README.md
grep -q 'MCP' AETHER_SOURCE_MAP.md
grep -q 'Do not try to load a Forge Aether mod jar' AETHER_IMPLEMENTATION_BLUEPRINT.md
grep -q 'Phase A' AETHER_IMPLEMENTATION_BLUEPRINT.md
grep -q 'TeaVM' AETHER_IMPLEMENTATION_BLUEPRINT.md

echo "PASS: Eaglercraft source layout, browser build pipeline, and Aether porting map are present."
