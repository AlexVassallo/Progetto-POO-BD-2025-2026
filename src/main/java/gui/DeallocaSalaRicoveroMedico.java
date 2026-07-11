package gui;

import javax.swing.*;
import controller.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeallocaSalaRicoveroMedico {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    Controller controller;

    public DeallocaSalaRicoveroMedico(){
        //creazione della frame
        frame= new JFrame("dealloca medico dalla sala ricovero");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //listener
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //qui verrà fatto l'operazione di deallocazione
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
