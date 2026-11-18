#!/bin/sh
# Check local prerequisites for the Eaglercraft 1.8 Aether development workspace.
# This script intentionally does not download or copy Minecraft/MCP files.

set -eu

ROOT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
cd "$ROOT_DIR"

missing=0

if ! command -v java >/dev/null 2>&1; then
  echo "MISSING: Java 11 or newer (Java 17 recommended)."
  missing=1
else
  java_version=$(java -version 2>&1 | sed -n '1s/.*version "\([^"]*\)".*/\1/p')
  if [ -z "$java_version" ]; then
    echo "WARNING: Could not determine Java version; verify that Java 11+ is installed."
  else
    java_major=$(printf '%s\n' "$java_version" | awk -F. '{ if ($1 == "1") print $2; else print $1 }')
    case "$java_major" in
      ''|*[!0-9]*)
        echo "WARNING: Could not parse Java version: $java_version"
        ;;
      *)
        if [ "$java_major" -lt 11 ]; then
          echo "MISSING: Java 11 or newer (found $java_version; Java 17 recommended)."
          missing=1
        else
          echo "OK: Java $java_version"
        fi
        ;;
    esac
  fi
fi

for file in mcp918/mcp918.zip mcp918/1.8.8.jar mcp918/1.8.json; do
  if [ -f "$file" ]; then
    echo "OK: $file"
  else
    echo "MISSING: $file"
    missing=1
  fi
done

for file in build_init.sh build_make_workspace.sh; do
  if [ -f "$file" ]; then
    echo "OK: $file"
  else
    echo "MISSING: $file"
    missing=1
  fi
done

if [ "$missing" -ne 0 ]; then
  echo
  echo "Workspace setup is not ready. Add the required inputs locally from sources you are authorized to use."
  echo "Do not commit Minecraft/MCP archives or proprietary game assets to this public repository."
  exit 1
fi

echo
echo "Prerequisites found. Next run:"
echo "  sh build_init.sh"
echo "  sh build_make_workspace.sh"
