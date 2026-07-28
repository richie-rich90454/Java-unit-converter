#!/usr/bin/env bash
set -euo pipefail

echo "══════════════════════════════════════════"
echo "      Java Unit Converter Launcher"
echo "══════════════════════════════════════════"
echo ""

if ! command -v java &>/dev/null; then
    echo "[ERROR] Java is not installed or not in PATH."
    echo "Please install JDK 8+ from https://java.com/download/"
    exit 1
fi

if command -v mvn &>/dev/null; then
    echo "[INFO] Maven detected -- running via Maven..."
    mvn compile exec:java -q && exit 0
    echo "[WARN] Maven execution failed, trying fallback..."
fi

SRC_DIR="src/main/java"
OUT_DIR="target/classes"
RES_DIR="src/main/resources"

mkdir -p "$OUT_DIR"

echo "[INFO] Compiling with javac..."
find "$SRC_DIR" -name '*.java' > sources.tmp 2>/dev/null
javac -d "$OUT_DIR" -encoding UTF-8 @sources.tmp
rm -f sources.tmp

cp -r "$RES_DIR"/* "$OUT_DIR"/ 2>/dev/null || true

echo "[INFO] Launching Unit Converter..."
java -cp "$OUT_DIR" com.unitconverter.App
