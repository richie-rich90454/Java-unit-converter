#!/usr/bin/env bash
set -euo pipefail

echo "══════════════════════════════════════════"
echo "      Java Unit Converter Builder"
echo "══════════════════════════════════════════"
echo ""

if ! command -v java &>/dev/null; then
    echo "[ERROR] Java is not installed or not in PATH."
    echo "Please install JDK 8+ from https://java.com/download/"
    exit 1
fi

mkdir -p dist

if command -v mvn &>/dev/null; then
    echo "[INFO] Maven detected -- building with Maven..."
    mvn clean package -q -DskipTests
    cp target/UnitConverter.jar dist/UnitConverter.jar
    echo "[OK] Built dist/UnitConverter.jar"
    exit 0
fi

echo "[INFO] Maven not found, building with javac + jar..."

SRC_DIR="src/main/java"
RES_DIR="src/main/resources"
TMP_DIR="target/build-jar"

rm -rf "$TMP_DIR"
mkdir -p "$TMP_DIR"

echo "[INFO] Compiling with javac..."
find "$SRC_DIR" -name '*.java' > sources.tmp 2>/dev/null
javac -d "$TMP_DIR" -encoding UTF-8 @sources.tmp
rm -f sources.tmp

cp -r "$RES_DIR"/* "$TMP_DIR"/ 2>/dev/null || true
cp manifest.txt "$TMP_DIR"/ 2>/dev/null || true

echo "[INFO] Packaging JAR..."
cd "$TMP_DIR"
if [ -f manifest.txt ]; then
    jar cfm ../../dist/UnitConverter.jar manifest.txt com/*.class *.png fonts/
else
    jar cf ../../dist/UnitConverter.jar com/*.class *.png fonts/
fi
cd ../../

echo "[OK] Built dist/UnitConverter.jar"
