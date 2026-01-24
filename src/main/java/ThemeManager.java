import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
/*
    Theme Management System
    Handles light/dark mode detection and theme switching
*/
public class ThemeManager{
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
    //Theme mode enumeration
    public enum ThemeMode{
        SYSTEM,
        LIGHT,
        DARK
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
    //Color getter methods for UI components
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
    public static void loadPreferences(){
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
    }
}