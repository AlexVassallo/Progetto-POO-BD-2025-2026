package gui;

import javax.swing.*;
import java.awt.event.*;
import controller.Controller;

public class AllocaSalaOperatoriaMedico {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    Controller controller;

    //costruttore
    public AllocaSalaOperatoriaMedico(){
        //creazione della frame
        frame= new JFrame("alloca medico alla sala operatoria");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //listener
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //qui verrà fatto l'operazione di allocazione
                new PaginaPrincipale(controller);
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
