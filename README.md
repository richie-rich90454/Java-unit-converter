# Java Unit Converter
A Java Swing application for converting between volume, length, and temperature units with real-time calculations.
## Demo and Interface:
- ![image_one](demo/image_one.png)
- ![image_two](demo/image_two.png)
- ![image_three](demo/image_three.png)
- ![image_four](demo/image_four.png)
- ![image_five](demo/image_five.png)
- ![image_six](demo/image_six.png)
## Features
### 1. Volume Conversion
Convert between 13 volume units:
- Gallons
- Cup
- Tablespoon
- Teaspoon
- Cubic Feet
- Cubic Yard
- Cubic Inch
- Liter
- Milliliter
- Cubic Meter
- Cubic Centimeter
- Quart
- Pint
### 2. Length Conversion
Convert between 8 length units:
- Miles
- Yards
- Feet
- Inches
- Meter
- Kilometer
- Centimeter
- Nautical Mile
### 3. Temperature Conversion
Convert between 3 temperature units:
- Fahrenheit
- Celsius
- Kelvin
## Usage Instructions
1. **Select Conversion Type**
   - Choose between Volume, Length, or Temperature using the tabbed interface
2. **Generate Conversion Box**
   1. Select source unit from first dropdown
   2. Select target unit from second dropdown
   3. Click "Generate Conversion"
   - *Note:* You will get an error message if both units are identical
3. **Real-time Conversion**
   - Enter value in either field
   - Automatic conversion appears in the other field
   - Supports decimal values and negative numbers (single "-" becomes -1)
## Installation & Running

1. **Requirements**  
   - Java Development Kit (JDK) 8 or higher  
   - Project directory structure (ignoring non-code parts and .gitignore):
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
2. **Compile and Run**  
   ```bash
   javac *.java
   java App
3. **Troubleshooting**
    - If fonts do not load:
        - Verify the /fonts directory exists in the project root
        - Check if the font files match the name of the files imported in the App.java file
        - Check (mostly an issue on UNIX-based systems) if read permissions were given
    - If there are classpath errors:
        - Use explicit classpath statements:
            ```bash
            javac -cp . *.java
            java -cp . App
    - If the Graphical User Interface looks strange:
        - Check if the fonts have loaded
        - Check for any error messages
## Font Attribution
This application uses the following fonts under the [SIL Open Font License (OFL)](https://scripts.sil.org/OFL):
### EB Garamond
- **Author**: Georg Duffner  
- **Source**: [https://github.com/octaviopardo/EBGaramond12](https://github.com/octaviopardo/EBGaramond12)  
- **License**: [font_licenses/OFL-EB_Garamond.txt](font_licenses/OFL-EB_Garamond.txt)  
### Noto Sans
- **Author**: Google Inc.  
- **Source**: [https://github.com/notofonts/latin-greek-cyrillic](https://github.com/notofonts/latin-greek-cyrillic)  
- **License**: [font_licenses/OFL-Noto_Sans.txt](font_licenses/OFL-Noto_Sans.txt)  
### License Compliance
This application complies with SIL OFL requirements by:
1. Including original font files without modification  
2. Bundling complete OFL license texts  
3. Maintaining original copyright notices  
4. Not selling fonts independently of the application  
5. Providing clear attribution in documentation  
Full license texts are available in the [font_licenses/](font_licenses/) directory.
**Note:** This application's code is licensed under [MIT License](LICENSE). Font licenses apply only to the font files themselves.
