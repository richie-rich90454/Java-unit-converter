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
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.InputStream;
import java.util.Locale;
import java.awt.BorderLayout;
/*
    Program: Java Unit Converter
    Programmer: Richard
    Date: 2025/5/3
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
        12. https://docs.oracle.com/javase/8/docs/api/javax/swing/BorderFactory.html
        13. https://docs.oracle.com/javase/8/docs/api/javax/swing/Box.html
        14. https://docs.oracle.com/javase/8/docs/api/java/awt/Component.html
 */
public class App{
    //Declares static variables for use in the frame
    static JPanel volumePanel, lengthPanel, temperaturePanel;
    static JComboBox<String> volumeDropdownOne, volumeDropdownTwo, lengthDropdownOne, lengthDropdownTwo, temperatureDropdownOne, temperatureDropdownTwo;
    //Declares the various conversion ratios used to convert a certain unit to the base unit (liter for volume, meter for length) except for temeperature
    public static final double[] conversionTableForVolume={3.7854, 0.47318, 0.014787, 0.004929, 28.3168, 764.555, 0.016387, 1, 0.001, 1000, 0.001, 0.946353, 0.47318};
    public static final double[] conversionTableForLength={1609.34, 0.9144, 0.3048, 0.0254, 1, 1000, 0.01, 1852};
    //Defines the units the conversion boxes support
    public static final String[] volumeUnits={"Gallon", "Cup", "Tablespoon", "Teaspoon", "Cubic Feet", "Cubic Yard", "Cubic Inch", "Liter", "Milliliter", "Cubic Meter", "Cubic Centimeter", "Quart", "Pint"}; 
    public static final String[] lengthUnits={"Mile", "Yard", "Feet", "Inch", "Meter", "Kilometer", "Centimeter", "Nautical Mile"};
    public static final String[] temperatureUnits={"Fahrenheit", "Celsius", "Kelvin"};
    public static void generateConverterBox(JPanel unitPanel, JComboBox<String> selectBoxOne, JComboBox<String> selectBoxTwo, String[] convertingUnits, double[] unitConversionTable, boolean isTemperatureConversion){
        int unitOneIndex=selectBoxOne.getSelectedIndex();
        int unitTwoIndex=selectBoxTwo.getSelectedIndex();
        if (unitOneIndex==unitTwoIndex){
            JOptionPane.showMessageDialog(null, "Please select two distinct units for conversion between them.");
            return;
        }
        while (unitPanel.getComponentCount()>5){
            unitPanel.remove(5);
        }
        if (unitOneIndex!=unitTwoIndex){
            JPanel newConversionBox=new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 3));
            newConversionBox.setAlignmentX(Component.CENTER_ALIGNMENT);
            JTextField unitOneField=new JTextField(15);
            JTextField unitTwoField=new JTextField(15);
            newConversionBox.add(new JLabel(convertingUnits[unitOneIndex]));
            newConversionBox.add(unitOneField);
            newConversionBox.add(new JLabel(convertingUnits[unitTwoIndex]));
            newConversionBox.add(unitTwoField);
            unitPanel.add(Box.createVerticalStrut(25));
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
                            writeField.setText(""+convertTemperature(-1, inputUnitIndex, writeUnitIndex));
                        }
                        else{
                            writeField.setText(""+(-1*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex]));
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
                        if (!isValidNumber||numberInput.length()==0||numberInput.equals("-")||numberInput.equals(".")){
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
    //Formats all double numbers (returns as a String) to three decimal places (the meaning of %.3f)
    public static String formatDoubleValues(double number){
        return String.format("%.3f", number);
    }
    //Function to fetch font from the local path and include it in the program
    public static void includeFont(String fontPath){
        try (InputStream fontFile=App.class.getResourceAsStream(fontPath)){
            Font newFont=Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(newFont);
        }
        catch (Exception exception){
            System.err.println("Failed to load font "+fontPath+": "+exception);
        }
    }
    static{
        //Allocates custom font for the UI:
        includeFont("/fonts/NotoSans-Regular.ttf");
        includeFont("/fonts/NotoSans-Bold.ttf");
        includeFont("/fonts/EBGaramond-Regular.ttf");
        Font notoSans=new Font("Noto Sans", Font.PLAIN, 14);
        Font notoSansBold=new Font("Noto Sans", Font.BOLD, 14);
        Font ebGaramond=new Font("EB Garamond", Font.PLAIN, 16);
        //Applies the custom fonts for the various components
        UIManager.put("Label.font", notoSans);
        UIManager.put("Button.font", notoSansBold);
        UIManager.put("TextField.font", ebGaramond);
        UIManager.put("ComboBox.font", ebGaramond);
        UIManager.put("OptionPane.messageFont", ebGaramond);
        UIManager.put("TitledBorder.font", notoSansBold);
        //Sets custom colors for the various components
        UIManager.put("Panel.background", Color.decode("#FFFFFF"));
        UIManager.put("Button.background", Color.decode("#DE0000"));
        UIManager.put("Button.foreground", Color.decode("#FFFFFF"));
        UIManager.put("TextField.background", Color.decode("#FFFFFF"));
        UIManager.put("TextField.foreground", Color.decode("#000000"));
        UIManager.put("ComboBox.background", Color.decode("#1C94E9"));
        UIManager.put("ComboBox.foreground", Color.decode("#FFFFFF"));
        UIManager.put("TabbedPane.font", notoSansBold);
    }
    public static void main(String[] args) throws Exception{
        //Sets Program Language to English
        Locale.setDefault(Locale.ENGLISH);
        JFrame frame=new JFrame();
        volumePanel=new JPanel();
        lengthPanel=new JPanel();
        temperaturePanel=new JPanel();
        JPanel mainPanel=new JPanel();
        JLabel titleLabel=new JLabel("Unit Converter");
        titleLabel.setFont(new Font("Noto Sans", Font.BOLD, 35));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(Color.decode("#1C94E9"));
        volumePanel.setBorder(new TitledBorder("Volume"));
        volumePanel.setLayout(new BoxLayout(volumePanel, BoxLayout.Y_AXIS));
        volumePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.setTitle("Unit Converter");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Adds favicon for the window
        try{
            ImageIcon favicon=new ImageIcon(App.class.getResource("/favicon.png"));
            frame.setIconImage(favicon.getImage());
        }
        catch (Exception exception){
            System.err.println("Failed to fetch favicon.");
        }
        //Initializes the two dropdown boxes for the volume units and appends them to the volume panel
        volumeDropdownOne=new JComboBox<String>(volumeUnits);
        volumeDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeDropdownTwo=new JComboBox<String>(volumeUnits);
        volumeDropdownTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumePanel.add(volumeDropdownOne);
        volumePanel.add(Box.createVerticalStrut(7));
        volumePanel.add(volumeDropdownTwo);
        volumePanel.add(Box.createVerticalStrut(7));
        //Initializes the generateConversion button for generating the volume conversion box
        JButton generateVolume=new JButton("Generate Conversion");
        volumePanel.add(generateVolume);
        generateVolume.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthPanel.setBorder(new TitledBorder("Length"));
        lengthPanel.setLayout(new BoxLayout(lengthPanel, BoxLayout.Y_AXIS));
        lengthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Initializes the two dropdown boxes for the length units and appends them to the length panel
        lengthDropdownOne=new JComboBox<String>(lengthUnits);
        lengthDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthDropdownTwo=new JComboBox<String>(lengthUnits);
        lengthDropdownTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthPanel.add(lengthDropdownOne);
        lengthPanel.add(Box.createVerticalStrut(7));
        lengthPanel.add(lengthDropdownTwo);
        lengthPanel.add(Box.createVerticalStrut(7));
        //Initializes the generateConversion button for generating the length conversion box
        JButton generateLength=new JButton("Generate Conversion");
        lengthPanel.add(generateLength);
        generateLength.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.setBorder(new TitledBorder("Temperature"));
        temperaturePanel.setLayout(new BoxLayout(temperaturePanel, BoxLayout.Y_AXIS));
        temperaturePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Initializes the two dropdown boxes for the temperature units and appends them to the temperature panel
        temperatureDropdownOne=new JComboBox<String>(temperatureUnits);
        temperatureDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperatureDropdownTwo=new JComboBox<String>(temperatureUnits);
        temperatureDropdownTwo.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.add(temperatureDropdownOne);
        temperaturePanel.add(Box.createVerticalStrut(7));
        temperaturePanel.add(temperatureDropdownTwo);
        temperaturePanel.add(Box.createVerticalStrut(7));
        //Initializes the generateConversion button for generating the temperature conversion box
        JButton generateTemperature=new JButton("Generate Conversion");
        generateTemperature.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.add(generateTemperature);
        //Sets the ActionListener for the buttons to create the boxes
        ActionListener buttonHandler=new ActionListener(){
        //    public static void generateConverterBox(JPanel unitPanel, JComboBox<String> selectBoxOne, JComboBox<String> selectBoxTwo, String[] convertingUnits, double[] unitConversionTable, boolean isTemperatureConversion){
            @Override
            public void actionPerformed(ActionEvent event){
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
        generateVolume.addActionListener(buttonHandler);
        generateLength.addActionListener(buttonHandler);
        generateTemperature.addActionListener(buttonHandler);
        JTabbedPane tabPanes=new JTabbedPane();
        tabPanes.setSize(new Dimension(600, 0));
        tabPanes.setForeground(Color.decode("#1C94E9"));
        tabPanes.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#1C94E9"), 2), BorderFactory.createEmptyBorder(7, 7, 7, 7)));
        tabPanes.addTab("Volume", volumePanel);
        tabPanes.addTab("Length", lengthPanel);
        tabPanes.addTab("Temperature", temperaturePanel);
        mainPanel.setSize(new Dimension(600, 0));
        mainPanel.add(tabPanes, BorderLayout.CENTER);
        //Appends everything to the frame
        frame.add(titleLabel, BorderLayout.PAGE_START);
        frame.add(mainPanel);
        frame.pack();
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(true);
        frame.setVisible(true);
    }
}