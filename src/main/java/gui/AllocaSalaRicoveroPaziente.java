package gui;

import javax.swing.*;
import java.awt.event.*;
import controller.Controller;

public class AllocaSalaRicoveroPaziente {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private Controller controller;

    //costruttore
    public AllocaSalaRicoveroPaziente(JFrame frameChiamante,Controller controller){

        //creazione della frame
        frame= new JFrame("alloca paziente alla sala ricovero");
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
    }
}
