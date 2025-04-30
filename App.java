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
    public static void main(String[] args) throws Exception{
        JFrame frame=new JFrame();
        JPanel volumePanel=new JPanel();
        JPanel lengthPanel=new JPanel();
        JPanel temperaturePanel=new JPanel();
        JPanel mainPanel=new JPanel();
        mainPanel.setLayout(new GridLayout(1,3,1,1));
        JLabel titleLabel=new JLabel("Unit Converter");
        titleLabel.setFont(new Font("EB Garamond", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        volumePanel.setBorder(new TitledBorder("Volume"));
        lengthPanel.setBorder(new TitledBorder("Length"));
        temperaturePanel.setBorder(new TitledBorder("Temperature"));
        frame.setTitle("Unit Converter");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.decode("#DFDFDF"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.add(volumePanel);
        mainPanel.add(lengthPanel);
        mainPanel.add(temperaturePanel);
        frame.add(titleLabel, BorderLayout.PAGE_START);
        frame.add(mainPanel);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}