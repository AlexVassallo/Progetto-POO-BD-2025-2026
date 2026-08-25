package gui;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;

import controller.Controller;
import exceptions.ChiaveException;

public class AllocaSalaOperatoriaPaziente {

    //attributi
    private static JFrame frame;
    private JTextField textField1;
    private JButton confermaButton;
    private JButton resettaButton;
    private JPanel mainPanel;
    private JButton tornaIndietroButton;
    private JTextField textField2;
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
                try{
                    controller.allocaPazienteSalaOperatoria(textField1.getText(), textField2.getText());
                    frameChiamante.setVisible(true);
                    frame.dispose();
                }
                catch (IllegalStateException | ChiaveException | SQLException ex){
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
