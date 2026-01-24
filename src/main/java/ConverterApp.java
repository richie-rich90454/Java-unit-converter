/*
    build with one from
    build-exe-bundled.bat
    build-exe.bat
    build-jar.bat
 */
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Locale;
/*
    Program: Java Unit Converter
    Programmer: Richard
    Date: 2026/1/24
*/
/*
    References:
        1. JOptionPane: https://www.geeksforgeeks.org/java-joptionpane/
        2. JComboBox: https://www.geeksforgeeks.org/java-swing-jcombobox-examples/
        3. KeyListener/KeyAdapter (used together): https://www.geeksforgeeks.org/java-keylistener-in-awt/, https://stackoverflow.com/questions/10876491/how-to-use-keylistener, https://www.tutorialspoint.com/swing/swing_keyadapter.htm, https://docs.oracle.com/javase/8/docs/api/java/awt/event/KeyAdapter.html
        4. UIManager: https://www.geeksforgeeks.org/java-swing-look-feel/
        5. Font.createFont(): https://stackoverflow.com/questions/16761630/font-createfont-set-color-and-size-java-awt-font
        6. GraphicsEnvironment: https://docs.oracle.com/javase/8/docs/api/java/awt/GraphicsEnvironment.html
        7. Try/Catch: https://www.w3schools.com/java/java_try_catch.asp, https://www.geeksforgeeks.org/try-catch-throw-and-throws-in-java/
        8. Locale: https://docs.oracle.com/javase/8/docs/api/java/util/Locale.html
        9. ImageIcon: https://docs.oracle.com/javase/8/docs/api/javax/swing/ImageIcon.html
        10. .setIconImage (JFrame): https://stackoverflow.com/questions/15657569/how-to-set-icon-to-jframe
        11. JTabbedPane: https://docs.oracle.com/javase/8/docs/api/javax/swing/JTabbedPane.html
        12. BorderFactory: https://docs.oracle.com/javase/8/docs/api/javax/swing/BorderFactory.html
        13. Box: https://docs.oracle.com/javase/8/docs/api/javax/swing/Box.html
        14. Component: https://docs.oracle.com/javase/8/docs/api/java/awt/Component.html
        15. Dimension: https://docs.oracle.com/javase/8/docs/api/java/awt/Dimension.html
        16. InputSteam: https://docs.oracle.com/javase/8/docs/api/java/io/InputStream.html
        17. SwingConstants: https://docs.oracle.com/javase/8/docs/api/index.html?javax/swing/SwingConstants.html
        18. ImageIcon: https://docs.oracle.com/javase/8/docs/api/javax/swing/ImageIcon.html
        19. Cursor: https://docs.oracle.com/javase/8/docs/api/java/awt/Cursor.html
        20. BorderUIResource: https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/javax/swing/plaf/BorderUIResource.html
        21. HTML in JLabel: https://stackoverflow.com/questions/6635730/how-do-i-put-html-in-a-jlabel-in-java
        22. Toolkit: https://docs.oracle.com/javase/8/docs/api/java/awt/Toolkit.html
        23. AudioFormat: https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/AudioFormat.html
        24. AudioSystem: https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/AudioSystem.html
        25. Clip: https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/Clip.html
        26. AudioInputStream: https://docs.oracle.com/javase/8/docs/api/javax/sound/sampled/AudioInputStream.html
 */
