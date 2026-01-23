/*
    build with one from
    build-exe-bundled.bat
    build-exe.bat
    build-jar.bat
 */
/*
    run with
    run.bat
 */
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.BorderUIResource;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
/*
    Program: Java Unit Converter
    Programmer: Richard
    Date: 2025/5/8
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
public class App{
    //Declares static variables (i.e. JPanels, JComboBoxes -basically <select></select> equivalent in Java Swing, and JButtons) for use in the program
    static JPanel volumePanel, lengthPanel, temperaturePanel;
    static JComboBox<String> volumeDropdownOne, volumeDropdownTwo, lengthDropdownOne, lengthDropdownTwo, temperatureDropdownOne, temperatureDropdownTwo;
    static JButton generateVolume, generateLength, generateTemperature;
    //Initializes various variables for use in playing the 432Hz tone including the audio byte array and the Clip object for storing the audio and playing it instantly when wanted
    static byte[] audioInformation;
    static Clip beepAudioClip;
    //Declares the various conversion ratios used to convert a certain unit to the base unit (liter for volume, meter for length) except for temeperature
    public static final double[] conversionTableForVolume={3.78541, 0.236588, 0.014787, 0.004929, 28.3168, 764.555, 0.016387, 1, 0.001, 1000, 0.001, 0.946353, 0.473176};
    public static final double[] conversionTableForLength={1609.344, 0.9144, 0.3048, 0.0254, 1, 1000, 0.01, 1852};
    //Defines the units the conversion boxes support and in the same order as the conversion ratios for direct use in initializes 
    public static final String[] volumeUnits={"Gallon", "U.S. Cup", "Tablespoon", "Teaspoon", "Cubic Feet", "Cubic Yard", "Cubic Inch", "Liter", "Milliliter", "Cubic Meter", "Cubic Centimeter", "Quart", "Pint"}; 
    public static final String[] lengthUnits={"Mile", "Yard", "Feet", "Inch", "Meter", "Kilometer", "Centimeter", "Nautical Mile"};
    public static final String[] temperatureUnits={"Fahrenheit", "Celsius", "Kelvin"};
    //Theme management variables and methods for light/dark mode support
    public enum ThemeMode {
        SYSTEM,
        LIGHT,
        DARK
    }
    static class ThemeManager{
        //Stores current theme mode (SYSTEM follows OS, LIGHT forces light, DARK forces dark)
        private static ThemeMode currentMode=ThemeMode.SYSTEM;
        //Stores whether system is in dark mode (detected from OS)
        private static boolean systemDarkMode=false;
        //Properties for storing theme preferences
        private static Properties themePrefs=new Properties();
        //File name for theme preferences
        private static final String PREFS_FILE=".unitconverter-theme.properties";
        //Light theme colors (matches original exactly)
        private static final Color LIGHT_PANEL_BG=Color.decode("#FFFFFF");
        private static final Color LIGHT_BUTTON_BG=Color.decode("#DE0000");
        private static final Color LIGHT_BUTTON_FG=Color.decode("#FFFFFF");
        private static final Color LIGHT_TEXTFIELD_BG=Color.decode("#FFFFFF");
        private static final Color LIGHT_TEXTFIELD_FG=Color.decode("#000000");
        private static final Color LIGHT_COMBOBOX_BG=Color.decode("#1C94E9");
        private static final Color LIGHT_COMBOBOX_FG=Color.decode("#FFFFFF");
        private static final Color LIGHT_LABEL_FG=Color.decode("#1C94E9");
        private static final Color LIGHT_TOOLTIP_BG=Color.decode("#FADE54");
        private static final Color LIGHT_TOOLTIP_FG=Color.decode("#000000");
        private static final Color LIGHT_BORDER=Color.decode("#000000");
        private static final Color LIGHT_TAB_FG=Color.decode("#1C94E9");
        private static final Color LIGHT_UNIT_LABEL=Color.decode("#1C94E9");
        //Dark theme colors (adjusted for better contrast)
        private static final Color DARK_PANEL_BG=Color.decode("#2D2D2D");
        private static final Color DARK_BUTTON_BG=Color.decode("#FF4444");
        private static final Color DARK_BUTTON_FG=Color.decode("#FFFFFF");
        private static final Color DARK_TEXTFIELD_BG=Color.decode("#3D3D3D");
        private static final Color DARK_TEXTFIELD_FG=Color.decode("#E0E0E0");
        private static final Color DARK_COMBOBOX_BG=Color.decode("#3A7CA5");
        private static final Color DARK_COMBOBOX_FG=Color.decode("#FFFFFF");
        private static final Color DARK_LABEL_FG=Color.decode("#6BB5E9");
        private static final Color DARK_TOOLTIP_BG=Color.decode("#5A5A00");
        private static final Color DARK_TOOLTIP_FG=Color.decode("#FFFFFF");
        private static final Color DARK_BORDER=Color.decode("#666666");
        private static final Color DARK_TAB_FG=Color.decode("#6BB5E9");
        private static final Color DARK_UNIT_LABEL=Color.decode("#6BB5E9");
        static{
            loadPreferences();
            detectSystemTheme();
        }
        public static void detectSystemTheme(){
            String os=System.getProperty("os.name").toLowerCase();
            try{
                if (os.contains("win")){
                    //Windows: check registry
                    Process process=Runtime.getRuntime().exec(new String[]{"reg","query","HKEY_CURRENT_USER\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize","/v","AppsUseLightTheme"});
                    process.waitFor();
                    java.io.BufferedReader reader=new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line=reader.readLine())!=null){
                        if (line.contains("REG_DWORD")){
                            if (line.contains("0x0")){
                                systemDarkMode=true;
                            }
                            else{
                                systemDarkMode=false;
                            }
                            break;
                        }
                    }
                    reader.close();
                }
                else if (os.contains("mac")){
                    //macOS: check defaults
                    Process process=Runtime.getRuntime().exec(new String[]{"defaults","read","-g","AppleInterfaceStyle"});
                    process.waitFor();
                    java.io.BufferedReader reader=new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
                    String line=reader.readLine();
                    if (line!=null&&line.contains("Dark")){
                        systemDarkMode=true;
                    }
                    else{
                        systemDarkMode=false;
                    }
                    reader.close();
                }
                else if (os.contains("nix")||os.contains("nux")){
                    //Linux: check gsettings
                    try{
                        Process process=Runtime.getRuntime().exec(new String[]{"gsettings","get","org.gnome.desktop.interface","gtk-theme"});
                        process.waitFor();
                        java.io.BufferedReader reader=new java.io.BufferedReader(new java.io.InputStreamReader(process.getInputStream()));
                        String line=reader.readLine();
                        if (line!=null&&line.toLowerCase().contains("dark")){
                            systemDarkMode=true;
                        }
                        else{
                            systemDarkMode=false;
                        }
                        reader.close();
                    }
                    catch (Exception e){
                        //Fallback: check environment variable
                        String gtkTheme=System.getenv("GTK_THEME");
                        if (gtkTheme!=null&&gtkTheme.toLowerCase().contains("dark")){
                            systemDarkMode=true;
                        }
                        else{
                            systemDarkMode=false;
                        }
                    }
                }
            }
            catch (Exception e){
                //If detection fails, assume light mode
                systemDarkMode=false;
                System.err.println("Theme detection failed, defaulting to light mode: "+e.getMessage());
            }
        }
        public static boolean isDarkModeActive(){
            switch (currentMode){
                case LIGHT: return false;
                case DARK: return true;
                case SYSTEM: default: return systemDarkMode;
            }
        }
        public static ThemeMode getCurrentMode(){
            return currentMode;
        }
        public static void setMode(ThemeMode mode){
            currentMode=mode;
            savePreferences();
            applyTheme();
        }
        public static void cycleMode(){
            switch (currentMode){
                case SYSTEM:
                    currentMode=ThemeMode.LIGHT;
                    break;
                case LIGHT:
                    currentMode=ThemeMode.DARK;
                    break;
                case DARK:
                    currentMode=ThemeMode.SYSTEM;
                    break;
            }
            savePreferences();
            applyTheme();
        }
        public static String getModeDescription(){
            switch (currentMode){
                case SYSTEM:
                    return "Automatic (Follow System)";
                case LIGHT:
                    return "Light Mode";
                case DARK:
                    return "Dark Mode";
                default:
                    return "Unknown";
            }
        }
        public static String getModeButtonText(){
            switch (currentMode){
                case SYSTEM:
                    return "A";
                case LIGHT:
                    return "L";
                case DARK:
                    return "D";
                default:
                    return "?";
            }
        }
        public static Color getPanelBackground(){
            return isDarkModeActive()?DARK_PANEL_BG:LIGHT_PANEL_BG;
        }
        public static Color getButtonBackground(){
            return isDarkModeActive()?DARK_BUTTON_BG:LIGHT_BUTTON_BG;
        }
        public static Color getButtonForeground(){
            return isDarkModeActive()?DARK_BUTTON_FG:LIGHT_BUTTON_FG;
        }
        public static Color getTextFieldBackground(){
            return isDarkModeActive()?DARK_TEXTFIELD_BG:LIGHT_TEXTFIELD_BG;
        }
        public static Color getTextFieldForeground(){
            return isDarkModeActive()?DARK_TEXTFIELD_FG:LIGHT_TEXTFIELD_FG;
        }
        public static Color getComboBoxBackground(){
            return isDarkModeActive()?DARK_COMBOBOX_BG:LIGHT_COMBOBOX_BG;
        }
        public static Color getComboBoxForeground(){
            return isDarkModeActive()?DARK_COMBOBOX_FG:LIGHT_COMBOBOX_FG;
        }
        public static Color getLabelForeground(){
            return isDarkModeActive()?DARK_LABEL_FG:LIGHT_LABEL_FG;
        }
        public static Color getToolTipBackground(){
            return isDarkModeActive()?DARK_TOOLTIP_BG:LIGHT_TOOLTIP_BG;
        }
        public static Color getToolTipForeground(){
            return isDarkModeActive()?DARK_TOOLTIP_FG:LIGHT_TOOLTIP_FG;
        }
        public static Color getBorderColor(){
            return isDarkModeActive()?DARK_BORDER:LIGHT_BORDER;
        }
        public static Color getTabForeground(){
            return isDarkModeActive()?DARK_TAB_FG:LIGHT_TAB_FG;
        }
        public static Color getUnitLabelColor(){
            return isDarkModeActive()?DARK_UNIT_LABEL:LIGHT_UNIT_LABEL;
        }
        private static void loadPreferences(){
            try{
                File prefsFile=new File(PREFS_FILE);
                if (prefsFile.exists()){
                    themePrefs.load(new FileInputStream(prefsFile));
                    String mode=themePrefs.getProperty("theme.mode","SYSTEM");
                    try{
                        currentMode=ThemeMode.valueOf(mode);
                    }
                    catch (IllegalArgumentException e){
                        currentMode=ThemeMode.SYSTEM;
                    }
                }
            }
            catch (Exception e){
                //If loading fails, use default
                currentMode=ThemeMode.SYSTEM;
            }
        }
        private static void savePreferences(){
            try{
                themePrefs.setProperty("theme.mode",currentMode.name());
                themePrefs.store(new FileOutputStream(PREFS_FILE),"Unit Converter Theme Preferences");
            }
            catch (Exception e){
                System.err.println("Failed to save theme preferences: "+e.getMessage());
            }
        }
        public static void applyTheme(){
            //Apply fonts (unchanged from original)
            Font notoSans=new Font("Noto Sans",Font.PLAIN,14);
            Font notoSansBold=new Font("Noto Sans",Font.BOLD,14);
            Font ebGaramond=new Font("EB Garamond",Font.PLAIN,16);
            UIManager.put("Label.font",notoSans);
            UIManager.put("Button.font",notoSansBold);
            UIManager.put("TextField.font",ebGaramond);
            UIManager.put("ComboBox.font",ebGaramond);
            UIManager.put("OptionPane.messageFont",ebGaramond);
            UIManager.put("TitledBorder.font",notoSansBold);
            UIManager.put("TabbedPane.font",notoSansBold);
            UIManager.put("ToolTip.font",ebGaramond);
            //Apply colors based on current theme
            UIManager.put("Panel.background",getPanelBackground());
            UIManager.put("Button.background",getButtonBackground());
            UIManager.put("Button.foreground",getButtonForeground());
            UIManager.put("TextField.background",getTextFieldBackground());
            UIManager.put("TextField.foreground",getTextFieldForeground());
            UIManager.put("ComboBox.background",getComboBoxBackground());
            UIManager.put("ComboBox.foreground",getComboBoxForeground());
            UIManager.put("Label.foreground",getLabelForeground());
            UIManager.put("ToolTip.background",getToolTipBackground());
            UIManager.put("ToolTip.foreground",getToolTipForeground());
            UIManager.put("ToolTip.border",new BorderUIResource.LineBorderUIResource(getBorderColor(),1));
            //TabbedPane specific colors
            UIManager.put("TabbedPane.background",getPanelBackground());
            UIManager.put("TabbedPane.foreground",getTabForeground());
            UIManager.put("TabbedPane.selected",getPanelBackground());
            UIManager.put("TabbedPane.border",new BorderUIResource.LineBorderUIResource(getBorderColor(),1));
            //TitledBorder color
            UIManager.put("TitledBorder.titleColor",getLabelForeground());
            //Window and frame colors
            UIManager.put("Frame.background",getPanelBackground());
            UIManager.put("Frame.foreground",getLabelForeground());
            //Update existing UI components if frame is already created
            if (mainFrame!=null){
                //Set frame background
                mainFrame.getContentPane().setBackground(getPanelBackground());
                mainFrame.setBackground(getPanelBackground());
                //Update all components
                javax.swing.SwingUtilities.updateComponentTreeUI(mainFrame);
                //Also update tab pane colors and border
                if (tabPanes!=null){
                    tabPanes.setForeground(getTabForeground());
                    tabPanes.setBackground(getPanelBackground());
                    tabPanes.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(getTabForeground(), 2), BorderFactory.createEmptyBorder(7, 7, 7, 7)));
                }
                //Update title label color
                if (titleLabelStatic!=null){
                    titleLabelStatic.setForeground(getLabelForeground());
                }
                //Update volume, length, temperature panels background and border
                if (volumePanel!=null){
                    volumePanel.setBackground(getPanelBackground());
                    ((TitledBorder)volumePanel.getBorder()).setTitleColor(getLabelForeground());
                }
                if (lengthPanel!=null){
                    lengthPanel.setBackground(getPanelBackground());
                    ((TitledBorder)lengthPanel.getBorder()).setTitleColor(getLabelForeground());
                }
                if (temperaturePanel!=null){
                    temperaturePanel.setBackground(getPanelBackground());
                    ((TitledBorder)temperaturePanel.getBorder()).setTitleColor(getLabelForeground());
                }
                //Update all components recursively
                updateAllComponents(mainFrame.getContentPane());
            }
        }
        private static void updateAllComponents(java.awt.Container container){
            for (java.awt.Component comp:container.getComponents()){
                if (comp instanceof javax.swing.JPanel){
                    ((javax.swing.JPanel)comp).setBackground(getPanelBackground());
                }
                if (comp instanceof javax.swing.JLabel){
                    ((javax.swing.JLabel)comp).setForeground(getLabelForeground());
                }
                if (comp instanceof javax.swing.JButton){
                    ((javax.swing.JButton)comp).setBackground(getButtonBackground());
                    ((javax.swing.JButton)comp).setForeground(getButtonForeground());
                }
                if (comp instanceof javax.swing.JTextField){
                    ((javax.swing.JTextField)comp).setBackground(getTextFieldBackground());
                    ((javax.swing.JTextField)comp).setForeground(getTextFieldForeground());
                }
                if (comp instanceof javax.swing.JComboBox){
                    ((javax.swing.JComboBox)comp).setBackground(getComboBoxBackground());
                    ((javax.swing.JComboBox)comp).setForeground(getComboBoxForeground());
                }
                if (comp instanceof java.awt.Container){
                    updateAllComponents((java.awt.Container)comp);
                }
            }
        }
    }
    //Additional static variables for theme support
    static JFrame mainFrame;
    static JTabbedPane tabPanes;
    static JButton themeToggleButton;
    static JLabel titleLabelStatic;
    //Method for generating the converter boxes for the various JPanels for volume, length, and temperature conversions
    public static void generateConverterBox(JPanel unitPanel, JComboBox<String> selectBoxOne, JComboBox<String> selectBoxTwo, String[] convertingUnits, double[] unitConversionTable, boolean isTemperatureConversion){
        //Initializes unitOneIndex (index of unit to convert from) and unitTwoIndex (index of unit to convert to)
        int unitOneIndex=selectBoxOne.getSelectedIndex();
        int unitTwoIndex=selectBoxTwo.getSelectedIndex();
        //If the two units are the same, alerts the users with a JOptionPane message and breaks out of the method
        if (unitOneIndex==unitTwoIndex){
            JOptionPane.showMessageDialog(null, "Please select two distinct units for conversion between them.");
            return;
        }
        //Removes the old converter box (the seven components that should be left are the JComboBoxes one and two, the user prompting message, and the generateX button with spaces between all four elements; anything apart from this would be removed, as it is part of the old converter box)
        while (unitPanel.getComponentCount()>7){
            unitPanel.remove(7);
        }
        //If it is actually unit conversion (not using else if to prevent compiler issue), which is the two unit (indicies) are different
        if (unitOneIndex!=unitTwoIndex){
            //Initializes the conversionBox for housing the inputs and conversion, sets it to y-axis oriented BorderLayout, makes the x-axis elements aligned to the center
            JPanel newConversionBox=new JPanel();
            newConversionBox.setLayout(new BoxLayout(newConversionBox, BoxLayout.Y_AXIS));
            newConversionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            //Initializes the two input fields for the two units and uses a tooltip to prompt the user to input the values there (conversion happens in both places with input)
            JTextField unitOneField=new JTextField(15);
            unitOneField.setToolTipText("Input your value for unit "+convertingUnits[unitOneIndex]+" here");
            JTextField unitTwoField=new JTextField(15);
            unitTwoField.setToolTipText("Input your value for unit "+convertingUnits[unitTwoIndex]+" here");
            //Sets the two labels to indicate to the user which input box is which and customizes them with the EB Garamond font and the theme-appropriate color and appends the labels and fields to the newConversionBox
            JLabel unitOneLabel=new JLabel(convertingUnits[unitOneIndex]);
            unitOneLabel.setFont(new Font("EB Garamond", Font.PLAIN, 17));
            unitOneLabel.setForeground(ThemeManager.getUnitLabelColor());
            newConversionBox.add(unitOneLabel);
            newConversionBox.add(unitOneField);
            newConversionBox.add(Box.createVerticalStrut(15));
            JLabel unitTwoLabel=new JLabel(convertingUnits[unitTwoIndex]);
            unitTwoLabel.setFont(new Font("EB Garamond", Font.PLAIN, 17));
            unitTwoLabel.setForeground(ThemeManager.getUnitLabelColor());
            newConversionBox.add(unitTwoLabel);
            newConversionBox.add(unitTwoField);
            //Adds space to the unitPanel before the newConversionBox to make the UI look better
            unitPanel.add(Box.createVerticalStrut(20));
            unitPanel.add(newConversionBox);
            //Applies the changes to the unitPanel (needs to re-render the section)
            unitPanel.revalidate();
            unitPanel.repaint();
            //KeyListener (calls on key actions such as keyup or keydown) to handle the actual conversion
            KeyListener numberInputHandle=new KeyAdapter(){
                public void keyReleased(KeyEvent event){
                    //Uses a JTextField and the .getText() method to fetch the text from the inputing JTextField
                    JTextField inputField=(JTextField) event.getSource();
                    String numberInput=inputField.getText().trim();
                    //The writeField is listed as the TextField to write the finished converted unit to
                    JTextField writeField;
                    int inputUnitIndex, writeUnitIndex;
                    //Checks which TextField the user is inputting from and sets the respective field as the inputField and the other as the writeField
                    if (inputField==unitOneField){
                        writeField=unitTwoField;
                        inputUnitIndex=unitOneIndex;
                        writeUnitIndex=unitTwoIndex;
                    }
                    else{
                        writeField=unitOneField;
                        writeUnitIndex=unitOneIndex;
                        inputUnitIndex=unitTwoIndex;
                    }
                    //Handles case "-" and parses it as -1 to prevent issues
                    if (numberInput.equals("-")){
                        if (isTemperatureConversion){
                            writeField.setText(formatDoubleValues(convertTemperature(-1, inputUnitIndex, writeUnitIndex)));
                        }
                        else{
                            writeField.setText(formatDoubleValues(-1*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex]));
                        }
                        return;
                    }
                    else{
                        //Performs number validation to check if it is a number and is a floating point number
                        boolean isValidNumber=true;
                        boolean hasDecimalPoint=false;
                        //Iterates through the numberInput String to validate
                        for (int i=0;i<numberInput.length();i++){
                            //Uses numberInput's character at i for comparison by using the .charAt() method of Strings and saving it as a char to validate each index and character
                            char digit=numberInput.charAt(i);
                            //If it is a '-' at the first number, indicating that it is a negative number, than it is fine (continues)
                            if (i==0&&digit=='-'){
                                continue;
                            }
                            //If the character is a decimal point
                            if (digit=='.'){
                                //If there is already a decimal point, the input is not a number (no multiple decimal points), so sets isValidNumber to false and breaks
                                if (hasDecimalPoint){
                                    isValidNumber=false;
                                    break;
                                }
                                //If it is the first time, then sets hasDecimalPoint to true
                                hasDecimalPoint=true;
                            }
                            //If the number is, in terms of ASCII, smaller than '0' or larger than '9', sets isValidNumber to false and breaks, as the number is not accurate (only handles base 10 at this point)
                            else if (digit<'0'||digit>'9'){
                                isValidNumber=false;
                                break;
                            }
                        }
                        //If it is not a valid number, no digits are inputed, or is only ".", returns and breaks from the method
                        if (!isValidNumber||numberInput.length()==0||numberInput.equals(".")){
                            writeField.setText("");
                            return;
                        }
                        //parses the numberInput String as a double
                        double originalDouble=Double.parseDouble(numberInput);
                        //Initializes the convertedDouble variable to store the converted unit
                        double convertedDouble;
                        if (isTemperatureConversion){
                            //If it is already a temperature conversion, calls the convertTemperature method with the parameters required
                            convertedDouble=convertTemperature(originalDouble, inputUnitIndex, writeUnitIndex);
                        }
                        else{
                            //If not, uses unit ratios (dimentional analysis) with ratios from the unitConversionTable to convert the unit to the other unit
                            convertedDouble=originalDouble*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex];
                        }
                        //Sets the writeField (unit converted to) to the convertedDouble (the "" is due to the .setText method only accepting Strings)
                        writeField.setText(""+formatDoubleValues(convertedDouble));
                    }
                }
            };
            //Adds KeyListeners to the two unit input fields
            unitOneField.addKeyListener(numberInputHandle);
            unitTwoField.addKeyListener(numberInputHandle);
        }
    }
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
        try (InputStream fontFile=App.class.getResourceAsStream(fontPath)){
            Font newFont=Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(newFont);
        }
        catch (Exception exception){
            System.err.println("Failed to load font "+fontPath+": "+exception);
        }
    }
    static{
        //Allocates custom font for the UI (adds Noto Sans Regular, Noto Sans Bold, and EB Garamond Regular font truetype fonts):
        includeFont("/fonts/NotoSans-Regular.ttf");
        includeFont("/fonts/NotoSans-Bold.ttf");
        includeFont("/fonts/EBGaramond-Regular.ttf");
        //Apply theme (includes fonts and colors based on system detection or saved preference)
        ThemeManager.applyTheme();
        //Audio preloading section
        //The sample number is based off of sample rate per second 48000 (Hz), duration 350ms
        int sampleNumber=(int) ((48000*350)/1000.0);
        //Creates an audioInformation byte array that stores sampleNumber of tones for the raw sound data
        audioInformation=new byte[sampleNumber];
        //Loops sampleNumber of times to populate the audioInformation array
        for (int i=0;i<sampleNumber;i++){
            //Populates with a tiny slice of the 432Hz sine wave (the 80 is the amplitude of the sine function, and it is used to set how loud the audio should be )
            audioInformation[i]=(byte)(Math.sin(i*2.0*Math.PI*432/48000)*80);
        }
        try{
            //Declares the audio format as with 48000Hz sample rate, 8-bit audio, stereo, signed, and bigEndian
            AudioFormat audioFormat=new AudioFormat((float) 48000, 8, 2, true, true);
            //Creates a new audioInputStream for playing by inputting the byte array, the audio format, and the length of the byte array to play the content of the byte array
            AudioInputStream audioInputStream=new AudioInputStream(new ByteArrayInputStream(audioInformation), audioFormat, audioInformation.length);
            //Adds the clip to a global static Clip object and allows it to be played back quickly
            beepAudioClip=AudioSystem.getClip();
            beepAudioClip.open(audioInputStream);
        }
        catch (Exception exception){
            //If the previous fails, prints the error and the Clip object would be set to null
            beepAudioClip=null;
            System.err.println("Failed to initialize audio"+exception);
        }
    }
    //Action listener for the generateX buttons
    public static ActionListener buttonHandler=new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent event){
            //Calls the generateConverterBox method with relevant parameters
            new Thread(new Runnable(){
               public void run(){
                playBeep();
               } 
            }).start();
            if (event.getSource().equals(generateVolume)){
                generateConverterBox(volumePanel, volumeDropdownOne, volumeDropdownTwo, volumeUnits, conversionTableForVolume, false);
            }
            else if (event.getSource().equals(generateLength)){
                generateConverterBox(lengthPanel, lengthDropdownOne, lengthDropdownTwo, lengthUnits, conversionTableForLength, false);
            }
            else if (event.getSource().equals(generateTemperature)){
                //Special case: since it is impossible (unneccesarily complicated for this case) to make a conversion table for temperature (Fahrenheit is in formula form with a variable and a constant), the unitConversionTable is set to null and the specific parameter for the method to detect if it is a temperature conversion is set to true for handling in the generateConverterBox method
                generateConverterBox(temperaturePanel, temperatureDropdownOne, temperatureDropdownTwo, temperatureUnits, null, true);
            }
        }
    };
    public static void configureMainFrame(JFrame frame) throws Exception{
        //Configures the frame's title; configures it to stop the program on closing the window; configures the layout to BorderLayout()
        frame.setTitle("Unit Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        //Adds favicon for the window; uses try catch for error handling and preventing an error that stops the program if the favicon fails to load
        try{
            ImageIcon favicon=new ImageIcon(App.class.getResource("/images/favicon.png"));
            frame.setIconImage(favicon.getImage());
        }
        catch (Exception exception){
            System.err.println("Failed to fetch favicon.");
        }
    }
    //Method to set up the volumePanel
    public static void configureVolumePanel(JPanel volumePanel){
        //Initializes the notice label to tell the user that a "-" would be treated as -1 and appends it to the volumePanel
        JLabel noticeLabelForNegativeSign=new JLabel("<html><center>Note: Any \"-\" entered with no trailing number would be treated as -1.</center></html>");
        noticeLabelForNegativeSign.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Sets the volumePanel to have the title in the JTabbedPane as "Volume", have y-axis BoxLayout, have elements aligned in the center, and size 600x400
        volumePanel.setBorder(new TitledBorder("Volume"));
        volumePanel.setLayout(new BoxLayout(volumePanel, BoxLayout.Y_AXIS));
        volumePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumePanel.setPreferredSize(new Dimension(600, 400));
        //Initializes the two dropdowns (JComboBoxes) for the unit tables with the volumeUnit and center alignment with a tooltip prompting the user to select a volume unit
        volumeDropdownOne=new JComboBox<String>(volumeUnits);
        volumeDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeDropdownOne.setToolTipText("Select a volume unit");
        volumeDropdownTwo=new JComboBox<String>(volumeUnits);
        volumeDropdownTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeDropdownTwo.setToolTipText("Select a volume unit");
        //Appends the JComboBoxes to the volumePanel with vertical spacing between them to improve the UI
        volumePanel.add(volumeDropdownOne);
        volumePanel.add(Box.createVerticalStrut(7));
        volumePanel.add(volumeDropdownTwo);
        volumePanel.add(Box.createVerticalStrut(7));
        volumePanel.add(noticeLabelForNegativeSign);
        volumePanel.add(Box.createVerticalStrut(7));
        //Initializes a generateVolume button with the title "Generate Conversion", the equivalent of "cursor: pointer;" in CSS, adds it to the volumePanel, makes it centerly aligned, sets the tooltip text to generate the volume conversion box to inform the user of its function, and adds the ActionListener buttonHandler to it
        generateVolume=new JButton("Generate Conversion");
        generateVolume.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        volumePanel.add(generateVolume);
        generateVolume.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateVolume.setToolTipText("Generate the volume conversion box");
        generateVolume.addActionListener(buttonHandler);
    }
    //Method to set up the length panel
    public static void configureLengthPanel(JPanel lengthPanel){
        //Comments would be the same as the configureVolumePanel except for the naming and variables being related to length
        JLabel noticeLabelForNegativeSign=new JLabel("<html><center>Note: Any \"-\" entered with no trailing number would be treated as -1.</center></html>");
        noticeLabelForNegativeSign.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthPanel.setBorder(new TitledBorder("Length"));
        lengthPanel.setLayout(new BoxLayout(lengthPanel, BoxLayout.Y_AXIS));
        lengthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthPanel.setPreferredSize(new Dimension(600, 400));
        lengthDropdownOne=new JComboBox<String>(lengthUnits);
        lengthDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthDropdownOne.setToolTipText("Select a length unit");
        lengthDropdownTwo=new JComboBox<String>(lengthUnits);
        lengthDropdownTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthDropdownTwo.setToolTipText("Select a length unit");
        lengthPanel.add(lengthDropdownOne);
        lengthPanel.add(Box.createVerticalStrut(7));
        lengthPanel.add(lengthDropdownTwo);
        lengthPanel.add(Box.createVerticalStrut(7));
        lengthPanel.add(noticeLabelForNegativeSign);
        lengthPanel.add(Box.createVerticalStrut(7));
        generateLength=new JButton("Generate Conversion");
        generateLength.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        generateLength.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthPanel.add(generateLength);
        generateLength.setToolTipText("Generate the length conversion box");
        generateLength.addActionListener(buttonHandler);
    }
    //Method to set up the temperature panel
    public static void configureTemperaturePanel(JPanel temperaturePanel){
        //Comments would be the same as the configureVolumePanel except for the naming and variables being related to temperature. Furthermore, the label would be expanded to prompt users that values below absolute 0 would be parsed as absolute zero in the respective units
        JLabel noticeLabelForAbsoluteZero=new JLabel("<html><center>Note: 1. Any values for temperature below absolute 0 (-459.67 Degrees Fahrenheit, -273.15 Degrees Celsius, or 0 Kelvin) would be taken as their respective absolute 0 values. <br>2.Any \\\"-\\\" entered with no trailing number would be treated as -1.</center></html>");
        noticeLabelForAbsoluteZero.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.setBorder(new TitledBorder("Temperature"));
        temperaturePanel.setLayout(new BoxLayout(temperaturePanel, BoxLayout.Y_AXIS));
        temperaturePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.setPreferredSize(new Dimension(600, 400));
        temperatureDropdownOne=new JComboBox<String>(temperatureUnits);
        temperatureDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperatureDropdownOne.setToolTipText("Select a temperature unit");
        temperatureDropdownTwo=new JComboBox<String>(temperatureUnits);
        temperatureDropdownTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperatureDropdownTwo.setToolTipText("Select a temperature unit");
        temperaturePanel.add(temperatureDropdownOne);
        temperaturePanel.add(Box.createVerticalStrut(7));
        temperaturePanel.add(temperatureDropdownTwo);
        temperaturePanel.add(Box.createVerticalStrut(7));
        temperaturePanel.add(noticeLabelForAbsoluteZero);
        temperaturePanel.add(Box.createVerticalStrut(7));
        generateTemperature=new JButton("Generate Conversion");
        generateTemperature.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        generateTemperature.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.add(generateTemperature);
        generateTemperature.setToolTipText("Generate the temperature conversion box");
        generateTemperature.addActionListener(buttonHandler);
    }
    //Method to configure the JTabPane that houses the three panels
    public static void configureTabPanes(JTabbedPane tabPanes){
        //sets maximum width to 600, text color to theme-appropriate color, and sets border to one with spacing to improve UI
        tabPanes.setSize(new Dimension(600, 0));
        tabPanes.setForeground(ThemeManager.getTabForeground());
        tabPanes.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(ThemeManager.getTabForeground(), 2), BorderFactory.createEmptyBorder(7, 7, 7, 7)));
        //Adds the various planes to the tabPane with their respective titles
        tabPanes.addTab("Volume", volumePanel);
        tabPanes.addTab("Length", lengthPanel);
        tabPanes.addTab("Temperature", temperaturePanel);
    }
    //Function to play the audio beep for the generateX buttons
    public static void playBeep(){
        if (beepAudioClip!=null){
            //If the audio clip initializes correctly, starts from position 0 on the array and starts playing
            beepAudioClip.setFramePosition(0);
            beepAudioClip.start();
        }
        else{
            //If the audio clip fails to initialize, the default system beep is played
            Toolkit.getDefaultToolkit().beep();
        }
    }
    public static void main(String[] args) throws Exception{
        //Sets Program Language to English
        Locale.setDefault(Locale.ENGLISH);
        //Intializes the various frames, panels, and tabPanes that contain the user interface
        JFrame frame=new JFrame();
        volumePanel=new JPanel();
        lengthPanel=new JPanel();
        temperaturePanel=new JPanel();
        JPanel mainPanel=new JPanel();
        JLabel titleLabel=new JLabel("Unit Converter");
        JTabbedPane tabPanes=new JTabbedPane();
        //Sets the title to use bold Noto Sans at size 60, aligned center, and with theme-appropriate color
        titleLabel.setFont(new Font("Noto Sans", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(ThemeManager.getLabelForeground());
        //Calls various configuration methods to configure the panels
        configureVolumePanel(volumePanel);
        configureLengthPanel(lengthPanel);
        configureTemperaturePanel(temperaturePanel);
        configureTabPanes(tabPanes);
        //Sets maximum width of the mainPanel to 600 and adds the tabPane to it in the center
        mainPanel.setSize(new Dimension(600, 0));
        mainPanel.add(tabPanes, BorderLayout.CENTER);
        //Set mainPanel background to match theme
        mainPanel.setBackground(ThemeManager.getPanelBackground());
        //Set static references for theme updates
        mainFrame=frame;
        App.tabPanes=tabPanes;
        titleLabelStatic=titleLabel;
        //Create theme toggle button
        themeToggleButton=new JButton(ThemeManager.getModeButtonText());
        themeToggleButton.setFont(new Font("Noto Sans", Font.BOLD, 16));
        themeToggleButton.setToolTipText(ThemeManager.getModeDescription());
        themeToggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        themeToggleButton.setMargin(new java.awt.Insets(2, 8, 2, 8));
        themeToggleButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ThemeManager.cycleMode();
                themeToggleButton.setToolTipText(ThemeManager.getModeDescription());
                themeToggleButton.setText(ThemeManager.getModeButtonText());
            }
        });
        //Create panel for title and toggle button
        JPanel titlePanel=new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(themeToggleButton, BorderLayout.EAST);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //Calls the configureMainFrame method to configure the frame
        configureMainFrame(frame);
        //Appends everything to the frame
        frame.add(titlePanel, BorderLayout.PAGE_START);
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}