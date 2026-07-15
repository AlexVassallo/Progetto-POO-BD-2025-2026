package gui;

import javax.swing.*;
import java.awt.event.*;
import controller.Controller;

public class AllocaSalaOperatoriaPaziente {

    //attributi
    private static JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JPanel mainPanel;
    private JButton tornaIndietroButton;
    Controller controller;

    //costruttore
    public AllocaSalaOperatoriaPaziente(JFrame frameChiamante,Controller controller){
        //creazione della frame
        frame= new JFrame("alloca paziente alla sala operatoria");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //listener
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //qui verrà fatto l'operazione di allocazione
                frameChiamante.setVisible(true);
                frame.dispose();
            }
        });

        resettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
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
