package gui;

import javax.swing.*;
import java.awt.event.*;
import controller.Controller;
public class DeallocaSalaRicoveroPaziente {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton1;
    private JButton resettaButton;
    Controller controller;

    //costruttore
    public DeallocaSalaRicoveroPaziente(){
        //creazione della frame
        frame=new JFrame("dealloca paziente dalla sala ricovero");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //listener
        confermaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //qui verra fatta l'operazione dove deallocherà il paziente
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