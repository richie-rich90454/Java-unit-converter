import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;
/*
    Program: Java Unit Converter
    Programmer: Richard
    Date: 2025/5/2
 */
/*
    References:
        1. JOptionPane: https://www.geeksforgeeks.org/java-joptionpane/
        2. JComboBox: https://www.geeksforgeeks.org/java-swing-jcombobox-examples/
        3. KeyListener/KeyAdapter (used together): https://www.geeksforgeeks.org/java-keylistener-in-awt/, https://stackoverflow.com/questions/10876491/how-to-use-keylistener, https://www.tutorialspoint.com/swing/swing_keyadapter.htm

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
        if (unitOneIndex!=unitTwoIndex){
            JPanel newConversionBox=new JPanel();
            JTextField unitOneField=new JTextField(5);
            JTextField unitTwoField=new JTextField(5);
            while (unitPanel.getComponentCount()>3){
                unitPanel.remove(3);
            }
            newConversionBox.add(new JLabel(convertingUnits[unitOneIndex]));
            newConversionBox.add(unitOneField);
            newConversionBox.add(new JLabel(convertingUnits[unitTwoIndex]));
            newConversionBox.add(unitTwoField);
            unitPanel.add(newConversionBox);
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
                        // if (isTemperatureConversion){
                            
                        // }
                        // else{
                            writeField.setText(""+(-1*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex]));
                        // }
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
                        // if (isTemperatureConversion){
                            
                        // }
                        // else{
                            convertedDouble=originalDouble*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex];
                        // }
                        writeField.setText(""+convertedDouble);
                    }
                }
            };
            unitOneField.addKeyListener(numberInputHandle);
            unitTwoField.addKeyListener(numberInputHandle);
        }
        else{
            JOptionPane.showMessageDialog(null, "Please select two distinct units for conversion between them.");
        }
    }
    public static double convertTemperature(double inputValue, int inputIndex, int writeIndex){
        if (inputIndex==0){
            if (writeIndex==1){
                return (inputValue-32)*(5/9);
            }
            else if (writeIndex==2){
                return ((inputValue-32)*(5/9))+273.15;
            }
        }
        else if (inputIndex==1){
            if (writeIndex==0){
                return (inputValue*(9/5))+32;
            }
            else if (writeIndex==2){
                return inputIndex+273.15;
            }
        }
        else if (inputIndex==2){
            if (writeIndex==0){
                return (inputValue-273.15)*(9/5)+32;
            }
            else if (writeIndex==1){
                return inputValue-273.15;
            }
        }
        return inputValue;
    }
    public static void main(String[] args) throws Exception{
        //Initializes basic components such as the frame, various panels for conversion, and does some styling on them by setting the font, font size, title, and text alignment; additional basic initiation is set by setting the frame title, setting it to use border layout and the main panel to use grid layout and other panels to use border layout, and setting the default close operation of ending the program
        JFrame frame=new JFrame();
        volumePanel=new JPanel();
        lengthPanel=new JPanel();
        temperaturePanel=new JPanel();
        JPanel mainPanel=new JPanel();
        JLabel titleLabel=new JLabel("Unit Converter");
        titleLabel.setFont(new Font("EB Garamond", Font.BOLD, 35));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        volumePanel.setBorder(new TitledBorder("Volume"));
        frame.setTitle("Unit Converter");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.decode("#DFDFDF"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Initializes the two dropdown boxes for the volume units and appends them to the volume panel
        volumeDropdownOne=new JComboBox<String>(volumeUnits);
        volumeDropdownTwo=new JComboBox<String>(volumeUnits);
        volumePanel.add(volumeDropdownOne);
        volumePanel.add(volumeDropdownTwo);
        //Initializes the generateConversion button for generating the volume conversion box
        JButton generateVolume=new JButton("Generate Conversion");
        volumePanel.add(generateVolume);
        lengthPanel.setBorder(new TitledBorder("Length"));
        //Initializes the two dropdown boxes for the length units and appends them to the length panel
        lengthDropdownOne=new JComboBox<String>(lengthUnits);
        lengthDropdownTwo=new JComboBox<String>(lengthUnits);
        lengthPanel.add(lengthDropdownOne);
        lengthPanel.add(lengthDropdownTwo);
        //Initializes the generateConversion button for generating the length conversion box
        JButton generateLength=new JButton("Generate Conversion");
        lengthPanel.add(generateLength);
        temperaturePanel.setBorder(new TitledBorder("Temperature"));
        //Initializes the two dropdown boxes for the temperature units and appends them to the temperature panel
        temperatureDropdownOne=new JComboBox<String>(temperatureUnits);
        temperatureDropdownTwo=new JComboBox<String>(temperatureUnits);
        temperaturePanel.add(temperatureDropdownOne);
        temperaturePanel.add(temperatureDropdownTwo);
        //Initializes the generateConversion button for generating the temperature conversion box
        JButton generateTemperature=new JButton("Generate Conversion");
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
                // else if (event.getSource().equals(generateTemperature)){
                //     generateConverterBox(temperaturePanel, temperatureDropdownOne, temperatureDropdownTwo, temperatureUnits, null, true);
                // }
            }
        };
        generateVolume.addActionListener(buttonHandler);
        generateLength.addActionListener(buttonHandler);
        // generateTemperature.addActionListener(buttonHandler);
        //Configures the grid layout for the main panel and appends the other panels
        mainPanel.setLayout(new GridLayout(1,3,1,1));
        mainPanel.add(volumePanel);
        mainPanel.add(lengthPanel);
        mainPanel.add(temperaturePanel);
        //Appends everything to the frame
        frame.add(titleLabel, BorderLayout.PAGE_START);
        frame.add(mainPanel);
        frame.setSize(1536, 768);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}