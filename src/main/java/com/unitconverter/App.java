package com.unitconverter;
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
import java.awt.Container;
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
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.awt.BorderLayout;
public class App{
    static JPanel volumePanel, lengthPanel, temperaturePanel;
    static JComboBox<String> volumeDropdownOne, volumeDropdownTwo, lengthDropdownOne, lengthDropdownTwo, temperatureDropdownOne, temperatureDropdownTwo;
    static JButton generateVolume, generateLength, generateTemperature;
    static JButton[] themeToggleButtons=new JButton[3];
    static byte[] audioInformation;
    static Clip beepAudioClip;
    static JFrame frame;
    static JLabel titleLabel;
    static JTabbedPane tabPanes;
    public static final double[] conversionTableForVolume={3.78541, 0.236588, 0.014787, 0.004929, 28.3168, 764.555, 0.016387, 1, 0.001, 1000, 0.001, 0.946353, 0.473176};
    public static final double[] conversionTableForLength={1609.344, 0.9144, 0.3048, 0.0254, 1, 1000, 0.01, 1852};
    public static final String[] volumeUnits={"Gallon", "U.S. Cup", "Tablespoon", "Teaspoon", "Cubic Feet", "Cubic Yard", "Cubic Inch", "Liter", "Milliliter", "Cubic Meter", "Cubic Centimeter", "Quart", "Pint"};
    public static final String[] lengthUnits={"Mile", "Yard", "Feet", "Inch", "Meter", "Kilometer", "Centimeter", "Nautical Mile"};
    public static final String[] temperatureUnits={"Fahrenheit", "Celsius", "Kelvin"};
    static boolean isDarkMode;
    static Color currentAccent;
    static final Color TAB_SELECTED=Color.decode("#F0F0F0");
    static final Color LIGHT_ACCENT=Color.decode("#1C94E9");
    static final Color DARK_ACCENT=Color.decode("#64B5F6");
    static final Color LIGHT_BG=Color.decode("#FFFFFF");
    static final Color DARK_BG=Color.decode("#1E1E1E");
    static final Color DARK_SURFACE=Color.decode("#2D2D2D");
    static final Color DARK_TEXT=Color.decode("#E0E0E0");
    public static void generateConverterBox(JPanel unitPanel, JComboBox<String> selectBoxOne, JComboBox<String> selectBoxTwo, String[] convertingUnits, double[] unitConversionTable, boolean isTemperatureConversion){
        int unitOneIndex=selectBoxOne.getSelectedIndex();
        int unitTwoIndex=selectBoxTwo.getSelectedIndex();
        if (unitOneIndex==unitTwoIndex){
            JOptionPane.showMessageDialog(null, "Please select two distinct units for conversion between them.");
            return;
        }
        while (unitPanel.getComponentCount()>8){
            unitPanel.remove(8);
        }
        if (unitOneIndex!=unitTwoIndex){
            JPanel newConversionBox=new JPanel();
            newConversionBox.setLayout(new BoxLayout(newConversionBox, BoxLayout.Y_AXIS));
            newConversionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            JTextField unitOneField=new JTextField(15);
            unitOneField.setToolTipText("Input your value for unit "+convertingUnits[unitOneIndex]+" here");
            JTextField unitTwoField=new JTextField(15);
            unitTwoField.setToolTipText("Input your value for unit "+convertingUnits[unitTwoIndex]+" here");
            JLabel unitOneLabel=new JLabel(convertingUnits[unitOneIndex]);
            unitOneLabel.setFont(new Font("EB Garamond", Font.PLAIN, 17));
            unitOneLabel.setForeground(Color.decode("#1C94E9"));
            newConversionBox.add(unitOneLabel);
            newConversionBox.add(unitOneField);
            newConversionBox.add(Box.createVerticalStrut(15));
            JLabel unitTwoLabel=new JLabel(convertingUnits[unitTwoIndex]);
            unitTwoLabel.setFont(new Font("EB Garamond", Font.PLAIN, 17));
            unitTwoLabel.setForeground(Color.decode("#1C94E9"));
            newConversionBox.add(unitTwoLabel);
            newConversionBox.add(unitTwoField);
            unitPanel.add(Box.createVerticalStrut(20));
            unitPanel.add(newConversionBox);
            unitPanel.revalidate();
            unitPanel.repaint();
            KeyListener numberInputHandle=new KeyAdapter(){
                public void keyReleased(KeyEvent event){
                    JTextField inputField=(JTextField) event.getSource();
                    String numberInput=inputField.getText().trim();
                    JTextField writeField;
                    int inputUnitIndex, writeUnitIndex;
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
                        boolean isValidNumber=true;
                        boolean hasDecimalPoint=false;
                        for (int i=0;i<numberInput.length();i++){
                            char digit=numberInput.charAt(i);
                            if (i==0&&digit=='-'){
                                continue;
                            }
                            if (digit=='.'){
                                if (hasDecimalPoint){
                                    isValidNumber=false;
                                    break;
                                }
                                hasDecimalPoint=true;
                            }
                            else if (digit<'0'||digit>'9'){
                                isValidNumber=false;
                                break;
                            }
                        }
                        if (!isValidNumber||numberInput.length()==0||numberInput.equals(".")){
                            writeField.setText("");
                            return;
                        }
                        double originalDouble=Double.parseDouble(numberInput);
                        double convertedDouble;
                        if (isTemperatureConversion){
                            convertedDouble=convertTemperature(originalDouble, inputUnitIndex, writeUnitIndex);
                        }
                        else{
                            convertedDouble=originalDouble*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex];
                        }
                        writeField.setText(""+formatDoubleValues(convertedDouble));
                    }
                }
            };
            unitOneField.addKeyListener(numberInputHandle);
            unitTwoField.addKeyListener(numberInputHandle);
        }
    }
    public static double convertTemperature(double inputValue, int inputIndex, int writeIndex){
        if (inputIndex==0&&inputValue<-459.67){
            inputValue=-459.67;
        }
        if (inputIndex==1&&inputValue<-273.15){
            inputValue=-273.15;
        }
        if (inputIndex==2&&inputValue<0){
            inputValue=0;
        }
        if (inputIndex==0&&writeIndex==1){
            return (inputValue-32.0)*(5.0/9.0);
        }
        if (inputIndex==0&&writeIndex==2){
            return ((inputValue-32.0)*(5.0/9.0))+273.15;
        }
        if (inputIndex==1&&writeIndex==0){
            return (inputValue*(9.0/5.0))+32.0;
        }
        if (inputIndex==1&&writeIndex==2){
            return inputValue+273.15;
        }
        if (inputIndex==2&&writeIndex==0){
            return (inputValue-273.15)*(9.0/5.0)+32.0;
        }
        if (inputIndex==2&&writeIndex==1){
            return inputValue-273.15;
        }
        return inputValue;
    }
    public static String formatDoubleValues(double number){
        return String.format("%.5f", number);
    }
    public static void includeFont(String fontPath){
        try (InputStream fontFile=App.class.getResourceAsStream(fontPath)){
            Font newFont=Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(newFont);
        }
        catch (Exception exception){
            System.err.println("Failed to load font "+fontPath+": "+exception);
        }
    }
    static boolean isSystemDarkMode(){
        String os=System.getProperty("os.name").toLowerCase(Locale.ENGLISH);
        try{
            if (os.contains("mac")||os.contains("darwin")){
                Process p=Runtime.getRuntime().exec("defaults read -g AppleInterfaceStyle");
                BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line=r.readLine();
                if (line!=null&&line.equalsIgnoreCase("Dark")){
                    return true;
                }
            }
            else if (os.contains("win")){
                Process p=Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize\" /v AppsUseLightTheme");
                BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line=r.readLine())!=null){
                    if (line.contains("AppsUseLightTheme")&&line.trim().endsWith("0x0")){
                        return true;
                    }
                }
            }
            else{
                Process p=Runtime.getRuntime().exec("gsettings get org.gnome.desktop.interface color-scheme");
                BufferedReader r=new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line=r.readLine();
                if (line!=null&&line.toLowerCase(Locale.ENGLISH).contains("dark")){
                    return true;
                }
                p=Runtime.getRuntime().exec("gsettings get org.gnome.desktop.interface gtk-theme");
                r=new BufferedReader(new InputStreamReader(p.getInputStream()));
                line=r.readLine();
                if (line!=null&&line.toLowerCase(Locale.ENGLISH).contains("dark")){
                    return true;
                }
            }
        }
        catch (Exception e){
        }
        return false;
    }
    static void applyTheme(){
        if (isDarkMode){
            currentAccent=DARK_ACCENT;
            UIManager.put("Panel.background", DARK_BG);
            UIManager.put("TextField.background", DARK_SURFACE);
            UIManager.put("TextField.foreground", DARK_TEXT);
            UIManager.put("TextField.caretForeground", DARK_TEXT);
            UIManager.put("ComboBox.background", DARK_SURFACE);
            UIManager.put("ComboBox.foreground", DARK_TEXT);
            UIManager.put("ToolTip.background", Color.decode("#424242"));
            UIManager.put("ToolTip.foreground", DARK_TEXT);
            UIManager.put("ToolTip.border", new BorderUIResource.LineBorderUIResource(Color.decode("#616161"), 1));
            UIManager.put("TitledBorder.titleColor", DARK_TEXT);
            UIManager.put("TabbedPane.background", DARK_SURFACE);
        }
        else{
            currentAccent=LIGHT_ACCENT;
            UIManager.put("Panel.background", LIGHT_BG);
            UIManager.put("TextField.background", LIGHT_BG);
            UIManager.put("TextField.foreground", Color.decode("#000000"));
            UIManager.put("TextField.caretForeground", Color.decode("#000000"));
            UIManager.put("ComboBox.background", Color.decode("#1C94E9"));
            UIManager.put("ComboBox.foreground", Color.decode("#FFFFFF"));
            UIManager.put("ToolTip.background", Color.decode("#FADE54"));
            UIManager.put("ToolTip.foreground", Color.decode("#000000"));
            UIManager.put("ToolTip.border", new BorderUIResource.LineBorderUIResource(Color.decode("#000000"), 1));
            UIManager.put("TitledBorder.titleColor", Color.decode("#000000"));
            UIManager.put("TabbedPane.background", LIGHT_BG);
        }
        UIManager.put("Button.background", Color.decode("#DE0000"));
        UIManager.put("Button.foreground", Color.decode("#FFFFFF"));
        UIManager.put("Label.foreground", currentAccent);
        UIManager.put("TabbedPane.foreground", currentAccent);
    }
    static void refreshComponentColors(Container container){
        for (Component c : container.getComponents()){
            if (c instanceof JPanel){
                if (isDarkMode){
                    c.setBackground(DARK_BG);
                }
                else{
                    c.setBackground(LIGHT_BG);
                }
                javax.swing.border.Border b=((JPanel) c).getBorder();
                if (b instanceof TitledBorder){
                    if (isDarkMode){
                        ((TitledBorder) b).setTitleColor(DARK_TEXT);
                    }
                    else{
                        ((TitledBorder) b).setTitleColor(Color.decode("#000000"));
                    }
                }
            }
            if (c instanceof JTextField){
                if (isDarkMode){
                    c.setBackground(DARK_SURFACE);
                    c.setForeground(DARK_TEXT);
                }
                else{
                    c.setBackground(LIGHT_BG);
                    c.setForeground(Color.decode("#000000"));
                }
            }
            if (c instanceof JComboBox){
                if (isDarkMode){
                    c.setBackground(DARK_SURFACE);
                    c.setForeground(DARK_TEXT);
                }
                else{
                    c.setBackground(Color.decode("#1C94E9"));
                    c.setForeground(Color.decode("#FFFFFF"));
                }
            }
            if (c instanceof JTabbedPane){
                JTabbedPane tp=(JTabbedPane) c;
                tp.setForeground(currentAccent);
                if (isDarkMode){
                    tp.setBackground(DARK_SURFACE);
                }
                else{
                    tp.setBackground(LIGHT_BG);
                }
                for (int i=0;i<tp.getTabCount();i++){
                    tp.setForegroundAt(i, currentAccent);
                    tp.setBackgroundAt(i, TAB_SELECTED);
                }
            }
            if (c instanceof JLabel){
                JLabel label=(JLabel) c;
                label.setForeground(currentAccent);
            }
            if (c instanceof Container){
                refreshComponentColors((Container) c);
            }
        }
    }
    static void toggleTheme(){
        isDarkMode=!isDarkMode;
        applyTheme();
        if (frame!=null){
            refreshComponentColors(frame.getContentPane());
            if (isDarkMode){
                frame.getContentPane().setBackground(DARK_BG);
            }
            else{
                frame.getContentPane().setBackground(LIGHT_BG);
            }
            titleLabel.setForeground(currentAccent);
            tabPanes.setForeground(currentAccent);
            if (isDarkMode){
                tabPanes.setBackground(DARK_SURFACE);
            }
            else{
                tabPanes.setBackground(LIGHT_BG);
            }
            for (int i=0;i<tabPanes.getTabCount();i++){
                tabPanes.setForegroundAt(i, currentAccent);
                tabPanes.setBackgroundAt(i, TAB_SELECTED);
            }
            tabPanes.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(currentAccent, 2), BorderFactory.createEmptyBorder(7, 7, 7, 7)));
        }
        for (int b=0;b<themeToggleButtons.length;b++){
            if (themeToggleButtons[b]!=null){
                if (isDarkMode){
                    themeToggleButtons[b].setText("Light");
                }
                else{
                    themeToggleButtons[b].setText("Dark");
                }
            }
        }
    }
    static{
        isDarkMode=isSystemDarkMode();
        applyTheme();
        includeFont("/fonts/NotoSans-Regular.ttf");
        includeFont("/fonts/NotoSans-Bold.ttf");
        includeFont("/fonts/EBGaramond-Regular.ttf");
        Font notoSans=new Font("Noto Sans", Font.PLAIN, 14);
        Font notoSansBold=new Font("Noto Sans", Font.BOLD, 14);
        Font ebGaramond=new Font("EB Garamond", Font.PLAIN, 16);
        UIManager.put("Label.font", notoSans);
        UIManager.put("Button.font", notoSansBold);
        UIManager.put("TextField.font", ebGaramond);
        UIManager.put("ComboBox.font", ebGaramond);
        UIManager.put("OptionPane.messageFont", ebGaramond);
        UIManager.put("TitledBorder.font", notoSansBold);
        UIManager.put("TabbedPane.font", notoSansBold);
        UIManager.put("ToolTip.font", ebGaramond);
        int sampleNumber=(int) ((48000*350)/1000.0);
        audioInformation=new byte[sampleNumber];
        for (int i=0;i<sampleNumber;i++){
            audioInformation[i]=(byte)(Math.sin(i*2.0*Math.PI*432/48000)*80);
        }
        try{
            AudioFormat audioFormat=new AudioFormat((float) 48000, 8, 2, true, true);
            AudioInputStream audioInputStream=new AudioInputStream(new ByteArrayInputStream(audioInformation), audioFormat, audioInformation.length);
            beepAudioClip=AudioSystem.getClip();
            beepAudioClip.open(audioInputStream);
        }
        catch (Exception exception){
            beepAudioClip=null;
            System.err.println("Failed to initialize audio"+exception);
        }
    }
    public static ActionListener buttonHandler=new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent event){
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
                generateConverterBox(temperaturePanel, temperatureDropdownOne, temperatureDropdownTwo, temperatureUnits, null, true);
            }
        }
    };
    public static void configureMainFrame(JFrame frame) throws Exception{
        frame.setTitle("Unit Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        try{
            ImageIcon favicon=new ImageIcon(App.class.getResource("/favicon.png"));
            frame.setIconImage(favicon.getImage());
        }
        catch (Exception exception){
            System.err.println("Failed to fetch favicon.");
        }
    }
    public static void configureVolumePanel(JPanel volumePanel){
        JLabel noticeLabelForNegativeSign=new JLabel("<html><center>Note: Any \"-\" entered with no trailing number would be treated as -1.</center></html>");
        noticeLabelForNegativeSign.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumePanel.setBorder(new TitledBorder("Volume"));
        volumePanel.setLayout(new BoxLayout(volumePanel, BoxLayout.Y_AXIS));
        volumePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumePanel.setPreferredSize(new Dimension(600, 400));
        volumeDropdownOne=new JComboBox<String>(volumeUnits);
        volumeDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeDropdownOne.setToolTipText("Select a volume unit");
        volumeDropdownTwo=new JComboBox<String>(volumeUnits);
        volumeDropdownTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeDropdownTwo.setToolTipText("Select a volume unit");
        volumePanel.add(volumeDropdownOne);
        volumePanel.add(Box.createVerticalStrut(7));
        volumePanel.add(volumeDropdownTwo);
        volumePanel.add(Box.createVerticalStrut(7));
        volumePanel.add(noticeLabelForNegativeSign);
        volumePanel.add(Box.createVerticalStrut(7));
        generateVolume=new JButton("Generate Conversion");
        generateVolume.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        volumePanel.add(generateVolume);
        generateVolume.setAlignmentX(Component.CENTER_ALIGNMENT);
        generateVolume.setToolTipText("Generate the volume conversion box");
        generateVolume.addActionListener(buttonHandler);
    }
    public static void configureLengthPanel(JPanel lengthPanel){
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
    public static void configureTemperaturePanel(JPanel temperaturePanel){
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
    public static void configureTabPanes(JTabbedPane tabPanes){
        tabPanes.setSize(new Dimension(600, 0));
        tabPanes.setForeground(currentAccent);
        if (isDarkMode){
            tabPanes.setBackground(DARK_SURFACE);
        }
        else{
            tabPanes.setBackground(LIGHT_BG);
        }
        tabPanes.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(currentAccent, 2), BorderFactory.createEmptyBorder(7, 7, 7, 7)));
        tabPanes.addTab("Volume", volumePanel);
        tabPanes.addTab("Length", lengthPanel);
        tabPanes.addTab("Temperature", temperaturePanel);
        for (int i=0;i<tabPanes.getTabCount();i++){
            tabPanes.setForegroundAt(i, currentAccent);
            tabPanes.setBackgroundAt(i, TAB_SELECTED);
        }
    }
    public static void playBeep(){
        if (beepAudioClip!=null){
            beepAudioClip.setFramePosition(0);
            beepAudioClip.start();
        }
        else{
            Toolkit.getDefaultToolkit().beep();
        }
    }
    public static void main(String[] args) throws Exception{
        Locale.setDefault(Locale.ENGLISH);
        frame=new JFrame();
        volumePanel=new JPanel();
        lengthPanel=new JPanel();
        temperaturePanel=new JPanel();
        JPanel mainPanel=new JPanel();
        titleLabel=new JLabel("Unit Converter");
        tabPanes=new JTabbedPane();
        titleLabel.setFont(new Font("Noto Sans", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(currentAccent);
        configureVolumePanel(volumePanel);
        configureLengthPanel(lengthPanel);
        configureTemperaturePanel(temperaturePanel);
        configureTabPanes(tabPanes);
        mainPanel.setSize(new Dimension(600, 0));
        mainPanel.add(tabPanes, BorderLayout.CENTER);
        configureMainFrame(frame);
        for (int b=0;b<3;b++){
            if (isDarkMode){
                themeToggleButtons[b]=new JButton("Light");
            }
            else{
                themeToggleButtons[b]=new JButton("Dark");
            }
            themeToggleButtons[b].setFont(new Font("Noto Sans", Font.PLAIN, 10));
            themeToggleButtons[b].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            themeToggleButtons[b].addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    toggleTheme();
                }
            });
        }
        volumePanel.add(Box.createVerticalStrut(5));
        volumePanel.add(themeToggleButtons[0]);
        lengthPanel.add(Box.createVerticalStrut(5));
        lengthPanel.add(themeToggleButtons[1]);
        temperaturePanel.add(Box.createVerticalStrut(5));
        temperaturePanel.add(themeToggleButtons[2]);
        frame.add(titleLabel, BorderLayout.PAGE_START);
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}