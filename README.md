<div align="center">

# Java Unit Converter

### Zero-dependency desktop unit conversion -- Volume, Length, Temperature

[![Java](https://img.shields.io/badge/Java-8%2B-%23ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://adoptium.net/)
[![Platform](https://img.shields.io/badge/Platform-Desktop-%2344A833?style=flat-square&logo=windows&logoColor=white)]()
[![GUI](https://img.shields.io/badge/GUI-Swing-%23F78C40?style=flat-square&logo=java&logoColor=white)]()

[![License](https://img.shields.io/github/license/richie-rich90454/Java-unit-converter?style=flat-square&color=%2344A833)](LICENSE)
[![Release](https://img.shields.io/github/v/release/richie-rich90454/Java-unit-converter?style=flat-square&color=%232D72D6)](https://github.com/richie-rich90454/Java-unit-converter/releases)
[![Last Commit](https://img.shields.io/github/last-commit/richie-rich90454/Java-unit-converter?style=flat-square&color=%23979797)](https://github.com/richie-rich90454/Java-unit-converter/commits)
[![Repo Size](https://img.shields.io/github/repo-size/richie-rich90454/Java-unit-converter?style=flat-square&color=%23979797)]()

[![Stars](https://img.shields.io/github/stars/richie-rich90454/Java-unit-converter?style=flat-square&color=%23EAC54F)](https://github.com/richie-rich90454/Java-unit-converter/stargazers)
[![Issues](https://img.shields.io/github/issues/richie-rich90454/Java-unit-converter?style=flat-square&color=%23CB2F2F)](https://github.com/richie-rich90454/Java-unit-converter/issues)
[![PRs](https://img.shields.io/github/issues-pr/richie-rich90454/Java-unit-converter?style=flat-square&color=%232D72D6)](https://github.com/richie-rich90454/Java-unit-converter/pulls)
[![CI](https://img.shields.io/github/actions/workflow/status/richie-rich90454/Java-unit-converter/ci.yml?style=flat-square&label=CI&color=%2344A833)](https://github.com/richie-rich90454/Java-unit-converter/actions/workflows/ci.yml)
[![Release](https://img.shields.io/github/actions/workflow/status/richie-rich90454/Java-unit-converter/release.yml?style=flat-square&label=release&color=%232D72D6)](https://github.com/richie-rich90454/Java-unit-converter/actions/workflows/release.yml)

---

**Java Unit Converter** is a lightweight, pure-Java desktop application that performs real-time bidirectional conversion between 24 units across Volume, Length, and Temperature. No frameworks. No external libraries. Just clean Swing.

[Getting Started](#getting-started) &bull;
[Features](#features) &bull;
[Screenshots](#screenshots) &bull;
[Build](#build-with-maven-all-platforms) &bull;
[Native Installers](#building-native-installers) &bull;
[Contributing](#contributing)

</div>

---

## Why This Project?

Most unit converter tools are web-based, cluttered with ads, or require an internet connection. This project was built to prove that Java Swing remains a viable choice for fast, offline desktop utilities that are:

- **Instant** -- converts as you type, no button pressing required
- **Offline** -- works entirely on your machine, zero network calls
- **Portable** -- single JAR, runs on Windows, macOS, and Linux
- **Transparent** -- MIT licensed, inspect every line of code

---

## Screenshots

<div align="center">

| Volume Conversion | Length Conversion | Temperature Conversion |
|:-:|:-:|:-:|
| <img src="demo/image_one.png" width="240"/> | <img src="demo/image_two.png" width="240"/> | <img src="demo/image_three.png" width="240"/> |
| <img src="demo/image_four.png" width="240"/> | <img src="demo/image_five.png" width="240"/> | <img src="demo/image_six.png" width="240"/> |

</div>

---

## Features

### Supported Units (24 total)

| Category | Units | Count |
|----------|-------|-------|
| **Volume** | Gallon, Cup, Tablespoon, Teaspoon, Cubic Foot, Cubic Yard, Cubic Inch, Liter, Milliliter, Cubic Meter, Cubic Centimeter, Quart, Pint | 13 |
| **Length** | Mile, Yard, Foot, Inch, Meter, Kilometer, Centimeter, Nautical Mile | 8 |
| **Temperature** | Fahrenheit, Celsius, Kelvin | 3 |

### Key Capabilities

- **Bidirectional real-time conversion** -- modify either field, the other updates instantly
- **Input validation** -- rejects invalid characters at the keystroke level
- **Decimal and negative support** -- enter `-` alone to use it as `-1`
- **Absolute-zero clamping** -- temperature inputs below physical limits are corrected automatically
- **Custom typography** -- EB Garamond and Noto Sans bundled and registered at startup
- **Audio feedback** -- subtle 432 Hz sine wave tone on conversion generation (falls back to system beep)
- **Topic tabs** -- Volume, Length, and Temperature organized in a tabbed pane

---

## How It Works

```
Select Units --> Click "Generate Conversion" --> Type a value --> Result appears instantly
```

1. Pick a **source unit** and a **target unit** from the dropdowns.
2. Click **Generate Conversion** to create the input fields.
3. Start typing in either field -- the converted value appears in the other in real time.

The conversion engine uses precomputed ratio tables for Volume and Length, and direct formulas for Temperature. All calculations happen on the event thread with no external dependencies.

---

## Getting Started

### Prerequisites

- **JDK 25** for building (the compiler targets Java 8 bytecode automatically)
- **JRE 8 or later** for running the raw JAR
- (Optional) Maven 3.6+ ([download](https://maven.apache.org/download.cgi))
- No other dependencies required

### Quick Start

#### Windows (PowerShell)
```powershell
.\launch.bat
```
The launch script detects whether Maven is available and falls back to direct `javac` compilation automatically.

#### macOS / Linux
```shell
# Option 1: with Maven
mvn clean compile exec:java -Dexec.mainClass="com.unitconverter.App"

# Option 2: manual compilation
mkdir -p target/classes
find src/main/java -name "*.java" > sources.txt
javac -d target/classes -encoding UTF-8 @sources.txt
cp -r src/main/resources/* target/classes/
java -cp target/classes com.unitconverter.App
```

### Build with Maven (all platforms)

```shell
# Build the executable JAR
mvn clean package

# Compile and run in one step
mvn clean compile exec:java -Dexec.mainClass="com.unitconverter.App"
```

After building, the runnable JAR is at `target/UnitConverter.jar`.

### Manual Compilation by Platform

<details>
<summary>Windows (PowerShell)</summary>

```powershell
# Create output directory
New-Item -ItemType Directory -Path target\classes -Force

# Collect and compile sources
Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName } | Out-File -Encoding ascii sources.txt
javac -d target\classes -encoding UTF-8 @sources.txt

# Copy resources
Copy-Item -Recurse src\main\resources\* target\classes\

# Run
java -cp target\classes com.unitconverter.App
```

</details>

<details>
<summary>macOS / Linux</summary>

```shell
# Create output directory
mkdir -p target/classes

# Collect and compile sources
find src/main/java -name "*.java" > sources.txt
javac -d target/classes -encoding UTF-8 @sources.txt

# Copy resources
cp -r src/main/resources/* target/classes/

# Run
java -cp target/classes com.unitconverter.App
```

</details>

---

## Project Structure

```
Java-unit-converter/
  pom.xml                                   Maven build configuration
  launch.bat                                One-click launcher (no Maven needed)

  src/main/java/com/unitconverter/
    App.java                                Main application (entry point)

  src/main/resources/
    fonts/NotoSans-Regular.ttf              User interface font (body)
    fonts/NotoSans-Bold.ttf                 User interface font (headings)
    fonts/EBGaramond-Regular.ttf            Data display font
    favicon.png                             Application window icon

  font_licenses/
    OFL-Noto_Sans.txt                       Noto Sans license (SIL OFL)
    OFL-EB_Garamond.txt                     EB Garamond license (SIL OFL)
```

---

## Building Native Installers

This project includes a GitHub Actions workflow that builds native platform installers with a **bundled Java runtime** -- no JVM installation required on the target machine.

### How to Trigger

**From a tag (automatic):**
```shell
git tag v1.0.0
git push origin v1.0.0
```

**Manually from the Actions tab:** open [Release workflow](https://github.com/richie-rich90454/Java-unit-converter/actions/workflows/release.yml), click "Run workflow," optionally provide a tag name.

### Artifacts Produced per Release

| Platform | Installer (bundled JRE) | Also Available |
|----------|------------------------|----------------|
| Windows x64 | `.exe` | Raw `.jar` (requires system JVM) |
| macOS arm64 | `.dmg` | Raw `.jar` (requires system JVM) |
| Linux x64 | `.deb` | Raw `.jar` (requires system JVM) |
| Linux arm64 | `.deb` | Raw `.jar` (requires system JVM) |

All artifacts are automatically attached to the GitHub Release page. The native installers contain a bundled JRE generated by `jlink` and are fully self-contained.

### How jpackage Bundles the JRE

`jpackage` uses `jlink` under the hood to create a minimal Java runtime containing only the modules required by the application. This runtime is embedded inside the installer:

```
UnitConverter/
  bin/UnitConverter       # Launcher binary
  lib/app/
    UnitConverter.jar     # Application code
    UnitConverter.cfg     # JVM arguments
  lib/runtime/            # Bundled JRE (jlink output)
```

The bundled runtime is stripped of debug symbols, man pages, and header files to minimize size.

### Build Matrix

| Artifact | Toolchain | Target JRE | Purpose |
|----------|-----------|------------|---------|
| Raw JAR | JDK 25 with `--release 8` | 8+ | Runs on any JRE 8 or later |
| Native installer | JDK 25 `jpackage` | 25 (bundled) | No JVM required on target |

Everything is built with **JDK 25**. The compiler flag `--release 8` produces Java 8 bytecode so the raw JAR works on any JRE from version 8 upward. The native installers use JDK 25's `jpackage` to embed a minimal JRE 25 runtime, making them fully self-contained.

### Prerequisites for Local jpackage Builds

- JDK 25 (jpackage is bundled with the JDK starting from JDK 14)
- On Linux: `dpkg-deb` and `fakeroot` (for `.deb` builds)
- On Windows: no additional tools required (for `.exe`; `.msi` requires WiX Toolset)

### Local jpackage Example

```shell
mvn clean package
jpackage \
  --type deb \
  --input target \
  --main-jar UnitConverter.jar \
  --main-class com.unitconverter.App \
  --name UnitConverter \
  --app-version "1.0.0" \
  --description "Java Unit Converter" \
  --vendor "richie-rich90454" \
  --destination target/installer
```

Adjust `--type` for your platform: `exe` (Windows), `dmg` (macOS), `deb` or `rpm` (Linux). For full options, see the [jpackage documentation](https://docs.oracle.com/en/java/javase/17/jpackage/).

---

## Troubleshooting

| Problem | Solution |
|---------|----------|
| Fonts not rendering | Confirm `src/main/resources/fonts/` exists and font filenames match `App.java`. Check file permissions on Linux/macOS. |
| Classpath errors | Compile with `javac -d target\classes -encoding UTF-8 src\main\java\com\unitconverter\App.java`, then copy resources and run with `java -cp target\classes com.unitconverter.App`. |
| JAR won't launch | Run `java -jar target/UnitConverter.jar` from the project root so resource paths resolve correctly. |
| UI looks wrong | Check the console for font-loading error messages. Confirm `favicon.png` is in `src/main/resources/`. |

---

## Contributing

Pull requests, bug reports, and feature ideas are welcome. The project is MIT-licensed and intentionally simple -- one source file, no framework, no build complexity.

1. Fork the repository.
2. Create a feature branch: `git checkout -b feature/my-change`.
3. Commit your changes.
4. Open a pull request.

See [CONTRIBUTING.md](CONTRIBUTING.md) for detailed guidelines.

---

## License

Distributed under the **MIT License**. See [LICENSE](LICENSE) for the full text.

Fonts are licensed separately under the **SIL Open Font License 1.1** -- see `font_licenses/`.

---

<div align="center">

**If you find this project useful, consider [starring the repository](https://github.com/richie-rich90454/Java-unit-converter/stargazers) -- it helps others discover it.**

&copy; 2025-2026 richie-rich90454. Made with Java Swing.

</div>
