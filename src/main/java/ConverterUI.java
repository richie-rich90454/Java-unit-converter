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
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.ByteArrayInputStream;
/*
    User Interface Components for Unit Converter
    Handles all Swing UI creation and management
*/
public class ConverterUI{
    //UI component declarations
    JFrame mainFrame;
    JPanel volumePanel, lengthPanel, temperaturePanel;
    JComboBox<String> volumeDropdownOne, volumeDropdownTwo, lengthDropdownOne, lengthDropdownTwo, temperatureDropdownOne, temperatureDropdownTwo;
    JButton generateVolume, generateLength, generateTemperature;
    JTabbedPane tabPanes;
    JButton themeToggleButton;
    JLabel titleLabelStatic;
    //Audio components
    private byte[] audioInformation;
    private Clip beepAudioClip;
    //Audio preloading section
    {
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
    private ActionListener buttonHandler=new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent event){
            //Calls the generateConverterBox method with relevant parameters
            new Thread(new Runnable(){
               public void run(){
                playBeep();
               } 
            }).start();
            if (event.getSource().equals(generateVolume)){
                generateConverterBox(volumePanel, volumeDropdownOne, volumeDropdownTwo, ConverterApp.volumeUnits, ConverterApp.conversionTableForVolume, false);
            }
            else if (event.getSource().equals(generateLength)){
                generateConverterBox(lengthPanel, lengthDropdownOne, lengthDropdownTwo, ConverterApp.lengthUnits, ConverterApp.conversionTableForLength, false);
            }
            else if (event.getSource().equals(generateTemperature)){
                //Special case: since it is impossible (unneccesarily complicated for this case) to make a conversion table for temperature (Fahrenheit is in formula form with a variable and a constant), the unitConversionTable is set to null and the specific parameter for the method to detect if it is a temperature conversion is set to true for handling in the generateConverterBox method
                generateConverterBox(temperaturePanel, temperatureDropdownOne, temperatureDropdownTwo, ConverterApp.temperatureUnits, null, true);
            }
        }
    };
    //Method for generating the converter boxes for the various JPanels for volume, length, and temperature conversions
    public void generateConverterBox(JPanel unitPanel, JComboBox<String> selectBoxOne, JComboBox<String> selectBoxTwo, String[] convertingUnits, double[] unitConversionTable, boolean isTemperatureConversion){
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
                            writeField.setText(ConverterApp.formatDoubleValues(ConverterApp.convertTemperature(-1, inputUnitIndex, writeUnitIndex)));
                        }
                        else{
                            writeField.setText(ConverterApp.formatDoubleValues(-1*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex]));
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
                            convertedDouble=ConverterApp.convertTemperature(originalDouble, inputUnitIndex, writeUnitIndex);
                        }
                        else{
                            //If not, uses unit ratios (dimentional analysis) with ratios from the unitConversionTable to convert the unit to the other unit
                            convertedDouble=originalDouble*unitConversionTable[inputUnitIndex]/unitConversionTable[writeUnitIndex];
                        }
                        //Sets the writeField (unit converted to) to the convertedDouble (the "" is due to the .setText method only accepting Strings)
                        writeField.setText(""+ConverterApp.formatDoubleValues(convertedDouble));
                    }
                }
            };
            //Adds KeyListeners to the two unit input fields
            unitOneField.addKeyListener(numberInputHandle);
            unitTwoField.addKeyListener(numberInputHandle);
        }
    }
    public void configureMainFrame(JFrame frame) throws Exception{
        //Configures the frame's title; configures it to stop the program on closing the window; configures the layout to BorderLayout()
        frame.setTitle("Unit Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        //Adds favicon for the window; uses try catch for error handling and preventing an error that stops the program if the favicon fails to load
        try{
            ImageIcon favicon=new ImageIcon(ConverterApp.class.getResource("/images/favicon.png"));
            frame.setIconImage(favicon.getImage());
        }
        catch (Exception exception){
            System.err.println("Failed to fetch favicon.");
        }
    }
    //Method to set up the volumePanel
    public void configureVolumePanel(JPanel volumePanel){
        //Initializes the notice label to tell the user that a "-" would be treated as -1 and appends it to the volumePanel
        JLabel noticeLabelForNegativeSign=new JLabel("<html><center>Note: Any \"-\" entered with no trailing number would be treated as -1.</center></html>");
        noticeLabelForNegativeSign.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Sets the volumePanel to have the title in the JTabbedPane as "Volume", have y-axis BoxLayout, have elements aligned in the center, and size 600x400
        volumePanel.setBorder(new TitledBorder("Volume"));
        volumePanel.setLayout(new BoxLayout(volumePanel, BoxLayout.Y_AXIS));
        volumePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumePanel.setPreferredSize(new Dimension(600, 400));
        //Initializes the two dropdowns (JComboBoxes) for the unit tables with the volumeUnit and center alignment with a tooltip prompting the user to select a volume unit
        volumeDropdownOne=new JComboBox<String>(ConverterApp.volumeUnits);
        volumeDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        volumeDropdownOne.setToolTipText("Select a volume unit");
        volumeDropdownTwo=new JComboBox<String>(ConverterApp.volumeUnits);
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
    public void configureLengthPanel(JPanel lengthPanel){
        //Comments would be the same as the configureVolumePanel except for the naming and variables being related to length
        JLabel noticeLabelForNegativeSign=new JLabel("<html><center>Note: Any \"-\" entered with no trailing number would be treated as -1.</center></html>");
        noticeLabelForNegativeSign.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthPanel.setBorder(new TitledBorder("Length"));
        lengthPanel.setLayout(new BoxLayout(lengthPanel, BoxLayout.Y_AXIS));
        lengthPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthPanel.setPreferredSize(new Dimension(600, 400));
        lengthDropdownOne=new JComboBox<String>(ConverterApp.lengthUnits);
        lengthDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        lengthDropdownOne.setToolTipText("Select a length unit");
        lengthDropdownTwo=new JComboBox<String>(ConverterApp.lengthUnits);
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
    public void configureTemperaturePanel(JPanel temperaturePanel){
        //Comments would be the same as the configureVolumePanel except for the naming and variables being related to temperature. Furthermore, the label would be expanded to prompt users that values below absolute 0 would be parsed as absolute zero in the respective units
        JLabel noticeLabelForAbsoluteZero=new JLabel("<html><center>Note: 1. Any values for temperature below absolute 0 (-459.67 Degrees Fahrenheit, -273.15 Degrees Celsius, or 0 Kelvin) would be taken as their respective absolute 0 values. <br>2.Any \\\"-\\\" entered with no trailing number would be treated as -1.</center></html>");
        noticeLabelForAbsoluteZero.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.setBorder(new TitledBorder("Temperature"));
        temperaturePanel.setLayout(new BoxLayout(temperaturePanel, BoxLayout.Y_AXIS));
        temperaturePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperaturePanel.setPreferredSize(new Dimension(600, 400));
        temperatureDropdownOne=new JComboBox<String>(ConverterApp.temperatureUnits);
        temperatureDropdownOne.setAlignmentX(Component.CENTER_ALIGNMENT);
        temperatureDropdownOne.setToolTipText("Select a temperature unit");
        temperatureDropdownTwo=new JComboBox<String>(ConverterApp.temperatureUnits);
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
    public void configureTabPanes(JTabbedPane tabPanes){
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
    public void playBeep(){
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
    public void initializeUI() throws Exception{
        //Intializes the various frames, panels, and tabPanes that contain the user interface
        mainFrame=new JFrame();
        volumePanel=new JPanel();
        lengthPanel=new JPanel();
        temperaturePanel=new JPanel();
        JPanel mainPanel=new JPanel();
        JLabel titleLabel=new JLabel("Unit Converter");
        tabPanes=new JTabbedPane();
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
                //Reapply theme to update UI
                ThemeManager.applyTheme();
                javax.swing.SwingUtilities.updateComponentTreeUI(mainFrame);
            }
        });
        //Create panel for title and toggle button
        JPanel titlePanel=new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(themeToggleButton, BorderLayout.EAST);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //Calls the configureMainFrame method to configure the frame
        configureMainFrame(mainFrame);
        //Appends everything to the frame
        mainFrame.add(titlePanel, BorderLayout.PAGE_START);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(true);
    }
}