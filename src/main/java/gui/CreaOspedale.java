package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaOspedale {
    //attributi
    JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JPanel mainPanel;
    private JLabel label1;
    private JButton tornaIndietroButton;


    //costruttore
    public CreaOspedale(JFrame frameChiamante, Controller controller){
        frame=new JFrame("crea ospedale");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frameChiamante.setVisible(false);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             //in qualche modo fara la query al database per aggiungere il nuovo ospedale
             frameChiamante.setVisible(true);
             frame.dispose();
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frameChiamante.setVisible(true);
            }
        });
    }

}
