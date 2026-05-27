package gui;

import javax.swing.*;

public class Home {
    private static JFrame frame;
    JPanel mainPanel;
    JButton button1;
    JButton button2;
    JTextField field1;
    JLabel label1;
    JPasswordField inserirePasswordPasswordField;
    private JTextField textField1;
    JLabel label2;
    public static void main(String[] args) {
         frame = new JFrame("login");
         frame.setContentPane(new Home().mainPanel);
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.pack();
         frame.setVisible(true);
    }
    }
