import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.BorderLayout;
public class App{
    //Defines the various conversion ratios used to convert a certain unit to the base unit (liter for volume, meter for length) except for temeperature
    public static final double[] conversionTableForVolumes={3.7854, 0.47318, 0.014787, 0.004929, 28.3168, 764.555, 0.016387, 1, 0.001, 1000, 0.001, 0.946353, 0.47318};
    public static final double[] conversionTableForLength={1609.34, 0.9144, 0.3048, 0.0254, 1, 1000, 0.01, 1852};
    //Defines the units the conversion boxes support
    public static final String[] volumeUnits={"Gallon", "Cup", "Tablespoon", "Teaspoon", "Cubic Feet", "Cubic Yard", "Cubic Inch", "Liter", "Milliliter", "Cubic Meter", "Cubic Centimeter", "Quart", "Pint"}; 
    public static final String[] lengthUnits={"Mile", "Yard", "Feet", "Inch", "Meter", "Kilometer", "Centimeter", "Nautical Mile"};
    public static final String[] temperatureUnits={"Fahrenheit", "Celsius", "Kelvin"};
    public static void main(String[] args) throws Exception{
        //Initializes basic components such as the frame, various panels for conversion, and does some styling on them by setting the font, font size, title, and text alignment; additional basic initiation is set by setting the frame title, setting it to use border layout and the main panel to use grid layout and other panels to use border layout, and setting the default close operation of ending the program
        JFrame frame=new JFrame();
        JPanel volumePanel=new JPanel();
        JPanel lengthPanel=new JPanel();
        JPanel temperaturePanel=new JPanel();
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
        JComboBox<String> volumeDropdownOne=new JComboBox<String>(volumeUnits);
        JComboBox<String> volumeDropdownTwo=new JComboBox<String>(volumeUnits);
        volumePanel.add(volumeDropdownOne);
        volumePanel.add(volumeDropdownTwo);
        //Initializes the generateConversion button for generating the volume conversion box
        JButton generateVolume=new JButton("Generate Conversion");
        volumePanel.add(generateVolume);
        lengthPanel.setBorder(new TitledBorder("Length"));
        //Initializes the two dropdown boxes for the length units and appends them to the length panel
        JComboBox<String> lengthDropdownOne=new JComboBox<String>(lengthUnits);
        JComboBox<String> lengthDropdownTwo=new JComboBox<String>(lengthUnits);
        lengthPanel.add(lengthDropdownOne);
        lengthPanel.add(lengthDropdownTwo);
        //Initializes the generateConversion button for generating the length conversion box
        JButton generateLength=new JButton("Generate Conversion");
        lengthPanel.add(generateLength);
        temperaturePanel.setBorder(new TitledBorder("Temperature"));
        //Initializes the two dropdown boxes for the temperature units and appends them to the temperature panel
        JComboBox<String> temperatureDropdownOne=new JComboBox<String>(temperatureUnits);
        JComboBox<String> temperatureDropdownTwo=new JComboBox<String>(temperatureUnits);
        temperaturePanel.add(temperatureDropdownOne);
        temperaturePanel.add(temperatureDropdownTwo);
        //Initializes the generateConversion button for generating the temperature conversion box
        JButton generateTemperature=new JButton("Generate Conversion");
        temperaturePanel.add(generateTemperature);
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