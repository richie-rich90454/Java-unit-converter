<div align="center">

# Java Unit Converter

A clean and intuitive **Java Swing desktop unit converter** for **volume, length, and temperature conversion**, featuring real-time calculations and a simple GUI. It supports common measurement units in a lightweight Java Swing desktop interface.

![Java](https://img.shields.io/badge/Java-8%2B-blue)
![Platform](https://img.shields.io/badge/Platform-Desktop-lightgrey)
![GUI](https://img.shields.io/badge/GUI-Java%20Swing-orange)

![License](https://img.shields.io/github/license/richie-rich90454/Java-unit-converter)
![Repo Size](https://img.shields.io/github/repo-size/richie-rich90454/Java-unit-converter)
![Last Commit](https://img.shields.io/github/last-commit/richie-rich90454/Java-unit-converter)
![Issues](https://img.shields.io/github/issues/richie-rich90454/Java-unit-converter)
![Pull Requests](https://img.shields.io/github/issues-pr/richie-rich90454/Java-unit-converter)
![Latest Release](https://img.shields.io/github/v/release/richie-rich90454/Java-unit-converter)

</div>

---

## 🚀 Overview

**Java Unit Converter** is a lightweight desktop tool built with **pure Java Swing** that allows users to convert between common units instantly.  
It focuses on **clarity, correctness, and ease of use**, without external dependencies.

✔ Real-time bidirectional conversion  
✔ Simple tab-based UI  
✔ Custom typography using open-source fonts  
✔ No libraries, no frameworks — just Java

---

## 🖥 Demo & Interface

<p align="center">
  <img src="src/main/resources/images/demo/image_one.png" width="250"/>
  <img src="src/main/resources/images/demo/image_two.png" width="250"/>
  <img src="src/main/resources/images/demo/image_three.png" width="250"/>
</p>

<p align="center">
  <img src="src/main/resources/images/demo/image_four.png" width="250"/>
  <img src="src/main/resources/images/demo/image_five.png" width="250"/>
  <img src="src/main/resources/images/demo/image_six.png" width="250"/>
</p>

---

## ✨ Features

### 📦 Volume Conversion
Convert between **13 volume units**:
- Gallons, Quarts, Pints
- U.S. Cups, Tablespoons, Teaspoons
- Cubic Inches, Feet, Yards
- Liters, Milliliters
- Cubic Meters, Cubic Centimeters

### 📏 Length Conversion
Convert between **8 length units**:
- Miles, Yards, Feet, Inches
- Meters, Kilometers, Centimeters
- Nautical Miles

### 🌡 Temperature Conversion
Convert between **3 temperature scales**:
- Fahrenheit
- Celsius
- Kelvin

---

## ⚡ How It Works

1. **Select a conversion category**  
   Choose *Volume*, *Length*, or *Temperature* using tabs.

2. **Generate a conversion box**
   - Select a source unit
   - Select a target unit
   - Click **Generate Conversion**
   > Identical unit selections are blocked with a clear error message.

3. **Convert in real time**
   - Enter a value in **either** field
   - The converted value updates instantly
   - Supports decimals and negatives  
     (`-` alone is treated as `-1` for convenience)

---

## 🛠 Installation & Running

### Requirements
- **Java Development Kit (JDK) 8+**
- No external dependencies

### Project Structure
```
Java-unit-converter/
├── src/
│   └── main/
│       ├── java/
│       │   └── App.java
│       └── resources/
│           ├── fonts/
│           │   ├── NotoSans-Regular.ttf
│           │   ├── NotoSans-Bold.ttf
│           │   └── EBGaramond-Regular.ttf
│           ├── images/
│           │   ├── favicon.ico
│           │   ├── favicon.png
│           │   └── demo/
│           │       ├── image_one.png
│           │       ├── image_two.png
│           │       ├── image_three.png
│           │       ├── image_four.png
│           │       ├── image_five.png
│           │       └── image_six.png
│           ├── font_licenses/
│           │   ├── OFL-Noto_Sans.txt
│           │   └── OFL-EB_Garamond.txt
│           └── native-config/
│               └── reachability-metadata.json
├── config/
│   ├── launch4j-config.xml
│   └── manifest.txt
├── docs/
│   ├── CODE_OF_CONDUCT.md
│   ├── CONTRIBUTING.md
│   ├── LICENSE
│   ├── README.md
│   └── SECURITY.md
├── build/          # Compiled class files
├── dist/           # Distribution files (.jar, .exe)
└── .gitignore
```

### Compile & Run
```bash
# Compile
javac -d build src/main/java/App.java

# Run (from project root)
java -cp "build;src/main/resources" App

# Alternative: compile and run from src/main/java directory
cd src/main/java
javac App.java
java -cp ".;../../main/resources" App
```

### Building Distribution Packages

The project includes several build scripts for creating different distribution packages:

#### 1. JAR File (requires Java 8+)
```bash
build-jar.bat
```
Creates `dist/UnitConverter.jar` that can be run with `java -jar UnitConverter.jar`.

#### 2. EXE File (requires Java 8+ installed)
```bash
build-exe.bat
```
Creates `dist/UnitConverter.exe` using launch4j. Requires launch4j installed.

#### 3. EXE with Bundled JRE (standalone)
```bash
build-exe-bundled.bat
```
Creates `dist/UnitConverter-bundled.exe` with bundled JRE. Requires:
- launch4j installed
- Java 8+ JRE placed in `dist/jre/` directory

#### Build Script Details
- `build-jar.bat` - Creates JAR file with all resources
- `build-exe.bat` - Creates EXE wrapper for JAR (requires system JRE)
- `build-exe-bundled.bat` - Creates standalone EXE with bundled JRE
- `run.bat` - Runs the application (builds if needed)
- `config/launch4j-config.xml` - Configuration for EXE without bundled JRE
- `config/launch4j-config-bundled.xml` - Configuration for EXE with bundled JRE

> **Note:** For EXE builds, you need to install [launch4j](http://launch4j.sourceforge.net/) and add it to your PATH.

---

## 🧪 Troubleshooting

**Fonts not loading?**
- Ensure `src/main/resources/fonts/` exists and filenames match those in `App.java`
- Check file read permissions (especially on Linux/macOS)
- Verify the classpath includes `src/main/resources` when running

**Classpath errors?**
```bash
# Make sure to include both build directory and resources
javac -d build src/main/java/App.java
java -cp "build;src/main/resources" App
```

**UI looks incorrect?**
- Check console logs for font loading errors
- Verify font paths are correct in `App.java` (should be `/fonts/...` for resources)
- Ensure resources are in the classpath

---

## 🔤 Font Attribution

This project uses fonts licensed under the **SIL Open Font License (OFL)**.

### EB Garamond
- **Author:** Georg Duffner  
- **Source:** https://github.com/octaviopardo/EBGaramond12  
- **License:** `src/main/resources/font_licenses/OFL-EB_Garamond.txt`

### Noto Sans
- **Author:** Google Inc.  
- **Source:** https://github.com/notofonts/latin-greek-cyrillic  
- **License:** `src/main/resources/font_licenses/OFL-Noto_Sans.txt`

---

## 📜 License Compliance

This project complies with **Apache License 2.0** and **SIL OFL** by:
1. Including unmodified font files
2. Bundling full license texts
3. Preserving copyright notices
4. Not redistributing fonts independently
5. Providing clear attribution

---

## 🤝 Contributing

Contributions, bug reports, and suggestions are welcome.  
If you find this project useful, please consider **starring ⭐ the repository** — it helps others discover it.

---

© 2026 richie-rich90454
