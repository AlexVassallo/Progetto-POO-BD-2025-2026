package gui;

import javax.swing.*;
import java.awt.event.*;


public class Registrati {
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton registratiButton;
    private JButton resettaButton;

    public Registrati() {
        frame = new JFrame("registrati");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Home.creaFrame();
                //qua verificheremo quando l'utente non scrive niente
            }
        });
       resettaButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               textField1.setText("");
               passwordField1.setText("");
           }
       });

    }
}