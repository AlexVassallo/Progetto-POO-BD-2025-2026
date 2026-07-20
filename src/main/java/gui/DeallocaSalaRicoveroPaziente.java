package gui;

import javax.swing.*;
import java.awt.event.*;
import controller.Controller;
import exceptions.ChiaveException;

public class DeallocaSalaRicoveroPaziente {

    //attributi
    private static JFrame frame;
    private JPanel mainPanel;
    private JTextField textField1;
    private JButton confermaButton1;
    private JButton resettaButton;
    private JButton tornaIndietroButton;
    Controller controller;

    //costruttore
    public DeallocaSalaRicoveroPaziente(JFrame frameChiamante,Controller controller){
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
                try{
                    controller.deallocaPazienteSalaRicovero(textField1.getText());
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
                catch (IllegalStateException | ChiaveException ex){
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);
                }
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