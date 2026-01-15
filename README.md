<div align="center">

# Java Unit Converter

A clean and intuitive **Java Swing desktop application** for converting **volume, length, and temperature units** with **real-time calculations**.

![Java](https://img.shields.io/badge/Java-8%2B-blue)
![License](https://img.shields.io/badge/License-Apache%202.0-green)
![GUI](https://img.shields.io/badge/GUI-Java%20Swing-orange)

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
  <img src="demo/image_one.png" width="250"/>
  <img src="demo/image_two.png" width="250"/>
  <img src="demo/image_three.png" width="250"/>
</p>

<p align="center">
  <img src="demo/image_four.png" width="250"/>
  <img src="demo/image_five.png" width="250"/>
  <img src="demo/image_six.png" width="250"/>
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
├── App.java
├── fonts/
│   ├── NotoSans-Regular.ttf
│   ├── NotoSans-Bold.ttf
│   └── EBGaramond-Regular.ttf
└── font_licenses/
    ├── OFL-Noto_Sans.txt
    └── OFL-EB_Garamond.txt
```

### Compile & Run
```bash
javac *.java
java App
```

---

## 🧪 Troubleshooting

**Fonts not loading?**
- Ensure `fonts/` exists and filenames match those in `App.java`
- Check file read permissions (especially on Linux/macOS)

**Classpath errors?**
```bash
javac -cp . *.java
java -cp . App
```

**UI looks incorrect?**
- Check console logs for font loading errors
- Verify font paths are correct

---

## 🔤 Font Attribution

This project uses fonts licensed under the **SIL Open Font License (OFL)**.

### EB Garamond
- **Author:** Georg Duffner  
- **Source:** https://github.com/octaviopardo/EBGaramond12  
- **License:** `font_licenses/OFL-EB_Garamond.txt`

### Noto Sans
- **Author:** Google Inc.  
- **Source:** https://github.com/notofonts/latin-greek-cyrillic  
- **License:** `font_licenses/OFL-Noto_Sans.txt`

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

© 2025 richie-rich90454