public class ConverterApp{
    //Declares static variables (i.e. JPanels, JComboBoxes -basically <select></select> equivalent in Java Swing, and JButtons) for use in the program
    static ConverterUI converterUI;
    //Declares the various conversion ratios used to convert a certain unit to the base unit (liter for volume, meter for length) except for temeperature
    public static final double[] conversionTableForVolume={3.78541, 0.236588, 0.014787, 0.004929, 28.3168, 764.555, 0.016387, 1, 0.001, 1000, 0.001, 0.946353, 0.473176};
    public static final double[] conversionTableForLength={1609.344, 0.9144, 0.3048, 0.0254, 1, 1000, 0.01, 1852};
    //Defines the units the conversion boxes support and in the same order as the conversion ratios for direct use in initializes 
    public static final String[] volumeUnits={"Gallon", "U.S. Cup", "Tablespoon", "Teaspoon", "Cubic Feet", "Cubic Yard", "Cubic Inch", "Liter", "Milliliter", "Cubic Meter", "Cubic Centimeter", "Quart", "Pint"}; 
    public static final String[] lengthUnits={"Mile", "Yard", "Feet", "Inch", "Meter", "Kilometer", "Centimeter", "Nautical Mile"};
    public static final String[] temperatureUnits={"Fahrenheit", "Celsius", "Kelvin"};
    //Method to handle temperature conversion with inputValue (the numerical value for temperature), inputIndex (the unit to convert from; 0==Fahrenheit, 1==Celsius, 2==Kelvin), and writeIndex(units converted to; same numbers orderd as the inputIndex) as parameters
    public static double convertTemperature(double inputValue, int inputIndex, int writeIndex){
        //Input handle part:
        //Handles out of range values (<0K temperatures, as 0K is absolute zero; temperatures could not be lower than that)
        if (inputIndex==0&&inputValue<-459.67){
            //Case if it is Fahrenheit and smaller than -459.67 degrees Fahrenheit (absolute zero), parses it to -459.67 to prevent a non-possible value
            inputValue=-459.67;
        }
        if (inputIndex==1&&inputValue<-273.15){
            //Case if it is Celsius and smaller than -273.15 degrees Celsius (absolute zero), parses it to -273.15 to prevent a non-possible value
            inputValue=-273.15;
        }
        if (inputIndex==2&&inputValue<0){
            //Cases it is Kelvin and smaller than 0 Kelvin (absolute zero), parses it to 0 to prevent a non-possible value
            inputValue=0;
        }
        //Conversion part
        if (inputIndex==0&&writeIndex==1){
            //Case it is Fahrenheit to Celsuis
            return (inputValue-32.0)*(5.0/9.0);
        }
        if (inputIndex==0&&writeIndex==2){
            //Case it is Fahrenheit to Kelvin
            return ((inputValue-32.0)*(5.0/9.0))+273.15;
        }
        if (inputIndex==1&&writeIndex==0){
            //Case it is Celsius to Fahrenheit
            return (inputValue*(9.0/5.0))+32.0;
        }
        if (inputIndex==1&&writeIndex==2){
            //Case it is Celsius to Kelvin
            return inputValue+273.15;
        }
        if (inputIndex==2&&writeIndex==0){
            //Case it is Kelvin to Fahrenheit
            return (inputValue-273.15)*(9.0/5.0)+32.0;
        }
        if (inputIndex==2&&writeIndex==1){
            //Case it is Kelvin to Celsius
            return inputValue-273.15;
        }
        //Statement to prevent a compiler error only (would not be reachable by the program)
        return inputValue;
    }
    //Formats all double numbers (returns as a String) to five decimal places (the meaning of %.5f)
    public static String formatDoubleValues(double number){
        return String.format("%.5f", number);
    }
    //Method to fetch font from the local path and include it in the program
    public static void includeFont(String fontPath){
        //uses the getResrouceAsStream method to obtain the font file and create a font with the file
        try (InputStream fontFile=ConverterApp.class.getResourceAsStream(fontPath)){
            Font newFont=Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(newFont);
        }
        catch (Exception exception){
            System.err.println("Failed to load font "+fontPath+": "+exception);
        }
    }
    public static void main(String[] args) throws Exception{
        //Sets Program Language to English
        Locale.setDefault(Locale.ENGLISH);
        //Initialize ThemeManager
        ThemeManager.detectSystemTheme();
        ThemeManager.loadPreferences();
        //Allocates custom font for the UI (adds Noto Sans Regular, Noto Sans Bold, and EB Garamond Regular font truetype fonts):
        includeFont("/fonts/NotoSans-Regular.ttf");
        includeFont("/fonts/NotoSans-Bold.ttf");
        includeFont("/fonts/EBGaramond-Regular.ttf");
        //Apply theme (includes fonts and colors based on system detection or saved preference)
        ThemeManager.applyTheme();
        //Initialize UI
        converterUI = new ConverterUI();
        converterUI.initializeUI();
        //Show the main frame
        converterUI.mainFrame.setVisible(true);
    }
}