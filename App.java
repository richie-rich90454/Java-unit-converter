import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BorderLayout;
public class App{
    public static void main(String[] args) throws Exception{
        JFrame frame=new JFrame();
        JPanel volumePanel=new JPanel();
        JPanel lengthPanel=new JPanel();
        JPanel temperaturePanel=new JPanel();
        JLabel title=new JLabel("Unit Converter");
        frame.setTitle("Unit Converter");
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.decode("#DFDFDF"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}