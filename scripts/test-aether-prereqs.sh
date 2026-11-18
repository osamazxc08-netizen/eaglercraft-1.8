#!/bin/sh
# Exercise the prerequisite checker in an isolated temporary repository.
# Dummy MCP filenames are created only under the temporary directory.
set -eu

ROOT=$(CDPATH= cd -- "$(dirname -- "$0")/.." && pwd)
TMP_DIR=$(mktemp -d)
trap 'rm -rf "$TMP_DIR"' EXIT HUP INT TERM

mkdir -p "$TMP_DIR/repo/scripts" "$TMP_DIR/repo/mcp918" "$TMP_DIR/bin"
cp "$ROOT/scripts/check-aether-prereqs.sh" "$TMP_DIR/repo/scripts/"

cat > "$TMP_DIR/bin/java" <<'JAVA17'
#!/bin/sh
echo 'openjdk version "17.0.12" 2024-07-16' >&2
JAVA17
chmod +x "$TMP_DIR/bin/java"

# Missing MCP inputs must be reported as not ready.
if PATH="$TMP_DIR/bin:$PATH" sh "$TMP_DIR/repo/scripts/check-aether-prereqs.sh" >"$TMP_DIR/missing.log" 2>&1; then
  echo "FAIL: checker accepted missing MCP inputs"
  exit 1
fi
grep -q 'MISSING: mcp918/mcp918.zip' "$TMP_DIR/missing.log"
grep -q 'MISSING: mcp918/1.8.8.jar' "$TMP_DIR/missing.log"
grep -q 'MISSING: mcp918/1.8.json' "$TMP_DIR/missing.log"

# Placeholder files in the temporary repository exercise the ready path;
# no game or MCP data is copied, downloaded, or committed.
: > "$TMP_DIR/repo/mcp918/mcp918.zip"
: > "$TMP_DIR/repo/mcp918/1.8.8.jar"
: > "$TMP_DIR/repo/mcp918/1.8.json"
: > "$TMP_DIR/repo/build_init.sh"
: > "$TMP_DIR/repo/build_make_workspace.sh"
if ! PATH="$TMP_DIR/bin:$PATH" sh "$TMP_DIR/repo/scripts/check-aether-prereqs.sh" >"$TMP_DIR/ready.log" 2>&1; then
  cat "$TMP_DIR/ready.log"
  echo "FAIL: checker rejected complete prerequisites with Java 17"
  exit 1
fi
grep -q 'Prerequisites found' "$TMP_DIR/ready.log"

# Java 8 must be rejected even when all expected files exist.
cat > "$TMP_DIR/bin/java" <<'JAVA8'
#!/bin/sh
echo 'java version "1.8.0_392"' >&2
JAVA8
chmod +x "$TMP_DIR/bin/java"
if PATH="$TMP_DIR/bin:$PATH" sh "$TMP_DIR/repo/scripts/check-aether-prereqs.sh" >"$TMP_DIR/java8.log" 2>&1; then
  echo "FAIL: checker accepted Java 8"
  exit 1
fi
grep -q 'Java 11 or newer' "$TMP_DIR/java8.log"

echo "PASS: missing inputs, Java 17 ready path, and Java 8 rejection"
